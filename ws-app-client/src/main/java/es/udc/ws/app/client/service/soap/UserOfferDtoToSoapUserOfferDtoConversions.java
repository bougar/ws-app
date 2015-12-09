package es.udc.ws.app.client.service.soap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserOfferDtoToSoapUserOfferDtoConversions {
	public static es.udc.ws.app.client.types.UserOfferDto toClientUserOfferDto(
			es.udc.ws.app.client.service.soap.wsdl.UserOfferDto userInfo) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(userInfo.getReservationDate().toGregorianCalendar()
				.getTime());
		return new es.udc.ws.app.client.types.UserOfferDto(
				userInfo.getDescription(), userInfo.getDiscountedPrice(), cal);
	}

	public static List<es.udc.ws.app.client.types.UserOfferDto> toClientUserOffersDtoList(
			List<es.udc.ws.app.client.service.soap.wsdl.UserOfferDto> usersInfo) {
		List<es.udc.ws.app.client.types.UserOfferDto> usersOfferDto = new ArrayList<es.udc.ws.app.client.types.UserOfferDto>();
		for ( es.udc.ws.app.client.service.soap.wsdl.UserOfferDto u : usersInfo)
			usersOfferDto.add(UserOfferDtoToSoapUserOfferDtoConversions.toClientUserOfferDto(u));
		return usersOfferDto;
	}
}
