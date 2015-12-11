package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.client.types.*;
import es.udc.ws.app.client.service.soap.wsdl.*;

public class ReservationToSoapReservationConversions {
	public static es.udc.ws.app.client.types.ReservationDto toClientReservation(
			es.udc.ws.app.client.service.soap.wsdl.ReservationDto r) {
		Calendar requestDate = Calendar.getInstance();
		requestDate.setTime(r.getRequestDate().toGregorianCalendar().getTime());
		return new es.udc.ws.app.client.types.ReservationDto(r.getReservationId(),r.getEmail(),
				r.getOfferId(), r.getState(), requestDate,
			    r.getCreditCardNumber(),
				r.getReservationPrice());
	}

	public static List<es.udc.ws.app.client.types.ReservationDto> toClientReservationList(
			List<es.udc.ws.app.client.service.soap.wsdl.ReservationDto> reservations) {
		List<es.udc.ws.app.client.types.ReservationDto> clientReservationsDto = new ArrayList<es.udc.ws.app.client.types.ReservationDto>();
		for (es.udc.ws.app.client.service.soap.wsdl.ReservationDto r: reservations)
			clientReservationsDto.add(ReservationToSoapReservationConversions.toClientReservation(r));
		return clientReservationsDto;
	}

}
