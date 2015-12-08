package es.udc.ws.app.client.ui;

public class OfferServiceClient {
	
	public static void main(String[] args) {
		if (args.length == 0) {
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
	 
	 
}
