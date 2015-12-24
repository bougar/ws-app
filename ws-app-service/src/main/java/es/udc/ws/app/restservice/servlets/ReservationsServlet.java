package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.ReservationDto;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.app.model.offerservice.OfferServiceFactory;
import es.udc.ws.app.model.reservation.Reservation;
import es.udc.ws.app.serviceutil.ReservationToReservationDtoConversor;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlReservationDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class ReservationsServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String offerIdParameter = req.getParameter("offerId");
		if (offerIdParameter == null) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'offerId' is mandatory")),
							null);
			return;
		}
		Long offerId;
		try {
			offerId = Long.valueOf(offerIdParameter);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'offerId' is invalid '"
													+ offerIdParameter + "'")),
							null);

			return;
		}
		String email = req.getParameter("email");
		if (email == null) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'email' is mandatory")),
							null);
			return;
		}
		String creditCardNumber = req.getParameter("creditCardNumber");
		if (creditCardNumber == null) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "parameter 'creditCardNumber' is mandatory")),
							null);

			return;
		}
		Reservation reservation = null;
		try {
			OfferServiceFactory.getService().reserveOffer(offerId, email,
					creditCardNumber);
			
			// Proceso no muy limpio para obtener la reserva, no podemos llamar
			// al Dao//
			List<Reservation> reservations = OfferServiceFactory.getService()
					.findReservationByOfferId(offerId);
			
			for (Reservation r : reservations)
				if (r.getEmail().equals(email))
					reservation = r;
		} catch (InstanceNotFoundException ex) {
			ServletUtils
					.writeServiceResponse(resp,
							HttpServletResponse.SC_NOT_FOUND,
							XmlExceptionConversor
									.toInstanceNotFoundException(ex), null);
			return;
		} catch (AlreadyInvalidatedException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_GONE,
					XmlExceptionConversor.toAlreadyInvalidatedException(ex),
					null);
			return;
		} catch (InputValidationException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_BAD_REQUEST,
					XmlExceptionConversor.toInputValidationExceptionXml(ex),
					null);
			return;
		} catch (ReservationTimeExpiredException ex) {
			ServletUtils
					.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
							XmlExceptionConversor
									.toReservationTimeExpiredException(ex),
							null);
			return;
		} catch (AlreadyReservatedException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_GONE,
					XmlExceptionConversor.toAlreadyReservatedException(ex),
					null);
			return;
		}

		ReservationDto reservationDto = ReservationToReservationDtoConversor
				.toReservationDto(reservation);

		String reservationURL = ServletUtils.normalizePath(req.getRequestURL()
				.toString()) + "/" + reservation.getReservationId();

		Map<String, String> headers = new HashMap<>(1);
		headers.put("Location", reservationURL);

		ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
				XmlReservationDtoConversor.toResponse(reservationDto), headers);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path == null || path.length() == 0) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid reservation id")), null);
			return;
		}
		String reservationIdAsString = path.substring(1);
		Long reservationId;
		try {
			reservationId = Long.valueOf(reservationIdAsString);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid reservation id '"
													+ reservationIdAsString)), null);
			return;
		}
		Reservation reservation = null;
		try {
			sale = MovieServiceFactory.getService().findSale(saleId);
			// Proceso no muy limpio para obtener la reserva, no podemos llamar
			// al Dao//
			List<Reservation> reservations = OfferServiceFactory.getService()
					.findReservationByOfferId(offerId);
		} catch (InstanceNotFoundException ex) {
			ServletUtils
					.writeServiceResponse(resp,
							HttpServletResponse.SC_NOT_FOUND,
							XmlExceptionConversor
									.toInstanceNotFoundException(ex), null);
			return;
		} catch (SaleExpirationException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_GONE,
					XmlExceptionConversor.toSaleExpirationException(ex), null);

			return;
		}

		SaleDto saleDto = SaleToSaleDtoConversor.toSaleDto(sale);

		ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
				XmlSaleDtoConversor.toResponse(saleDto), null);

	}
}
