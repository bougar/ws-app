package es.udc.ws.app.model.offerservice;

import static es.udc.ws.app.model.util.ModelConstants.OFFER_DATA_SOURCE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.FacebookException;
import es.udc.ws.app.exceptions.HttpFacebookException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.app.model.facebook.FacebookService;
import es.udc.ws.app.model.facebook.FacebookServiceFactory;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offer.OfferToReturnedOffer;
import es.udc.ws.app.model.offer.ReturnedOffer;
import es.udc.ws.app.model.offer.SqlOfferDao;
import es.udc.ws.app.model.offer.SqlOfferDaoFactory;
import es.udc.ws.app.model.util.ModelConstants;
import es.udc.ws.app.model.reservation.Reservation;
import es.udc.ws.app.model.reservation.SqlReservationDao;
import es.udc.ws.app.model.reservation.SqlReservationDaoFactory;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.app.validation.PropertyValidator;

public class OfferServiceImpl implements OfferService {

	/*
	 * IMPORTANT: Some JDBC drivers require "setTransactionIsolation" to be
	 * called before "setAutoCommit".
	 */

	private DataSource dataSource;
	private SqlOfferDao offerDao = null;
	private SqlReservationDao reservationDao = null;
	private FacebookService facebook = null;

	public OfferServiceImpl() {
		dataSource = DataSourceLocator.getDataSource(OFFER_DATA_SOURCE);
		offerDao = SqlOfferDaoFactory.getDao();
		reservationDao = SqlReservationDaoFactory.getDao();
		facebook = FacebookServiceFactory.instance();
	}

	private void validateOffer(Offer offer) throws InputValidationException {
		PropertyValidator.validateMandatoryString("title", offer.getName());
		PropertyValidator.validateMandatoryString("description",
				offer.getDescription());
		PropertyValidator.validateTwoDates("limitReservationDate",
				offer.getLimitReservationDate(),
				offer.getLimitApplicationDate());
		PropertyValidator.validateNow("limitReservationDate",
				offer.getLimitReservationDate());
		PropertyValidator.validateFloat("realPrice", offer.getRealPrice(), 0,
				Float.MAX_VALUE);
		PropertyValidator.validateFloat("discountedPrice",
				offer.getDiscountedPrice(), 0, offer.getRealPrice());
		PropertyValidator.validateFloat("fee", offer.getFee(), 0, 100);
	}

	@Override
	public ReturnedOffer addOffer(Offer offer) throws InputValidationException {
		validateOffer(offer);

		try (Connection connection = dataSource.getConnection()) {

			try {

				/* Prepare connection. */
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
				offer.setValid(true);
				ReturnedOffer returnedOffer = OfferToReturnedOffer
						.toReturnedOffer(offer);
				returnedOffer.setLikes(Long.valueOf(0));
				offer.setFaceBookId(facebook.addOffer(offer));

				/* Do work. */
				Offer createdOffer = offerDao.create(connection, offer);
				returnedOffer.setOfferId(createdOffer.getOfferId());

				/* Commit. */

				connection.commit();

				return returnedOffer;
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			} catch (FacebookException | HttpFacebookException e) {
				throw new RuntimeException(e.getMessage());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateOffer(Offer offer) throws InputValidationException,
			InstanceNotFoundException, NotModifiableOfferException {

		validateOffer(offer);

		try (Connection connection = dataSource.getConnection()) {

			try {

				/* Prepare connection. */
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				/* Get previous values to validate modifications */
				Offer baseOffer = offerDao.find(connection, offer.getOfferId());
				offer.setValid(baseOffer.isValid());
				/* Checks if any user has already reserved the offer */

				/*
				 * If reservations > 0 we can only modify AppDate(later) and
				 * prize(cheaper)
				 */
				if (reservationDao.howManyByOffer(connection,
						offer.getOfferId()) > 0) {
					if (!baseOffer.isValid())
						throw new NotModifiableOfferException(
								baseOffer.getOfferId());
					if (offer.getLimitApplicationDate().compareTo(
							baseOffer.getLimitApplicationDate()) < 0)
						throw new NotModifiableOfferException(
								offer.getOfferId());

					if (offer.getDiscountedPrice() > baseOffer
							.getDiscountedPrice())
						throw new NotModifiableOfferException(
								offer.getOfferId());
				}

				offer.setFaceBookId(facebook.updateOffer(
						baseOffer.getFaceBookId(), offer));

				/* Do work. */
				offerDao.update(connection, offer);

				/* Commit. */
				connection.commit();

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw e;
			} catch (NotModifiableOfferException e) {
				connection.commit();
				throw e;
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			} catch (FacebookException | HttpFacebookException e) {
				throw new RuntimeException(e.getMessage());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void removeOffer(long offerId) throws InstanceNotFoundException,
			NotModifiableOfferException {

		try (Connection connection = dataSource.getConnection()) {

			try {

				/* Prepare connection. */
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				/*
				 * An offer can only be removed if no one has already reserved
				 * it
				 */
				if (reservationDao.howManyByOffer(connection, offerId) > 0)
					throw new NotModifiableOfferException(offerId);

				/* Do work. */
				Offer offer = offerDao.find(connection, offerId);
				facebook.removeOffer(offer.getFaceBookId());
				offerDao.remove(connection, offerId);

				/* Commit. */
				connection.commit();

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw e;
			} catch (NotModifiableOfferException e) {
				connection.commit();
				throw e;
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			} catch (FacebookException | HttpFacebookException e) {
				throw new RuntimeException(e.getMessage());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public ReturnedOffer findOffer(long offerId)
			throws InstanceNotFoundException {

		try (Connection connection = dataSource.getConnection()) {
			Offer offer = offerDao.find(connection, offerId);
			ReturnedOffer returnedeOffer = OfferToReturnedOffer
					.toReturnedOffer(offer);
			returnedeOffer.setLikes(facebook.getOfferLikes(offer
					.getFaceBookId()));
			return returnedeOffer;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (FacebookException | HttpFacebookException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public List<ReturnedOffer> findOffers(String keywords, Boolean state,
			Calendar date) {
		try (Connection connection = dataSource.getConnection()) {
			List<Offer> offers = offerDao.advancedFilter(connection, keywords,
					state, date);
			List<ReturnedOffer> returnedOffers = OfferToReturnedOffer
					.toReturnedOfferList(offers);
			int i = 0;
			for (ReturnedOffer returnedOffer : returnedOffers) {
				returnedOffer.setLikes(facebook.getOfferLikes(offers.get(i)
						.getFaceBookId()));
				i++;
			}
			return returnedOffers;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (FacebookException | HttpFacebookException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public long reserveOffer(long offerId, String email, String creditCardNumber)
			throws InputValidationException, InstanceNotFoundException,
			AlreadyInvalidatedException, ReservationTimeExpiredException,
			AlreadyReservatedException {

		PropertyValidator.validateCreditCard(creditCardNumber);

		try (Connection connection = dataSource.getConnection()) {

			try {

				/* Prepare connection. */
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				/* Do work. */
				Offer offer = offerDao.find(connection, offerId);

				/* If offer is invalid a reservation cannot be made */
				if (!offer.isValid())
					throw new AlreadyInvalidatedException(offer.getOfferId());

				if (reservationDao.isOfferAlreadyReservated(connection,
						offerId, email))
					throw new AlreadyReservatedException(offerId, email);

				if (offer.getLimitReservationDate().compareTo(
						Calendar.getInstance()) < 0)
					throw new ReservationTimeExpiredException(
							offer.getOfferId(), offer.getLimitReservationDate());

				Calendar requestDate = Calendar.getInstance();
				Reservation base = new Reservation(email, offer.getOfferId(),
						ModelConstants.NOT_CLAIMED, requestDate,
						creditCardNumber, offer.getDiscountedPrice(),
						offer.getFee());
				Reservation reservation = reservationDao.create(connection,
						base);

				/* Commit. */
				connection.commit();

				return reservation.getReservationId();

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw e;
			} catch (AlreadyInvalidatedException e) {
				connection.commit();
				throw e;
			} catch (AlreadyReservatedException e) {
				connection.commit();
				throw e;
			} catch (ReservationTimeExpiredException e) {
				connection.commit();
				throw e;
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void claimOffer(long reservationId, String email)
			throws InstanceNotFoundException, NotClaimableException {

		try (Connection connection = dataSource.getConnection()) {

			try {

				/* Prepare connection. */
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				Reservation reservation = reservationDao.findByReservationId(
						connection, reservationId);

				/* Reservation owned by other user */
				if (!reservation.getEmail().equals(email))
					throw new NotClaimableException(email);

				ReturnedOffer aux = findOffer(reservation.getOfferId());

				if (aux.getLimitApplicationDate().compareTo(
						Calendar.getInstance()) < 0)
					throw new NotClaimableException(aux.getOfferId(),
							aux.getLimitApplicationDate());

				/* State must be NOT_CLAIMED in order to claim a reservation */
				if (!reservation.getState().toString().equals("NOT_CLAIMED"))
					throw new NotClaimableException(
							reservation.getReservationId(), reservation
									.getState().toString());

				/* Do work. */
				reservation.setState("CLAIMED");
				reservationDao.update(connection, reservation);

				/* Commit. */
				connection.commit();

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw e;
			} catch (NotClaimableException e) {
				connection.commit();
				throw e;
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Reservation> findReservationByOfferId(long offerId)
			throws InstanceNotFoundException {
		try (Connection connection = dataSource.getConnection()) {
			return reservationDao.findByOfferId(connection, offerId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Reservation> findReservationByUser(String email, boolean showAll) {
		try (Connection connection = dataSource.getConnection()) {
			return reservationDao.findByUserId(connection, email, showAll);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void offerInvalidation(long offerId)
			throws InstanceNotFoundException, AlreadyInvalidatedException {

		try (Connection connection = dataSource.getConnection()) {

			try {
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				Offer offer = offerDao.find(connection, offerId);
				/* Offer is invalid already */
				if (!offer.isValid())
					throw new AlreadyInvalidatedException(offerId);

				List<Reservation> reservations = reservationDao.findByOfferId(
						connection, offerId);

				/* Loop sets reservations state to INVALID if NOT_CALIMED */
				for (Reservation r : reservations) {
					if (r.getState().equals("NOT_CLAIMED"))
						reservationDao.stateUpdate(connection,
								r.getReservationId(), ModelConstants.INVALID);
				}

				/* Offer is not longer valid */
				offer.setValid(false);
				offerDao.update(connection, offer);

				/* Commit. */
				connection.commit();

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw e;
			} catch (AlreadyInvalidatedException e) {
				connection.commit();
				throw e;
			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);

			} catch (RuntimeException | Error e) {
				connection.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}