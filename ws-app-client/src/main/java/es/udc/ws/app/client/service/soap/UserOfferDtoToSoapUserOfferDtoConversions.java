package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.dto.UserOfferDto;

public class UserOfferDtoToSoapUserOfferDtoConversions {
	public static UserOfferDto toClientUserOfferDto(
			es.udc.ws.app.client.service.soap.wsdl.UserOfferDto userInfo) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(userInfo.getReservationDate().toGregorianCalendar()
				.getTime());
		return new UserOfferDto(
				userInfo.getDescription(), userInfo.getDiscountedPrice(), cal);
	}

	public static List<UserOfferDto> toClientUserOffersDtoList(
			List<es.udc.ws.app.client.service.soap.wsdl.UserOfferDto> usersInfo) {
		List<UserOfferDto> usersOfferDto = new ArrayList<UserOfferDto>();
		for ( es.udc.ws.app.client.service.soap.wsdl.UserOfferDto u : usersInfo)
			usersOfferDto.add(UserOfferDtoToSoapUserOfferDtoConversions.toClientUserOfferDto(u));
		return usersOfferDto;
	}
}
