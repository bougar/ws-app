package es.udc.ws.app.test.model.offerservice;

import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import static es.udc.ws.app.model.util.ModelConstants.OFFER_DATA_SOURCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.app.model.offer.Offer;
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
	private final long NON_EXISTENT_RESERVATION_ID = -1;
	private final String USER_ID = "ws-user";

	private final String VALID_CREDIT_CARD_NUMBER = "1234567890123456";
	private final String INVALID_CREDIT_CARD_NUMBER = "";

	private static OfferService offerService = null;

	private static SqlReservationDao reservationDao = null;

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

	}

	private Offer getValidOffer(String name) {
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		endDate.add(Calendar.DATE, 5);
		return new Offer(name, "Offer description", startDate, endDate,
				(float) 10.5, (float) 8, (float) 5, true);
	}

	private Offer getValidOffer() {
		return getValidOffer("Valid offer");
	}

	private Offer createOffer(Offer offer) {

		Offer addedOffer = null;
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

	private void updateRervation(Reservation reservation) {

		DataSource dataSource = DataSourceLocator
				.getDataSource(OFFER_DATA_SOURCE);

		try (Connection connection = dataSource.getConnection()) {

			try {

				/* Prepare connection. */
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);

				/* Do work. */
				reservationDao.update(connection, reservation);

				/* Commit. */
				connection.commit();

			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw new RuntimeException(e);
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

	@Test
	public void testAddMovieAndFindMovie() throws InputValidationException,
			InstanceNotFoundException, NotModifiableOfferException {

		Offer offer = getValidOffer();
		Offer addedOffer = null;

		addedOffer = offerService.addOffer(offer);
		Offer foundOffer = offerService.findOffer(addedOffer.getOfferId());

		assertEquals(addedOffer, foundOffer);

		// Clear Database
		removeOffer(addedOffer.getOfferId());

	}

	@Test
	public void testAddInvalidOffer() {

		Offer offer = getValidOffer();
		Offer addedOffer = null;
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
			limitReservationDate.add(Calendar.DATE, 5);
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

			// Check movie runtime <= MAX_RUNTIME
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setRuntime((short) (MAX_RUNTIME + 1));
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie description not null
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setDescription(null);
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie description not null
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setDescription("");
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie price >= 0
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setPrice((short) -1);
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie price <= MAX_PRICE
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setRuntime((short) (MAX_PRICE + 1));
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie creationDate not null
			exceptionCatched = false;
			movie = getValidMovie();
			movie.setCreationDate(null);
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check movie creationDate in the past
			exceptionCatched = false;
			movie = getValidMovie();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, 1);
			movie.setCreationDate(calendar);
			try {
				addedMovie = movieService.addMovie(movie);
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

		} finally {
			if (!exceptionCatched) {
				// Clear Database
				removeMovie(addedMovie.getMovieId());
			}
		}

	}

}
