package es.udc.ws.app.model.offer.test;

import java.sql.Connection;

public class Test {
	public static void main (String[] args) {
		try (Connection connection = ConnectionManager.getConnection()){
			
		}catch (Exception e) {
            e.printStackTrace(System.err);
        }
	}
}
