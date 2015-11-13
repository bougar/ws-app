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

public abstract class AbstractSqlReservationDao implements SqlReservationDao{
	@Override
	public void update(Connection connection, Reservation r)
			throws InstanceNotFoundException {
		/*Create string query*/
		String queryString = "UPDATE Reservation "
				+ "SET email = ?, state = ?, requestDate = ?, "
				+ "creditCardNumber = ?, "
				+ "WHERE reservationId = ? ";
		try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setLong(i++,r.getOfferId());
			preparedStatement.setString(i++,r.getEmail());
			preparedStatement.setString(i++,r.getState().name());
			Timestamp date = r.getRequestDate() != null ? 
					new Timestamp(r.getRequestDate().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++,date);	
			preparedStatement.setString(i++,r.getCreditCardNumber());
			preparedStatement.setLong(i++,r.getReservationId());
			
			int updatedRows = preparedStatement.executeUpdate();
			
			if (updatedRows == 0) {
				throw new InstanceNotFoundException(r.getReservationId(),Offer.class.getName());
			}
			
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Reservation> findByUserId(Connection connection, String email,
			boolean isValid) {
		String queryString = "SELECT email, offerId, state, requestDate, "
				+ "reservationId, creditCardNumber FROM Reservation WHERE email = ?";
		
		if(!isValid){
			queryString += " AND state = INVALID";
		}
		queryString += " ORDER BY reservationId";

		try(PreparedStatement preparedStatement = connection.prepareStatement(queryString)){
			preparedStatement.setString(1, email);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Reservation> reservations= new ArrayList<Reservation>();
			
			while(resultSet.next()) {
				int i = 2;
				long offerId = resultSet.getLong(i++);
				EnumState state = EnumState.valueOf(resultSet.getString(i++));
				Calendar requestDate = Calendar.getInstance();
				requestDate.setTime(resultSet.getTimestamp(i++));
				long reservationId = resultSet.getLong(i++);
				String creditCardNumber = resultSet.getString(i++);
				reservations.add( new Reservation(email, offerId, state, 
						requestDate, reservationId, creditCardNumber));
			}
			
			return reservations;
		}catch (SQLException e){
			throw new RuntimeException(e);
		
		}
	}
	
	@Override
	public List<Reservation> findByOfferId(Connection connection , long offerId){
		String queryString = "SELECT email, offerId, state, requestDate, "
				+ "reservationId, creditCardNumber FROM Reservation WHERE offerId = ?";
		
		queryString += " ORDER BY reservationId";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(queryString)){
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Reservation> reservations= new ArrayList<Reservation>();
			
			while(resultSet.next()) {
				int i = 1;
				String email = resultSet.getString(i++);
				EnumState state = EnumState.valueOf(resultSet.getString(i++));
				Calendar requestDate = Calendar.getInstance();
				requestDate.setTime(resultSet.getTimestamp(i++));
				long reservationId = resultSet.getLong(i++);
				String creditCardNumber = resultSet.getString(i++);
				reservations.add( new Reservation(email, offerId, state, 
						requestDate, reservationId, creditCardNumber));
			}
			
			return reservations;
		}catch (SQLException e){
			throw new RuntimeException(e);
		
		}
	}

	public void stateUpdate(Connection connection, long offerId, EnumState state)
			throws InstanceNotFoundException{
		
		String queryString = "UPDATE Reservation SET state = ?";
		queryString += " WHERE offerId = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setString(i++,state.name());
			preparedStatement.setLong(i++,offerId);
			
			int updatedRows = preparedStatement.executeUpdate();
			
			if (updatedRows == 0) {
				throw new InstanceNotFoundException(offerId,Offer.class.getName());
			}
			
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
		
	}

	public Reservation findByReservationId(Connection connection, long reservationId)
			throws InstanceNotFoundException{
			String queryString = "SELECT email, offerId, state, requestDate, creditCardNumber"
					+ "FROM Reservation WHERE reservationId = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)){
				int i = 1;
				preparedStatement.setLong(i++,reservationId);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				
				if (!resultSet.next()) {
					throw new InstanceNotFoundException(reservationId,Offer.class.getName()); 
				}
				i = 1;
				
				String email = resultSet.getString(i++);
				long offerId = resultSet.getLong(i++);
				EnumState state = EnumState.valueOf(resultSet.getString(i++));
				Calendar requestDate = Calendar.getInstance();
				String creditCardNumber = resultSet.getString(i++);
				
				return new Reservation(email, offerId, state, requestDate, creditCardNumber);
				
			}catch (SQLException e){
				throw new RuntimeException(e);
			}
		
}
}