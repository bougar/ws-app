package es.udc.ws.app.model.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


public class Jdbc3CcSqlReservationDao extends AbstractSqlReservationDao {

	 @Override
	    public Reservation create(Connection connection, Reservation r) {

	        /* Create "queryString". */
	        String queryString = "INSERT INTO Reservation"
	                + " (offerId, email, state, requestDate, creditCardNumber)"
	                + " VALUES (?, ?, ?, ?, ?)";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(
	                        queryString, Statement.RETURN_GENERATED_KEYS)) {

	            /* Fill "preparedStatement". */
	            int i = 1;
	            preparedStatement.setLong(i++, r.getOfferId());
	            preparedStatement.setString(i++, r.getEmail());
	            preparedStatement.setString(i++, r.getState().name());
	            Timestamp date = r.getRequestDate() != null ? new Timestamp(
	                    r.getRequestDate().getTime().getTime()) : null;
	            preparedStatement.setTimestamp(i++, date);
	            preparedStatement.setString(i++, r.getCreditCardNumber());

	            /* Execute query. */
	            preparedStatement.executeUpdate();

	            /* Get generated identifier. */
	            ResultSet resultSet = preparedStatement.getGeneratedKeys();

	            if (!resultSet.next()) {
	                throw new SQLException(
	                        "JDBC driver did not return generated key.");
	            }
	            Long reservationId = resultSet.getLong(1);

	            /* Return movie. */
	            return new Reservation(r.getEmail(), r.getOfferId(), r.getState(),
	        			r.getRequestDate(),reservationId, r.getCreditCardNumber());

	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	    }
	
}
