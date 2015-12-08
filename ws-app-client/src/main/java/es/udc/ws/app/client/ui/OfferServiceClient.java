package es.udc.ws.app.client.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.udc.ws.app.client.service.ClientOfferService;
import es.udc.ws.app.client.service.ClientOfferServiceFactory;
import es.udc.ws.app.client.types.*;

public class OfferServiceClient {

	public static void main(String[] args) {
		if (args.length == 0) {
			printUsageAndExit();
		}

		ClientOfferService clientOfferService = ClientOfferServiceFactory
				.getInstance();

		if ("-a".equalsIgnoreCase(args[0])) {
			validateArgs(args, 9, new int[] {5,6,7});
			Calendar limitReservationDate = toDate(args[2]);
			Calendar limitApplicationDate = toDate(args[3]);
			Offer offer = new Offer(args[0],args[1],limitReservationDate,limitApplicationDate,Float.valueOf(args[4]),Float.valueOf(args[5]),Float.valueOf(args[6]),true);
			
		}
		
		else if ("-u".equalsIgnoreCase(args[0])){
			validateArgs(args, 10, new int[] {1,5,6,7});
			Calendar limitReservationDate = toDate(args[3]);
			Calendar limitApplicationDate = toDate(args[4]);
			Offer offer = new Offer(Integer.valueOf(args[1]),args[2],args[3],limitReservationDate,limitApplicationDate,Float.valueOf(args[5]),Float.valueOf(args[6]),Float.valueOf(args[7]),true);
		}
		
		else if ("-r".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {1});
		}
		
		else if ("-g".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {1});
		}
		
		else if ("-f".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {});
		}
		
		else if ("-reserve".equalsIgnoreCase(args[0])){
			validateArgs(args,4,new int[] {1});
		}
		
		else if ("-claim".equalsIgnoreCase(args[0])){
			validateArgs(args,3,new int[] {1});
		}
		
		else if ("-getReservation".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {});
		}
		
		else if ("-findUserReservation".equalsIgnoreCase(args[0])){
			validateArgs(args,3,new int[] {});
		}
		
		else if ("-getUserOffers".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {});
		}
		
		else {
			printUsageAndExit();
		}
		
	}

	public static void validateArgs(String[] args, int expectedArgs,
			int[] numericArguments) {

		if (expectedArgs != args.length) {
			printUsageAndExit();
		}

		for (int i = 0; i < numericArguments.length; i++) {
			int position = numericArguments[i];
			try {
				Double.parseDouble(args[position]);
			} catch (NumberFormatException n) {
				printUsageAndExit();
			}
		}
	}

	private static void printUsageAndExit() {
		printUsage();
		System.exit(-1);
	}

	private static void printUsage() {
		System.out.println("Procedure is being constructed");
	}
	
	private static Calendar toDate(String dateString){
		Calendar cal = Calendar.getInstance();
		DateFormat format= new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
		try {
			cal.setTime(format.parse(dateString));
		} catch (ParseException e) {
			printDateFormatAndExit();
		}
		return cal;
	}
	
	private static void printDateFormatAndExit() {
		printDateFormat();
	}

	private static void printDateFormat() {
		System.exit(-1);
	}
}
