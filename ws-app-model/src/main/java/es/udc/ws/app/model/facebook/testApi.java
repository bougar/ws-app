package es.udc.ws.app.model.facebook;

import java.util.Calendar;

import es.udc.ws.app.model.offer.Offer;

public class testApi {
	private static FacebookService facebook;
	public static void main(String[] args){
		facebook=FacebookServiceFactory.instance();
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.add(Calendar.DATE, 10);
		endDate.add(Calendar.DATE, 20);
		Offer o = new Offer("My offer", "Offer description", startDate, endDate,
				(float) 10.5, (float) 8, (float) 5, true);
		try {
		String a = facebook.addOffer(o);
		o = new Offer("My offer", "Offer description", startDate, endDate,
				(float) 10.5, (float) 9, (float) 5, true);
		String b = facebook.updateOffer(a, o);
		facebook.removeOffer(b);
		a = facebook.addOffer(o);
		System.out.println("The number of likes is: "+facebook.getOfferLikes(a));
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
}