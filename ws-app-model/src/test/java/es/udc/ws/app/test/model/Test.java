package es.udc.ws.app.test.model;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.model.offer.Jdbc3CcSqlOfferDao;
import es.udc.ws.app.model.offer.Offer;

public class Test {
	public static void main (String[] args) {
		try (Connection connection = ConnectionManager.getConnection()){
			Jdbc3CcSqlOfferDao dao = new Jdbc3CcSqlOfferDao();
			Calendar date = Calendar.getInstance();
			Offer o = new Offer("zapatos ricos","marcelo nose",date,date,(float) 8.8,(float) 7.7,(float) 0.5, true );
			Offer o2 = new Offer("zapatos ricos","carlos sise",date,date,(float) 8.8,(float) 7.7,(float) 0.5, false );
			Offer o3 = new Offer("zapatos ricos","vilariño puede",date,date,(float) 8.8,(float) 7.7,(float) 0.5, true );
			//dao.create(connection, o);
			//dao.create(connection, o2);
			//dao.create(connection, o3);
			List<Offer> offers=dao.advancedFilter(connection,"vilariño puede",false,null);
			System.out.println(offers.get(0).getDescription());
		}catch (Exception e) {
            e.printStackTrace(System.err);
        }
	}
}
