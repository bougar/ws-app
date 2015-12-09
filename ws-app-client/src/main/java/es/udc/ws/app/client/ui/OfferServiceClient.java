package es.udc.ws.app.client.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.udc.ws.app.client.service.ClientOfferService;
import es.udc.ws.app.client.service.ClientOfferServiceFactory;
import es.udc.ws.app.client.types.*;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class OfferServiceClient {

	public static void main(String[] args) {
		if (args.length == 0) {
			printUsageAndExit();
		}

		ClientOfferService clientOfferService = ClientOfferServiceFactory
				.getInstance();

		if ("-a".equalsIgnoreCase(args[0])) {
			validateArgs(args, 8, new int[] {5,6,7});
			Calendar limitReservationDate = toDate(args[3]);
			Calendar limitApplicationDate = toDate(args[4]);
			Offer offer = new Offer(args[1],args[2],limitReservationDate,limitApplicationDate,Float.valueOf(args[5]),Float.valueOf(args[6]),Float.valueOf(args[7]),true);
			try {
				clientOfferService.addOffer(offer);
			} catch (InputValidationException e) {
				e.getMessage();
			}
		}
		
		else if ("-u".equalsIgnoreCase(args[0])){
			validateArgs(args, 10, new int[] {1,5,6,7});
			Calendar limitReservationDate = toDate(args[3]);
			Calendar limitApplicationDate = toDate(args[4]);
			Offer offer = new Offer(Integer.valueOf(args[1]),args[2],args[3],limitReservationDate,limitApplicationDate,Float.valueOf(args[5]),Float.valueOf(args[6]),Float.valueOf(args[7]),true);
			try {
				clientOfferService.updateOffer(offer);
			} catch (InputValidationException e) {
				e.getMessage();
			} catch (InstanceNotFoundException e) {
				e.getMessage();
			} catch (NotModifiableOfferException e) {
				e.getMessage();
			}
		}
		
		else if ("-r".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {1});
			try {
				clientOfferService.removeOffer(Integer.valueOf(args[1]));
			} catch (NumberFormatException e) {
				e.getMessage();
			} catch (InstanceNotFoundException e) {
				e.getMessage();
			} catch (NotModifiableOfferException e) {
				e.getMessage();
			}
		}
		
		else if ("-g".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {1});
			try {
				clientOfferService.findOffer(Integer.valueOf(args[1]));
			} catch (NumberFormatException e) {
				e.getMessage();
			} catch (InstanceNotFoundException e) {
				e.getMessage();
			}
		}
		
		else if ("-f".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {});
			clientOfferService.findOffers(args[1]);
			
		}
		
		else if ("-reserve".equalsIgnoreCase(args[0])){
			validateArgs(args,4,new int[] {1});
			try {
				clientOfferService.reserveOffer(Integer.valueOf(args[1]), args[2], args[3]);
			} catch (NumberFormatException e) {
				e.getMessage();
			} catch (InputValidationException e) {
				e.getMessage();
			} catch (InstanceNotFoundException e) {
				e.getMessage();
			} catch (AlreadyInvalidatedException e) {
				e.getMessage();
			} catch (ReservationTimeExpiredException e) {
				e.getMessage();
			} catch (AlreadyReservatedException e) {
				e.getMessage();
			}
		}
		
		else if ("-claim".equalsIgnoreCase(args[0])){
			validateArgs(args,3,new int[] {1});
			try {
				clientOfferService.claimOffer(Integer.valueOf(args[1]), args[2]);
			} catch (NumberFormatException e) {
				e.getMessage();
			} catch (InstanceNotFoundException e) {
				e.getMessage();
			} catch (NotClaimableException e) {
				e.getMessage();
			}
		}
		
		else if ("-getReservation".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {});
			try {
				clientOfferService.findReservationByOfferId(Integer.valueOf(args[1]));
			} catch (NumberFormatException e) {
				e.getMessage();
			} catch (InstanceNotFoundException e) {
				e.getMessage();
			}
		}
		
		else if ("-findUserReservations".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {});
			clientOfferService.findReservationByUser(args[1], true);
			
		}
		
		else if ("-getUserOffers".equalsIgnoreCase(args[0])){
			validateArgs(args,2,new int[] {});
			try {
				clientOfferService.getUserOffersInfo(args[1]);
			} catch (InstanceNotFoundException e) {
				e.getMessage();
			}
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
		System.err.println("Bad date format: dd-MM-yyyy'T'HH:mm");
		System.exit(-1);
	}
}
