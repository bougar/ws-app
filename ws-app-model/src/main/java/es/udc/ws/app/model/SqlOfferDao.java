package es.udc.ws.app.model;

import java.sql.Connection;
import java.util.List;
import java.util.Calendar;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface SqlOfferDao {
	public Offer create(Connection connection,Offer o);
	
	public void update(Connection connection,Offer o)
			throws InstanceNotFoundException;
	
	public void remove(Connection connection,long offerId)
			throws InstanceNotFoundException;
	
	public Offer find(Connection connection, long offerId)
			throws InstanceNotFoundException;
	
	public List<Offer> advancedFilter(Connection connection,String keywords,boolean isValid, Calendar data); 
	
	
}
