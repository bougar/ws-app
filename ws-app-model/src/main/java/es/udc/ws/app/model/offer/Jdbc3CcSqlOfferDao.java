package es.udc.ws.app.model.offer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc3CcSqlOfferDao extends AbstractSqlOfferDao {

	@Override
	public Offer create(Connection connection, Offer o) {
		String queryString = "INSERT INTO Offer "
				+ "(name,description, limitReservationDate, limitApplicationDate, "
				+ "realPrice, discountedPrice, fee, valid, faceBookId) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				queryString,Statement.RETURN_GENERATED_KEYS)) {
			int i = 1;
			preparedStatement.setString(i++,o.getName());
			preparedStatement.setString(i++,o.getDescription());
			Timestamp date = o.getLimitReservationDate() != null ? 
					new Timestamp(o.getLimitReservationDate().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++,date);	
			date = o.getLimitApplicationDate() != null ? 
					new Timestamp(o.getLimitApplicationDate().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++,date);	
			preparedStatement.setFloat(i++,o.getRealPrice());
			preparedStatement.setFloat(i++,o.getDiscountedPrice());
			preparedStatement.setFloat(i++,o.getFee());
			preparedStatement.setBoolean(i++,o.isValid());
			preparedStatement.setString(i++, o.getFaceBookId());
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (!resultSet.next()) {
				throw new SQLException("JDBC driver did not return a generated key.");
			}
			long offerId = resultSet.getLong(1);
			
			return new Offer(offerId,o.getName(),o.getDescription(),o.getLimitReservationDate(),o.getLimitApplicationDate(),
					o.getRealPrice(),o.getDiscountedPrice(),o.getFee(),o.isValid(), o.getFaceBookId());
			
		}catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}
	

}
