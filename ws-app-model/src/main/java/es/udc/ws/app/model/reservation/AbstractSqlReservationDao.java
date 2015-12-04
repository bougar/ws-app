package es.udc.ws.app.model.reservation;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlReservationDao implements SqlReservationDao {

	public void update(Connection connection, Reservation r)
			throws InstanceNotFoundException {
		/* Create string query */
		String queryString = "UPDATE Reservation "
				+ "SET email = ?, state = ?, requestDate = ?, "
				+ "creditCardNumber = ? " + "WHERE reservationId = ? ";
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setString(i++, r.getEmail());
			preparedStatement.setString(i++, r.getState());
			Timestamp date = r.getRequestDate() != null ? new Timestamp(r
					.getRequestDate().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, date);
			preparedStatement.setString(i++, r.getCreditCardNumber());
			preparedStatement.setLong(i++, r.getReservationId());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(r.getReservationId(),
						Offer.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Reservation> findByUserId(Connection connection, String email,
			boolean showAll) {
		String queryString = "SELECT email, offerId, state, requestDate, "
				+ "reservationId, creditCardNumber, reservationPrice, reservationFee FROM Reservation WHERE email = ?";

		if (!showAll) {
			queryString += " AND state = 'VALID'";
		}
		queryString += " ORDER BY reservationId";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Reservation> reservations = new ArrayList<Reservation>();

			while (resultSet.next()) {
				int i = 2;
				long offerId = resultSet.getLong(i++);
				String state = resultSet.getString(i++);
				Calendar requestDate = Calendar.getInstance();
				requestDate.setTime(resultSet.getTimestamp(i++));
				long reservationId = resultSet.getLong(i++);
				String creditCardNumber = resultSet.getString(i++);
				long reservationPrice = resultSet.getLong(i++);
				long reservationFee = resultSet.getLong(i++);
				reservations.add(new Reservation(email, offerId, state,
						requestDate, reservationId, creditCardNumber,
						reservationPrice, reservationFee));
			}

			return reservations;
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
	}

	public List<Reservation> findByOfferId(Connection connection, long offerId) {
		String queryString = "SELECT email, state, requestDate, "
				+ "reservationId, creditCardNumber, reservationPrice, reservationFee FROM Reservation WHERE offerId = ?";

		queryString += " ORDER BY reservationId";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			preparedStatement.setLong(1, offerId);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Reservation> reservations = new ArrayList<Reservation>();

			while (resultSet.next()) {
				int i = 1;
				String email = resultSet.getString(i++);
				String state = resultSet.getString(i++);
				Calendar requestDate = Calendar.getInstance();
				requestDate.setTime(resultSet.getTimestamp(i++));
				long reservationId = resultSet.getLong(i++);
				String creditCardNumber = resultSet.getString(i++);
				long reservationPrice = resultSet.getLong(i++);
				long reservationFee = resultSet.getLong(i++);
				reservations.add(new Reservation(email, offerId, state,
						requestDate, reservationId, creditCardNumber,
						reservationPrice, reservationFee));
			}

			return reservations;
		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
	}

	public boolean isOfferAlreadyReservated(Connection connection,
			long offerId, String user) {
		String queryString = "SELECT reservationId"
				+ " FROM Reservation WHERE offerId = ? AND email = ?";
		boolean isReservated = false;
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setLong(i++, offerId);
			preparedStatement.setString(i++, user);

			ResultSet updatedRows = preparedStatement.executeQuery();

			isReservated = (updatedRows.next());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return isReservated;

	}

	public void stateUpdate(Connection connection, long reservationId,
			String state) throws InstanceNotFoundException {

		String queryString = "UPDATE Reservation SET state = ?";
		queryString += " WHERE reservationId = ?";
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setString(i++, state);
			preparedStatement.setLong(i++, reservationId);

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(reservationId,
						Offer.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public Reservation findByReservationId(Connection connection,
			long reservationId) throws InstanceNotFoundException {
		String queryString = "SELECT offerId, email, state, requestDate, creditCardNumber, reservationPrice, reservationFee "
				+ "FROM Reservation WHERE reservationId = ?";
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setLong(i++, reservationId);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				throw new InstanceNotFoundException(reservationId,
						Offer.class.getName());
			}
			i = 1;

			long offerId = resultSet.getLong(i++);
			String email = resultSet.getString(i++);
			String state = resultSet.getString(i++);
			Calendar requestDate = Calendar.getInstance();
			requestDate.setTime(resultSet.getTimestamp(i++));
			String creditCardNumber = resultSet.getString(i++);
			long reservationPrice = resultSet.getLong(i++);
			long reservationFee = resultSet.getLong(i++);
			Reservation reservation = new Reservation(email, offerId, state,
					requestDate, reservationId, creditCardNumber,
					reservationPrice, reservationFee);

			return reservation;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void remove(Connection connection, long reservationId)
			throws InstanceNotFoundException {
		String queryString = "DELETE FROM Reservation WHERE"
				+ " reservationId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			connection.setAutoCommit(false);
			int i = 1;
			preparedStatement.setLong(i++, reservationId);
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(reservationId,
						Offer.class.getName());
			}

			connection.commit();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int howManyByOffer(Connection connection, long offerId) {
		String queryString = "SELECT COUNT(*) FROM Reservation WHERE" + " offerId = ?";
		int rows = 0;
		
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			connection.setAutoCommit(false);
			int i = 1;
			preparedStatement.setLong(i++, offerId);
			ResultSet updatedRows = preparedStatement.executeQuery();
			updatedRows.next();
			rows = updatedRows.getInt(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return rows;
	}
	public int howManyByUser(Connection connection, String user) {
		String queryString = "SELECT COUNT(*) FROM Reservation WHERE" + " email = ?";
		int rows = 0;
		
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			connection.setAutoCommit(false);
			int i = 1;
			preparedStatement.setString(i++, user);
			ResultSet updatedRows = preparedStatement.executeQuery();
			updatedRows.next();
			rows = updatedRows.getInt(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return rows;
	}
	
}
