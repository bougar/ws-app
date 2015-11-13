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
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offer.SqlOfferDao;
import es.udc.ws.app.model.offer.SqlOfferDaoFactory;
import es.udc.ws.app.model.reservation.EnumState;
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

	public OfferServiceImpl() {
		dataSource = DataSourceLocator.getDataSource(OFFER_DATA_SOURCE);
		offerDao = SqlOfferDaoFactory.getDao();
		reservationDao = SqlReservationDaoFactory.getDao();
	}

	private void validateOffer(Offer offer) throws InputValidationException {
		PropertyValidator.validateMandatoryString("title", offer.getName());
		PropertyValidator.validateMandatoryString("description",
				offer.getDescription());
		PropertyValidator.validateTwoDates("limitReservationDate",
				offer.getLimitReservationDate(),
				offer.getLimitApplicationDate());
		PropertyValidator.validateFloat("realPrice", offer.getRealPrice(), 0,
				Float.MAX_VALUE);
		PropertyValidator.validateFloat("discountedPrice",
				offer.getDiscountedPrice(), 0, offer.getRealPrice());
		PropertyValidator.validateFloat("fee", offer.getFee(), 0, 100);
	}

	@Override
	public Offer addOffer(Offer offer) throws InputValidationException {
		validateOffer(offer);

		try (Connection connection = dataSource.getConnection()) {

			try {

				/* Prepare connection. */
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				/* Do work. */
				Offer createdOffer = offerDao.create(connection, offer);

				/* Commit. */
				connection.commit();

				return createdOffer;
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

				List<Reservation> reservations = reservationDao.findByOfferId(
						connection, offer.getOfferId());

				/* Checks if any user has already reserved the offer */

				/*
				 * If reservations > 0 we can only modify AppDate(later) and
				 * prize(cheaper)
				 */
				if (reservations.size() != 0) {

					if (offer.getLimitApplicationDate().compareTo(
							baseOffer.getLimitApplicationDate()) <= 0)
						throw new NotModifiableOfferException(
								offer.getOfferId());

					if (offer.getDiscountedPrice() >= baseOffer
							.getDiscountedPrice())
						throw new NotModifiableOfferException(
								offer.getOfferId());
				}

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

				Offer offer = offerDao.find(connection, offerId);

				List<Reservation> reservations = reservationDao.findByOfferId(
						connection, offer.getOfferId());

				/*
				 * An offer can only be removed if no one has already reserved
				 * it
				 */
				if (reservations.size() != 0)
					throw new NotModifiableOfferException(offerId);

				/* Do work. */
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
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Offer findOffer(long offerId) throws InstanceNotFoundException {

		try (Connection connection = dataSource.getConnection()) {
			return offerDao.find(connection, offerId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Offer> findOffers(String keywords, Boolean state, Calendar date) {
		try (Connection connection = dataSource.getConnection()) {
			return offerDao.advancedFilter(connection, keywords, state, date);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long reserveOffer(long offerId, String email, String creditCardNumber)
			throws InputValidationException, InstanceNotFoundException,
			AlreadyInvalidatedException {

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

				Calendar requestDate = Calendar.getInstance();
				Reservation base = new Reservation(email, offer.getOfferId(),
						EnumState.NOT_CLAIMED, requestDate, creditCardNumber);
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

				/* State must be NOT_CLAIMED in order to claim a reservation */
				if (!reservation.getState().toString().equals("NOT_CLAIMED"))
					throw new NotClaimableException(
							reservation.getReservationId(), reservation
									.getState().toString());

				/* Do work. */
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
	public List<Reservation> findReservationByUser(String email, boolean state) {
		try (Connection connection = dataSource.getConnection()) {
			return reservationDao.findByUserId(connection, email, state);
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
					if (r.getState().toString().equals("NOT_CLAIMED"))
						reservationDao.stateUpdate(connection,
								r.getReservationId(), EnumState.INVALID);
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