package es.udc.ws.app.test.model.offerservice;

import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import static es.udc.ws.app.model.util.ModelConstants.OFFER_DATA_SOURCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offer.OfferToReturnedOffer;
import es.udc.ws.app.model.offer.ReturnedOffer;
import es.udc.ws.app.model.offer.SqlOfferDao;
import es.udc.ws.app.model.offer.SqlOfferDaoFactory;
import es.udc.ws.app.model.offerservice.OfferService;
import es.udc.ws.app.model.offerservice.OfferServiceFactory;
import es.udc.ws.app.model.reservation.Reservation;
import es.udc.ws.app.model.reservation.SqlReservationDao;
import es.udc.ws.app.model.reservation.SqlReservationDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

public class OfferServiceTest {
	private final long NON_EXISTENT_OFFER_ID = -1;

	private final String USER_ID = "ws-user";

	private final String VALID_CREDIT_CARD_NUMBER = "1234567890123456";
	private final String INVALID_CREDIT_CARD_NUMBER = "";

	private static OfferService offerService = null;

	private static SqlReservationDao reservationDao = null;
	private static SqlOfferDao offerDao = null;

	@BeforeClass
	public static void init() {

		/*
		 * Create a simple data source and add it to "DataSourceLocator" (this
		 * is needed to test "es.udc.ws.movies.model.movieservice.MovieService"
		 */
		DataSource dataSource = new SimpleDataSource();

		/* Add "dataSource" to "DataSourceLocator". */
		DataSourceLocator.addDataSource(OFFER_DATA_SOURCE, dataSource);

		offerService = OfferServiceFactory.getService();

		reservationDao = SqlReservationDaoFactory.getDao();
		offerDao = SqlOfferDaoFactory.getDao();

	}

	private Offer getValidOffer(String name) {
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.add(Calendar.DATE, 10);
		endDate.add(Calendar.DATE, 20);
		return new Offer(name, "Offer description", startDate, endDate,
				(float) 10.5, (float) 8, (float) 5, true);
	}

	private Offer getValidOffer() {
		return getValidOffer("Valid offer");
	}

	private ReturnedOffer createOffer(Offer offer) {

		ReturnedOffer addedOffer = null;
		try {
			addedOffer = offerService.addOffer(offer);
		} catch (InputValidationException e) {
			throw new RuntimeException(e);
		}
		return addedOffer;

	}

	private void removeOffer(Long offerId) {

		try {
			offerService.removeOffer(offerId);
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException(e);
		} catch (NotModifiableOfferException e) {
			throw new RuntimeException(e);
		}
	}

	private void updateOffer(Offer offer) throws InstanceNotFoundException {
		DataSource dataSource = DataSourceLocator
				.getDataSource(OFFER_DATA_SOURCE);

		try (Connection connection = dataSource.getConnection()) {
			offer.setFaceBookId("b");
			connection.setAutoCommit(false);
			offerDao.update(connection, offer);
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void removeReservation(long reservationId)
			throws InstanceNotFoundException {
		DataSource dataSource = DataSourceLocator
				.getDataSource(OFFER_DATA_SOURCE);

		try (Connection connection = dataSource.getConnection()) {
			reservationDao.remove(connection, reservationId);
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testFindOfferByKeywords() throws InstanceNotFoundException,
			NotModifiableOfferException, InputValidationException {
		ReturnedOffer offer1 = createOffer(getValidOffer());
		ReturnedOffer offer2 = createOffer(getValidOffer());
		ReturnedOffer offer3 = null;
		try {
			List<ReturnedOffer> offerList = offerService.findOffers(null, true,
					null);
			assertEquals(offerList.size(), 2);
			offer3 = createOffer(getValidOffer());
			offer3.setDescription("test");
			offerService.updateOffer(OfferToReturnedOffer.toOffer(offer3));
			offerList = offerService
					.findOffers("Offer description", true, null);
			assertEquals(offerList.size(), 2);
		} finally {
			removeOffer(offer1.getOfferId());
			removeOffer(offer2.getOfferId());
			if (offer3 != null)
				removeOffer(offer3.getOfferId());
		}
	}

	@Test
	public void testFindOfferByValid() throws InstanceNotFoundException,
			NotModifiableOfferException, AlreadyInvalidatedException {
		ReturnedOffer offer1 = createOffer(getValidOffer());
		ReturnedOffer offer2 = createOffer(getValidOffer());
		try {

			offerService.offerInvalidation(offer2.getOfferId());
			List<ReturnedOffer> offerList = offerService.findOffers(null, true,
					null);
			assertEquals(offerList.size(), 2);
			offerList = offerService.findOffers(null, false, null);
			assertEquals(offerList.size(), 1);
		} finally {
			removeOffer(offer1.getOfferId());
			removeOffer(offer2.getOfferId());
		}
	}

	@Test
	public void testFindOfferByVariousFields()
			throws InstanceNotFoundException, NotModifiableOfferException,
			InputValidationException {
		ReturnedOffer offer1 = createOffer(getValidOffer());
		Calendar sDate1 = offer1.getLimitReservationDate();
		try {
			List<ReturnedOffer> offerList = offerService.findOffers(null, true,
					sDate1);
			assertEquals(offerList.size(), 1);
			sDate1.add(Calendar.DATE, -1);
			offerList = offerService.findOffers(null, true, sDate1);
			assertEquals(offerList.size(), 1);
		} finally {
			removeOffer(offer1.getOfferId());
		}
	}

	@Test
	public void testFindReservationByUserId() throws InputValidationException,
			InstanceNotFoundException, AlreadyInvalidatedException,
			ReservationTimeExpiredException, AlreadyReservatedException {
		ReturnedOffer offer = createOffer(getValidOffer());
		ReturnedOffer invalid = createOffer(getValidOffer());
		long reservationUser = offerService.reserveOffer(offer.getOfferId(),
				USER_ID, VALID_CREDIT_CARD_NUMBER);
		long reservationInvalid = offerService.reserveOffer(
				invalid.getOfferId(), USER_ID, VALID_CREDIT_CARD_NUMBER);
		offerService.offerInvalidation(invalid.getOfferId());
		long reservationOther = offerService.reserveOffer(offer.getOfferId(),
				"otherUSer", VALID_CREDIT_CARD_NUMBER);
		List<Reservation> reservations = offerService.findReservationByUser(
				USER_ID, true);
		try {
			assertEquals(reservations.size(), 2);
			reservations = offerService.findReservationByUser(USER_ID, false);
			assertEquals(reservations.size(), 1);
		} finally {
			removeReservation(reservationUser);
			removeReservation(reservationInvalid);
			removeReservation(reservationOther);
			removeOffer(offer.getOfferId());
			removeOffer(invalid.getOfferId());
		}
	}

	@Test
	public void testAddOfferAndFindOffer() throws InputValidationException,
			InstanceNotFoundException, NotModifiableOfferException {
		ReturnedOffer addedOffer = null;
		try {
			Offer offer = getValidOffer();

			addedOffer = offerService.addOffer(offer);
			ReturnedOffer foundOffer = offerService.findOffer(addedOffer
					.getOfferId());
			addedOffer.setLikes(Long.valueOf(5));
			assertTrue(addedOffer.equals(foundOffer));

			// Clear Database
		} finally {
			removeOffer(addedOffer.getOfferId());
		}
	}

	@Test
	public void testAddInvalidOffer() {

		Offer offer = getValidOffer();
		ReturnedOffer addedOffer = null;
		boolean exceptionCatched = false;

		try {
			// Check name offer not null
			offer.setName(null);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check offer name not empty
			exceptionCatched = false;
			offer = getValidOffer();
			offer.setName("");
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check offer description not null
			exceptionCatched = false;
			offer = getValidOffer();
			offer.setDescription(null);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check offer description not empty
			exceptionCatched = false;
			offer = getValidOffer();
			offer.setDescription("");
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check limitResvationDate <= limitApplicationDate
			exceptionCatched = false;
			offer = getValidOffer();
			Calendar limitReservationDate = Calendar.getInstance();
			Calendar limitApplicationDate = Calendar.getInstance();
			limitReservationDate.add(Calendar.DATE, 15);
			offer.setLimitApplicationDate(limitApplicationDate);
			offer.setLimitReservationDate(limitReservationDate);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}

			assertTrue(exceptionCatched);

			// Check limitReservationDate not null
			exceptionCatched = false;
			offer = getValidOffer();
			offer.setLimitReservationDate(null);
			try {
				addedOffer = offerService.addOffer(offer);
				System.out.println("");
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}

			assertTrue(exceptionCatched);

			// Check limitApplicationDate not null
			exceptionCatched = false;
			offer = getValidOffer();
			offer.setLimitApplicationDate(null);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}

			assertTrue(exceptionCatched);

			// Check movie realprice >= 0
			exceptionCatched = false;
			offer = getValidOffer();
			offer.setRealPrice(-1);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie discountedprice >= 0
			exceptionCatched = false;
			offer = getValidOffer();
			offer.setDiscountedPrice(-1);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie fee >= 0 && fee <=100
			exceptionCatched = false;
			offer = getValidOffer();
			offer.setFee(-1);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie fee >= 0 && fee <=100
			exceptionCatched = false;
			offer = getValidOffer();
			offer.setFee(101);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check Discountedprice <= realprice
			exceptionCatched = false;
			offer = getValidOffer();
			offer.setDiscountedPrice(8);
			offer.setRealPrice(5);
			try {
				addedOffer = offerService.addOffer(offer);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

		} finally {
			if (!exceptionCatched) {
				// Clear Database
				removeOffer(addedOffer.getOfferId());
			}
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentOffer() throws InstanceNotFoundException {

		offerService.findOffer(NON_EXISTENT_OFFER_ID);

	}

	@Test
	public void testUpdateOffer() throws InputValidationException,
			InstanceNotFoundException, NotModifiableOfferException {

		ReturnedOffer offer = createOffer(getValidOffer());
		try {

			offer.setName("new name");
			offer.setDescription("new description");
			offer.setRealPrice((float) 20.6);
			offer.setDiscountedPrice((float) 19.95);
			offer.setFee(1);
			Calendar apliDate = Calendar.getInstance();
			Calendar resDate = Calendar.getInstance();
			apliDate.add(Calendar.DATE, 28);
			resDate.add(Calendar.DATE, 8);
			offer.setLimitApplicationDate(apliDate);
			offer.setLimitReservationDate(resDate);
			offer.setLikes(Long.valueOf(5));
			offerService.updateOffer(OfferToReturnedOffer.toOffer(offer));

			ReturnedOffer updatedOffer = offerService.findOffer(offer
					.getOfferId());
			assertTrue(offer.equals(updatedOffer));

		} finally {
			// Clear Database
			removeOffer(offer.getOfferId());
		}

	}

	@Test(expected = InputValidationException.class)
	public void testUpdateInvalidOffer() throws InputValidationException,
			InstanceNotFoundException, NotModifiableOfferException {
		ReturnedOffer offer = createOffer(getValidOffer());
		try {
			offer = offerService.findOffer(offer.getOfferId());
			offer.setName(null);
			offerService.updateOffer(OfferToReturnedOffer.toOffer(offer));
		} finally {
			removeOffer(offer.getOfferId());
		}
	}

	@Test(expected = InstanceNotFoundException.class)
	public void testUpdateNonExistentOffer() throws InputValidationException,
			InstanceNotFoundException, NotModifiableOfferException {

		Offer offer = getValidOffer();
		offer.setOfferId(NON_EXISTENT_OFFER_ID);
		offerService.updateOffer(offer);

	}

	@Test
	public void testNotModifiableOfferPrize() throws InstanceNotFoundException,
			InputValidationException, AlreadyInvalidatedException,
			ReservationTimeExpiredException, AlreadyReservatedException {
		Offer offer = getValidOffer();
		ReturnedOffer addedOffer = null;
		boolean exceptionCatched = false;
		addedOffer = createOffer(offer);
		long reservation = offerService.reserveOffer(addedOffer.getOfferId(),
				USER_ID, VALID_CREDIT_CARD_NUMBER);
		try {
			try {
				addedOffer
						.setDiscountedPrice(addedOffer.getDiscountedPrice() + 1);
				offerService.updateOffer(OfferToReturnedOffer
						.toOffer(addedOffer));
			} catch (NotModifiableOfferException e) {
				// removeReservation(reservation);
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
		} finally {
			removeReservation(reservation);
			removeOffer(addedOffer.getOfferId());
		}
	}

	@Test
	public void testNotModifiableOfferDate() throws InstanceNotFoundException,
			InputValidationException, AlreadyInvalidatedException,
			ReservationTimeExpiredException, AlreadyReservatedException {
		Offer offer = getValidOffer();
		ReturnedOffer addedOffer = null;
		boolean exceptionCatched = false;
		addedOffer = createOffer(offer);
		long reservation = offerService.reserveOffer(addedOffer.getOfferId(),
				USER_ID, VALID_CREDIT_CARD_NUMBER);
		try {
			try {
				Calendar date = Calendar.getInstance();
				date.add(Calendar.DATE, 11);
				addedOffer.setLimitApplicationDate(date);
				offerService.updateOffer(OfferToReturnedOffer
						.toOffer(addedOffer));
			} catch (NotModifiableOfferException e) {
				removeReservation(reservation);
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
		} finally {
			removeOffer(addedOffer.getOfferId());
		}
	}

	@Test(expected = InputValidationException.class)
	public void testReserveOfferWithInvalidCreditCard()
			throws InputValidationException, InstanceNotFoundException,
			AlreadyInvalidatedException, ReservationTimeExpiredException,
			AlreadyReservatedException {
		ReturnedOffer offer = createOffer(getValidOffer());
		try {
			offerService.reserveOffer(offer.getOfferId(), USER_ID,
					INVALID_CREDIT_CARD_NUMBER);
			// removeReservation(reservation);
		} finally {

			removeOffer(offer.getOfferId());
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testReserveNonExistingOffer() throws InputValidationException,
			InstanceNotFoundException, AlreadyInvalidatedException,
			ReservationTimeExpiredException, AlreadyReservatedException {

		long reservation = offerService.reserveOffer(NON_EXISTENT_OFFER_ID,
				USER_ID, VALID_CREDIT_CARD_NUMBER);
		removeReservation(reservation);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testRemoveOffer() throws InstanceNotFoundException,
			NotModifiableOfferException {

		ReturnedOffer offer = createOffer(getValidOffer());
		boolean exceptionCatched = false;
		try {
			offerService.removeOffer(offer.getOfferId());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(!exceptionCatched);

		offerService.findOffer(offer.getOfferId());

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testRemoveNonExistentOffer() throws InstanceNotFoundException,
			NotModifiableOfferException {

		offerService.removeOffer(NON_EXISTENT_OFFER_ID);

	}

	@Test
	public void testNotRemovableOffer() throws InstanceNotFoundException,
			InputValidationException, AlreadyInvalidatedException,
			ReservationTimeExpiredException, AlreadyReservatedException {
		Offer offer = getValidOffer();
		ReturnedOffer addedOffer = null;
		boolean exceptionCatched = false;
		addedOffer = createOffer(offer);
		long reservation = offerService.reserveOffer(addedOffer.getOfferId(),
				USER_ID, VALID_CREDIT_CARD_NUMBER);
		try {
			try {
				offerService.removeOffer(addedOffer.getOfferId());
			} catch (NotModifiableOfferException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
		} finally {
			removeReservation(reservation);
			removeOffer(addedOffer.getOfferId());
		}
	}

	@Test
	public void testInvalidateOffer() throws InstanceNotFoundException,
			AlreadyInvalidatedException, InputValidationException,
			ReservationTimeExpiredException, AlreadyReservatedException,
			SQLException {
		Offer offer = getValidOffer();
		ReturnedOffer addedOffer = null;
		long reservationId = 0;
		DataSource dataSource = DataSourceLocator
				.getDataSource(OFFER_DATA_SOURCE);
		try (Connection connection = dataSource.getConnection()) {
			addedOffer = createOffer(offer);
			reservationId = offerService.reserveOffer(addedOffer.getOfferId(),
					USER_ID, VALID_CREDIT_CARD_NUMBER);
			offerService.offerInvalidation(addedOffer.getOfferId());
			offer = OfferToReturnedOffer.toOffer(offerService
					.findOffer(addedOffer.getOfferId()));
			assertTrue(!(offer.isValid()));
			Reservation r = reservationDao.findByReservationId(connection,
					reservationId);
			assertEquals(r.getState(), "INVALID");
		} finally {
			removeReservation(reservationId);
			removeOffer(addedOffer.getOfferId());
		}

	}

	@Test(expected = AlreadyInvalidatedException.class)
	public void testAlreadyInvalidatedOffer() throws InstanceNotFoundException,
			AlreadyInvalidatedException {
		Offer offer = getValidOffer();
		ReturnedOffer addedOffer = createOffer(offer);
		try {
			offerService.offerInvalidation(addedOffer.getOfferId());
			offer = OfferToReturnedOffer.toOffer((offerService
					.findOffer(addedOffer.getOfferId())));
			offerService.offerInvalidation(addedOffer.getOfferId());
		} finally {
			removeOffer(addedOffer.getOfferId());
		}
	}

	@Test
	public void testClaimOffer() throws InstanceNotFoundException,
			NotClaimableException, AlreadyInvalidatedException,
			InputValidationException, ReservationTimeExpiredException,
			AlreadyReservatedException {
		Offer offer = getValidOffer();
		ReturnedOffer addedOffer = null;
		addedOffer = createOffer(offer);
		long reservationId = offerService.reserveOffer(addedOffer.getOfferId(),
				USER_ID, VALID_CREDIT_CARD_NUMBER);
		try {
			offerService.claimOffer(reservationId, USER_ID);
			List<Reservation> reservations = offerService
					.findReservationByOfferId(addedOffer.getOfferId());
			assertEquals(reservations.get(0).getState(), "CLAIMED");
		} finally {
			removeReservation(reservationId);
			removeOffer(addedOffer.getOfferId());
		}
	}

	@Test(expected = NotClaimableException.class)
	public void testAlreadyClaimedOffer() throws InputValidationException,
			InstanceNotFoundException, AlreadyInvalidatedException,
			NotClaimableException, ReservationTimeExpiredException,
			AlreadyReservatedException {
		ReturnedOffer offer = createOffer(getValidOffer());
		long reservation = 0;
		try {
			reservation = offerService.reserveOffer(offer.getOfferId(),
					USER_ID, VALID_CREDIT_CARD_NUMBER);
			offerService.claimOffer(reservation, USER_ID);
			offerService.claimOffer(reservation, USER_ID);
		} finally {
			removeReservation(reservation);
			removeOffer(offer.getOfferId());
		}
	}

	@Test(expected = NotClaimableException.class)
	public void testInvalidatedClaimed() throws InputValidationException,
			InstanceNotFoundException, AlreadyInvalidatedException,
			NotClaimableException, ReservationTimeExpiredException,
			AlreadyReservatedException {
		ReturnedOffer offer = createOffer(getValidOffer());
		long reservation = 0;
		try {
			reservation = offerService.reserveOffer(offer.getOfferId(),
					USER_ID, VALID_CREDIT_CARD_NUMBER);
			offerService.offerInvalidation(offer.getOfferId());
			offerService.claimOffer(reservation, USER_ID);
		} finally {
			removeReservation(reservation);
			removeOffer(offer.getOfferId());
		}
	}

	@Test
	public void testReserveOffer() throws InputValidationException,
			InstanceNotFoundException, AlreadyInvalidatedException,
			ReservationTimeExpiredException, AlreadyReservatedException {
		ReturnedOffer offer = createOffer(getValidOffer());
		try {
			long reservation = 0;
			reservation = offerService.reserveOffer(offer.getOfferId(),
					USER_ID, VALID_CREDIT_CARD_NUMBER);
			List<Reservation> reservations = offerService
					.findReservationByOfferId(offer.getOfferId());
			assertEquals(reservation, reservations.get(0).getReservationId());
			removeReservation(reservation);
		} finally {
			removeOffer(offer.getOfferId());
		}
	}

	@Test(expected = NotClaimableException.class)
	public void testClaimOutOfDate() throws InputValidationException,
			InstanceNotFoundException, AlreadyInvalidatedException,
			NotClaimableException, ReservationTimeExpiredException,
			InterruptedException, AlreadyReservatedException {
		ReturnedOffer offer = null;
		long reservationId = 0;
		try {

			Calendar expired = Calendar.getInstance();
			expired.add(Calendar.DATE, -1);
			offer = offerService.addOffer(getValidOffer());
			reservationId = offerService.reserveOffer(offer.getOfferId(),
					USER_ID, VALID_CREDIT_CARD_NUMBER);
			offer.setLimitApplicationDate(expired);
			updateOffer(OfferToReturnedOffer.toOffer(offer));
			offerService.claimOffer(reservationId, USER_ID);

		} finally {
			removeReservation(reservationId);
			removeOffer(offer.getOfferId());
		}
	}

	@Test(expected = ReservationTimeExpiredException.class)
	public void testReservationDateExpired() throws InputValidationException,
			InstanceNotFoundException, AlreadyInvalidatedException,
			NotClaimableException, ReservationTimeExpiredException,
			InterruptedException, AlreadyReservatedException {
		ReturnedOffer offer = null;
		try {
			offer = offerService.addOffer(getValidOffer());
			Calendar expired = Calendar.getInstance();
			expired.add(Calendar.DATE, -1);
			offer.setLimitReservationDate(expired);
			updateOffer(OfferToReturnedOffer.toOffer(offer));
			offerService.reserveOffer(offer.getOfferId(), USER_ID,
					VALID_CREDIT_CARD_NUMBER);

		} finally {
			removeOffer(offer.getOfferId());
		}
	}

	@Test(expected = AlreadyReservatedException.class)
	public void testAlreadyReservated() throws InputValidationException,
			InstanceNotFoundException, AlreadyInvalidatedException,
			NotClaimableException, ReservationTimeExpiredException,
			InterruptedException, AlreadyReservatedException {
		ReturnedOffer offer = null;
		long reservationId = -1;
		try {
			offer = offerService.addOffer(getValidOffer());
			reservationId = offerService.reserveOffer(offer.getOfferId(),
					USER_ID, VALID_CREDIT_CARD_NUMBER);
			offerService.reserveOffer(offer.getOfferId(), USER_ID,
					VALID_CREDIT_CARD_NUMBER);

		} finally {
			removeReservation(reservationId);
			removeOffer(offer.getOfferId());
		}
	}

}
