package es.udc.ws.app.serviceutil;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.dto.ReservationDto;
import es.udc.ws.app.model.reservation.Reservation;

public class ReservationToReservationDtoConversor {
	public static ReservationDto toReservationDto(Reservation r) {
		return new ReservationDto(r.getEmail(), r.getOfferId(), r.getState(),
				r.getRequestDate(), r.getCreditCardNumber(),
				r.getReservationPrice());
	}

	public static List<ReservationDto> toReservationDtoList(
			List<Reservation> reservations) {
		List<ReservationDto> reservationsDto = new ArrayList<ReservationDto>();
		for (Reservation r : reservations)
			reservationsDto.add(ReservationToReservationDtoConversor
					.toReservationDto(r));
		return reservationsDto;
	}
}
