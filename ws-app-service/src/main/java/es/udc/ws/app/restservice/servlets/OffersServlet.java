package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.dto.CreationOfferDto;
import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offer.ReturnedOffer;
import es.udc.ws.app.model.offerservice.OfferServiceFactory;
import es.udc.ws.app.serviceutil.OfferToOfferDtoConversor;
import es.udc.ws.app.serviceutil.OfferToCreationOfferDtoConversor;
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlCreationOfferDtoConversor;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlOfferDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class OffersServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		CreationOfferDto xmloffer;
		try {
			xmloffer = XmlCreationOfferDtoConversor.toOffer(req
					.getInputStream());
		} catch (ParsingException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											ex.getMessage())), null);

			return;

		}
		Offer offer = OfferToCreationOfferDtoConversor.toOffer(xmloffer);
		ReturnedOffer roffer = null;
		try {
			roffer = OfferServiceFactory.getService().addOffer(offer);
		} catch (InputValidationException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_BAD_REQUEST,
					XmlExceptionConversor.toInputValidationExceptionXml(ex),
					null);
			return;
		}
		OfferDto offerDto = OfferToOfferDtoConversor.toOfferDto(roffer);

		String offerURL = ServletUtils.normalizePath(req.getRequestURL()
				.toString()) + "/" + offer.getOfferId();
		Map<String, String> headers = new HashMap<>(1);
		headers.put("Location", offerURL);

		ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
				XmlOfferDtoConversor.toXml(offerDto), headers);
	}

	private static void updateOffer(Long offerId, HttpServletResponse resp,
			InputStream input) throws IOException {
		CreationOfferDto offerDto;
		try {
			offerDto = XmlCreationOfferDtoConversor.toOffer(input);
		} catch (ParsingException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											ex.getMessage())), null);
			return;

		}
		if (!offerId.equals(offerDto.getOfferId())) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid offerId")), null);
			return;
		}
		Offer offer = OfferToCreationOfferDtoConversor.toOffer(offerDto);
		try {
			OfferServiceFactory.getService().updateOffer(offer);
		} catch (InputValidationException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_BAD_REQUEST,
					XmlExceptionConversor.toInputValidationExceptionXml(ex),
					null);
			return;
		} catch (InstanceNotFoundException ex) {
			ServletUtils
					.writeServiceResponse(resp,
							HttpServletResponse.SC_NOT_FOUND,
							XmlExceptionConversor
									.toInstanceNotFoundException(ex), null);
			return;
		} catch (NotModifiableOfferException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_GONE,
					XmlExceptionConversor.toNotModifiableOfferException(ex),
					null);
		}
		ServletUtils.writeServiceResponse(resp,
				HttpServletResponse.SC_NO_CONTENT, null, null);
	}

	private static void invalidateOffer(Long offerId, HttpServletResponse resp)
			throws IOException {

		try {
			OfferServiceFactory.getService().offerInvalidation(offerId);
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_NO_CONTENT, null, null);
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
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
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
													+ "invalid offer id")),
							null);
			return;
		}
		String offerIdAsString = path.substring(1);
		String function = req.getParameter("function");
		Long offerId;
		try {
			offerId = Long.valueOf(offerIdAsString);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid offer id '"
													+ offerIdAsString + "'")),
							null);
			return;
		}
		if (function == null | function.equals("update")) {
			updateOffer(offerId,resp,req.getInputStream());
			return;
		} else if (function.equals("invalidate")) {
			invalidateOffer(offerId, resp);
			return;
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
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
													+ "invalid offer id")),
							null);
			return;
		}
		String offerIdAsString = path.substring(1);
		Long offerId;
		try {
			offerId = Long.valueOf(offerIdAsString);
		} catch (NumberFormatException ex) {
			ServletUtils
					.writeServiceResponse(
							resp,
							HttpServletResponse.SC_BAD_REQUEST,
							XmlExceptionConversor
									.toInputValidationExceptionXml(new InputValidationException(
											"Invalid Request: "
													+ "invalid offer id '"
													+ offerIdAsString + "'")),
							null);

			return;
		}
		try {
			OfferServiceFactory.getService().removeOffer(offerId);
		} catch (InstanceNotFoundException ex) {
			ServletUtils
					.writeServiceResponse(resp,
							HttpServletResponse.SC_NOT_FOUND,
							XmlExceptionConversor
									.toInstanceNotFoundException(ex), null);
			return;
		} catch (NotModifiableOfferException ex) {
			ServletUtils.writeServiceResponse(resp,
					HttpServletResponse.SC_GONE,
					XmlExceptionConversor.toNotModifiableOfferException(ex),
					null);
		}
		ServletUtils.writeServiceResponse(resp,
				HttpServletResponse.SC_NO_CONTENT, null, null);
	}

	/*
	 * 
	 * @Override protected void doGet(HttpServletRequest req,
	 * HttpServletResponse resp) throws ServletException, IOException { String
	 * path = ServletUtils.normalizePath(req.getPathInfo()); if (path == null ||
	 * path.length() == 0) { String keyWords = req.getParameter("keywords");
	 * List<Movie> movies = MovieServiceFactory.getService().findMovies(
	 * keyWords); List<MovieDto> movieDtos = MovieToMovieDtoConversor
	 * .toMovieDtos(movies); ServletUtils.writeServiceResponse(resp,
	 * HttpServletResponse.SC_OK, XmlMovieDtoConversor.toXml(movieDtos), null);
	 * } else { String movieIdAsString = path.substring(1); Long movieId; try {
	 * movieId = Long.valueOf(movieIdAsString); } catch (NumberFormatException
	 * ex) { ServletUtils .writeServiceResponse( resp,
	 * HttpServletResponse.SC_BAD_REQUEST, XmlExceptionConversor
	 * .toInputValidationExceptionXml(new InputValidationException(
	 * "Invalid Request: " + "invalid movie id'" + movieIdAsString + "'")),
	 * null);
	 * 
	 * return; } Movie movie; try { movie =
	 * MovieServiceFactory.getService().findMovie(movieId); } catch
	 * (InstanceNotFoundException ex) { ServletUtils.writeServiceResponse(resp,
	 * HttpServletResponse.SC_NOT_FOUND,
	 * XmlExceptionConversor.toInstanceNotFoundException(ex), null); return; }
	 * MovieDto movieDto = MovieToMovieDtoConversor.toMovieDto(movie);
	 * ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
	 * XmlMovieDtoConversor.toXml(movieDto), null); } }
	 */
}
