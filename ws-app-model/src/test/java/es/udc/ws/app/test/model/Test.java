package es.udc.ws.app.test.model;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.model.offer.Jdbc3CcSqlOfferDao;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.reservation.*;
public class Test {
	public static void main (String[] args) {
		try (Connection connection = ConnectionManager.getConnection()){
			Jdbc3CcSqlOfferDao dao = new Jdbc3CcSqlOfferDao();
			Jdbc3CcSqlReservationDao daor = new Jdbc3CcSqlReservationDao();
			Calendar date = Calendar.getInstance();
			Offer o = new Offer("zapatos ricos","marcelo nose",date,date,(float) 8.8,(float) 7.7,(float) 0.5, true );
			Offer aux = dao.create(connection, o);
			daor.stateUpdate(connection, 1, EnumState.INVALID);
		}catch (Exception e) {
            e.printStackTrace(System.err);
        }
	}
}
