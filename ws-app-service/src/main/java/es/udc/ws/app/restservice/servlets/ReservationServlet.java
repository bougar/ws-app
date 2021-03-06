package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.ReservationDto;
import es.udc.ws.app.dto.UserOfferDto;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.app.model.offer.ReturnedOffer;
import es.udc.ws.app.model.offerservice.OfferServiceFactory;
import es.udc.ws.app.model.reservation.Reservation;
import es.udc.ws.app.serviceutil.ReservationToReservationDtoConversor;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlReservationDtoConversor;
import es.udc.ws.app.xml.XmlUserOfferDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class ReservationServlet extends HttpServlet {
	private void reserve(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, InputValidationException,
			InstanceNotFoundException, AlreadyInvalidatedException,
			ReservationTimeExpiredException, AlreadyReservatedException {
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
		OfferServiceFactory.getService().reserveOffer(offerId, email,
				creditCardNumber);

		/*
		 * reserveOffer returns 'long' value, we need 'Reservation' to call the
		 * 'DtoConversor', there is no method 'findByReservationId' in the
		 * service, so we obtain the reservation in a dirty way
		 */

		List<Reservation> reservations = OfferServiceFactory.getService()
				.findReservationByOfferId(offerId);
		for (Reservation r : reservations)
			if (email.equals(r.getEmail()))
				reservation = r;
		ReservationDto reservationDto = ReservationToReservationDtoConversor
				.toReservationDto(reservation);

		String reservationURL = ServletUtils.normalizePath(req.getRequestURL()
				.toString()) + "/" + reservation.getReservationId();

		Map<String, String> headers = new HashMap<>(1);
		headers.put("Location", reservationURL);

		ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
				XmlReservationDtoConversor.toResponse(reservationDto), headers);
	}
	private void claim(HttpServletRequest req, HttpServletResponse resp,String [] parameters) throws IOException, InstanceNotFoundException, NotClaimableException{
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
		String reservationIdAsString = parameters[1];
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
													+ reservationIdAsString
													+ "'")), null);
			return;
		}
			OfferServiceFactory.getService().claimOffer(reservationId,
					email);
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_NO_CONTENT, null, null);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path == null || path.length() == 0) {
			try {
				reserve(req, resp);
			} catch (InstanceNotFoundException ex) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_NOT_FOUND,
						XmlExceptionConversor.toInstanceNotFoundException(ex),
						null);
				return;
			} catch (InputValidationException ex) {
				ServletUtils
						.writeServiceResponse(resp,
								HttpServletResponse.SC_BAD_REQUEST,
								XmlExceptionConversor
										.toInputValidationExceptionXml(ex),
								null);
				return;
			} catch (AlreadyInvalidatedException ex) {
				ServletUtils
						.writeServiceResponse(resp,
								HttpServletResponse.SC_GONE,
								XmlExceptionConversor
										.toAlreadyInvalidatedException(ex),
								null);
				return;
			} catch (AlreadyReservatedException ex) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_GONE,
						XmlExceptionConversor.toAlreadyReservatedException(ex),
						null);
				return;
			} catch (ReservationTimeExpiredException ex) {
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_GONE, XmlExceptionConversor
								.toReservationTimeExpiredException(ex), null);
				return;
			}

		} else {
			String[] parameters = path.split("/");
			if (parameters[2].equals("claimReservation")) {
				try{
					claim(req,resp,parameters);
				} catch (InstanceNotFoundException ex) {
					ServletUtils.writeServiceResponse(resp,
							HttpServletResponse.SC_NOT_FOUND,
							XmlExceptionConversor
									.toInstanceNotFoundException(ex), null);
					return;
				} catch (NotClaimableException ex) {
					ServletUtils.writeServiceResponse(resp,
							HttpServletResponse.SC_GONE,
							XmlExceptionConversor.toNotClaimableException(ex),
							null);
					return;
				}
			} else {
				ServletUtils
						.writeServiceResponse(
								resp,
								HttpServletResponse.SC_BAD_REQUEST,
								XmlExceptionConversor
										.toInputValidationExceptionXml(new InputValidationException(
												"Invalid Request: "
														+ "Invalid function")),
								null);
				return;

			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = ServletUtils.normalizePath(req.getPathInfo());
		if (path == null || path.length() == 0 || !path.equals("/search")) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid parameters")),
							null);
			return;
		}
		String function = req.getParameter("function");
		List<Reservation> reservations = null;
		try {
			if (function.equals("userInfo")) {

				String user = req.getParameter("user");
				reservations = OfferServiceFactory.getService()
						.findReservationByUser(user, true);
				List<UserOfferDto> userOffers = new ArrayList<UserOfferDto>();
				Calendar requestDate = null;
				String description = null;
				float discountedPrice;
				for (Reservation r : reservations) {
					ReturnedOffer offer = null;
					try {
						offer = OfferServiceFactory.getService().findOffer(
								r.getOfferId());
					} catch (InstanceNotFoundException e) {
						ServletUtils.writeServiceResponse(resp,
								HttpServletResponse.SC_NOT_FOUND,
								XmlExceptionConversor
										.toInstanceNotFoundException(e), null);
						return;
					}
					description = offer.getDescription();
					discountedPrice = offer.getDiscountedPrice();
					requestDate = r.getRequestDate();
					userOffers.add(new UserOfferDto(description,
							discountedPrice, requestDate));
				}
				ServletUtils.writeServiceResponse(resp,
						HttpServletResponse.SC_OK,
						XmlUserOfferDtoConversor.toXml(userOffers), null);
				return;

			} else if (function == null | function.equals("byOfferId")) {
				Long offerId = Long.valueOf(req.getParameter("offerId"));
				reservations = OfferServiceFactory.getService()
						.findReservationByOfferId(offerId);
			} else if (function.equals("byUser")) {
				String email = req.getParameter("email");
				boolean state = Boolean.valueOf(req.getParameter("state"));
				reservations = OfferServiceFactory.getService()
						.findReservationByUser(email, state);
			} else {
				ServletUtils
						.writeServiceResponse(
								resp,
								HttpServletResponse.SC_BAD_REQUEST,
								XmlExceptionConversor
										.toInputValidationExceptionXml(new InputValidationException(
												"Invalid function: " + "'"
														+ function + "'"
														+ " invalid parameters")),
								null);
				return;
			}

			List<ReservationDto> reservationDtos = ReservationToReservationDtoConversor
					.toReservationDtoList(reservations);
			ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
					XmlReservationDtoConversor.toXml(reservationDtos), null);
			return;

		} catch (InstanceNotFoundException ex) {
			ServletUtils
					.writeServiceResponse(resp,
							HttpServletResponse.SC_NOT_FOUND,
							XmlExceptionConversor
									.toInstanceNotFoundException(ex), null);
			return;
		}
	}
	/**/
}
