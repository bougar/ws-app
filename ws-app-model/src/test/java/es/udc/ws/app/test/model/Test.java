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
			//Reservation r = new Reservation("mail@mail",1, EnumState.CLAIMED, date, "34632140"); 
			//daor.create(connection, r);
			List<Reservation> reservations = daor.findByUserId(connection, "user@mail", false);
			for (Reservation raus:reservations)
				System.out.println(raus);
			//List<Offer> offers=dao.advancedFilter(connection,"vilariño puede",false,null);
			//System.out.println(offers.get(0).getDescription());
			//String s =daor.findByUserId(connection, "user@mail", true).get(0).getEmail();
		}catch (Exception e) {
            e.printStackTrace(System.err);
        }
	}
}
