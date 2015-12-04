package es.udc.ws.app.model.offer;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public abstract class AbstractSqlOfferDao implements SqlOfferDao {

	@Override
	public void update(Connection connection, Offer o)
			throws InstanceNotFoundException {
		/* Create string query */
		String queryString = "UPDATE Offer "
				+ "SET name = ?, description = ?, limitReservationDate = ?, "
				+ "limitApplicationDate = ?, realPrice = ?, discountedPrice = ?, "
				+ "fee = ?, valid = ? " + "WHERE offerId=?";
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setString(i++, o.getName());
			preparedStatement.setString(i++, o.getDescription());
			Timestamp date = o.getLimitReservationDate() != null ? new Timestamp(
					o.getLimitReservationDate().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, date);
			date = o.getLimitApplicationDate() != null ? new Timestamp(o
					.getLimitApplicationDate().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, date);
			preparedStatement.setFloat(i++, o.getRealPrice());
			preparedStatement.setFloat(i++, o.getDiscountedPrice());
			preparedStatement.setFloat(i++, o.getFee());
			preparedStatement.setBoolean(i++, o.isValid());
			preparedStatement.setLong(i++, o.getOfferId());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(o.getOfferId(),
						Offer.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remove(Connection connection, long offerId)
			throws InstanceNotFoundException {
		String queryString = "DELETE FROM Offer WHERE" + " offerId = ?";
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setLong(i++, offerId);
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(offerId,
						Offer.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Offer find(Connection connection, long offerId)
			throws InstanceNotFoundException {
		String queryString = "SELECT name, description, limitReservationDate, limitApplicationDate, "
				+ "realPrice, discountedPrice, fee, valid FROM Offer WHERE offerId = ?";
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setLong(i++, offerId);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				throw new InstanceNotFoundException(offerId,
						Offer.class.getName());
			}
			i = 1;

			String name = resultSet.getString(i++);
			String description = resultSet.getString(i++);
			Calendar limitReservationDate = Calendar.getInstance();
			limitReservationDate.setTime(resultSet.getTimestamp(i++));
			Calendar limitApplicationDate = Calendar.getInstance();
			limitApplicationDate.setTime(resultSet.getTimestamp(i++));
			float realPrice = resultSet.getFloat(i++);
			float discountedPrice = resultSet.getFloat(i++);
			float fee = resultSet.getFloat(i++);
			boolean isValid = resultSet.getBoolean(i++);

			return new Offer(offerId, name, description, limitReservationDate,
					limitApplicationDate, realPrice, discountedPrice, fee,
					isValid);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Offer> advancedFilter(Connection connection, String keywords,
			boolean isValid, Calendar data) {
		String[] words = keywords != null ? keywords.split(" ") : null;
		String queryString = "SELECT offerId, name, description, limitReservationDate, limitApplicationDate, "
				+ "realPrice, discountedPrice, fee, valid FROM Offer ";

		if (isValid) {
			queryString += "WHERE valid=1";
		} else {
			queryString += "WHERE valid=0 || valid=1";
		}
		if (words != null && words.length > 0) {
			queryString += " AND ";
			for (int i = 0; i < words.length; i++) {
				if (i > 0)
					queryString += " AND ";
				queryString += "LOWER(description) LIKE LOWER(?)";
			}

		}

		if (data != null) {
			queryString += " AND";
			queryString += " limitReservationDate >= ?";
		}
		queryString += " ORDER BY name";
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 0;
			if (words != null) {

				for (i = 0; i < words.length; i++) {
					preparedStatement.setString(i + 1, "%" + words[i] + "%");
				}
			}
			if (data != null)
				preparedStatement.setTimestamp(i + 1, new Timestamp(data
						.getTime().getTime()));

			ResultSet resultSet = preparedStatement.executeQuery();

			List<Offer> offers = new ArrayList<Offer>();

			while (resultSet.next()) {
				i = 1;
				long offerId = resultSet.getLong(i++);
				String name = resultSet.getString(i++);
				String description = resultSet.getString(i++);
				Calendar limitReservationDate = Calendar.getInstance();
				limitReservationDate.setTime(resultSet.getTimestamp(i++));
				Calendar limitApplicationDate = Calendar.getInstance();
				limitApplicationDate.setTime(resultSet.getTimestamp(i++));
				float realPrice = resultSet.getFloat(i++);
				float discountedPrice = resultSet.getFloat(i++);
				float fee = resultSet.getFloat(i++);
				boolean valid = resultSet.getBoolean(i++);
				offers.add(new Offer(offerId, name, description,
						limitReservationDate, limitApplicationDate, realPrice,
						discountedPrice, fee, valid));
			}
			return offers;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
