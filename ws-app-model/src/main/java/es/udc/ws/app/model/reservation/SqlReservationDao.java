package es.udc.ws.app.model.reservation;

import java.sql.Connection;
import java.util.List;

import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlReservationDao {

	public Reservation create(Connection connection,Reservation r);
	
	public void update(Connection connection,Reservation r)
			throws InstanceNotFoundException;
	
	public List<Reservation> findByUserId(Connection c, String email, boolean isValid);
	
	public List<Reservation> findByOfferId(Connection c , long offerId);
	
	public void stateUpdate(Connection c, long offerId, EnumState state)
			throws InstanceNotFoundException;
	
}