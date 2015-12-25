package es.udc.ws.app.client.service.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import es.udc.ws.app.client.service.ClientOfferService;
import es.udc.ws.app.client.types.UserOfferDto;
import es.udc.ws.app.dto.CreationOfferDto;
import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.dto.ReservationDto;
import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlCreationOfferDtoConversor;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlOfferDtoConversor;
import es.udc.ws.app.xml.XmlReservationDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class RestClientOfferService implements ClientOfferService {
	private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientMovieService.endpointAddress";
	private String endpointAddress;

	@Override
	public OfferDto addOffer(CreationOfferDto offer)
			throws InputValidationException {
		try {
			HttpResponse response = Request
					.Post(getEndpointAddress() + "offers")
					.bodyStream(toInputStream(offer),
							ContentType.create("application/xml")).execute()
					.returnResponse();
			validateStatusCode(HttpStatus.SC_CREATED, response);
			return XmlOfferDtoConversor.toOffer(response.getEntity()
					.getContent());
		} catch (InputValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateOffer(CreationOfferDto offer)
			throws InputValidationException, InstanceNotFoundException,
			NotModifiableOfferException {
		try {
			HttpResponse response = Request
					.Put(getEndpointAddress() + "offers/" + +offer.getOfferId())
					.bodyStream(toInputStream(offer),
							ContentType.create("application/xml")).execute()
					.returnResponse();
			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);
		} catch (InputValidationException | InstanceNotFoundException
				| NotModifiableOfferException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void removeOffer(long offerId) throws InstanceNotFoundException,
			NotModifiableOfferException {
		try {
			HttpResponse response = Request
					.Delete(getEndpointAddress() + "offers/" + offerId)
					.execute().returnResponse();
			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);
		} catch (InstanceNotFoundException | NotModifiableOfferException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public OfferDto findOffer(long offerId) throws InstanceNotFoundException {
		try {
			HttpResponse response = Request
					.Get(getEndpointAddress() + "offers/" + offerId).execute()
					.returnResponse();
			validateStatusCode(HttpStatus.SC_OK, response);
			return XmlOfferDtoConversor.toOffer(response.getEntity()
					.getContent());
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<OfferDto> findOffers(String keywords) {
		try {
			HttpResponse response = Request
					.Get(getEndpointAddress() + "offers?keywords="
							+ URLEncoder.encode(keywords, "UTF-8")).execute()
					.returnResponse();
			validateStatusCode(HttpStatus.SC_OK, response);
			return XmlOfferDtoConversor.toOffers(response.getEntity()
					.getContent());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public long reserveOffer(long offerId, String email, String creditCardNumber)
			throws InputValidationException, InstanceNotFoundException,
			AlreadyInvalidatedException, ReservationTimeExpiredException,
			AlreadyReservatedException {
		try {
			HttpResponse response = Request
					.Post(getEndpointAddress() + "reservations")
					.bodyForm(
							Form.form().add("offerId", Long.toString(offerId))
									.add("email", email)
									.add("creditCardNumber", creditCardNumber)
									.build()).execute().returnResponse();
			validateStatusCode(HttpStatus.SC_CREATED, response);
			return XmlReservationDtoConversor.toReservation(
					response.getEntity().getContent()).getReservationId();
		} catch (InputValidationException | InstanceNotFoundException
				| AlreadyInvalidatedException | ReservationTimeExpiredException
				| AlreadyReservatedException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void claimOffer(long reservationId, String email)
			throws InstanceNotFoundException, NotClaimableException {
		try {
			HttpResponse response = Request
					.Put(getEndpointAddress() + "reservations/"
							+ +reservationId)
					.bodyForm(Form.form().add("email", email).build())
					.execute().returnResponse();
			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);
		} catch (InstanceNotFoundException | NotClaimableException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<ReservationDto> findReservationByOfferId(long offerId)
			throws InstanceNotFoundException {
		try {
			HttpResponse response = Request
					.Get(getEndpointAddress()
							+ "reservations/search?function=byOfferId&offerId="
							+ URLEncoder.encode(Long.toString(offerId), "UTF-8"))
					.execute().returnResponse();
			validateStatusCode(HttpStatus.SC_OK, response);
			return XmlReservationDtoConversor.toReservations(response
					.getEntity().getContent());
		} catch (InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ReservationDto> findReservationByUser(String email,
			boolean state) {
		try {
			HttpResponse response = Request
					.Get(getEndpointAddress()
							+ "reservations/search?function=byUser&email="
							+ URLEncoder.encode(email, "UTF-8")
							+ "&state="
							+ URLEncoder.encode(Boolean.toString(state),
									"UTF-8")).execute().returnResponse();

			validateStatusCode(HttpStatus.SC_OK, response);
			return XmlReservationDtoConversor.toReservations(response
					.getEntity().getContent());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void offerInvalidation(long offerId)
			throws InstanceNotFoundException, AlreadyInvalidatedException {
		try {
			HttpResponse response = Request
					.Put(getEndpointAddress() + "offers/"
							+ +offerId + "?function=invalidate").execute().returnResponse();
			validateStatusCode(HttpStatus.SC_NO_CONTENT, response);
		} catch (InstanceNotFoundException | AlreadyInvalidatedException e){
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<UserOfferDto> getUserOffersInfo(String user)
			throws InstanceNotFoundException {
		try {
			HttpResponse response = Request
					.Get(getEndpointAddress() + "reservations/search?function=userInfor&user="
							+ URLEncoder.encode(user, "UTF-8")).execute()
					.returnResponse();
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private synchronized String getEndpointAddress() {
		if (endpointAddress == null) {
			endpointAddress = ConfigurationParametersManager
					.getParameter(ENDPOINT_ADDRESS_PARAMETER);
		}
		return endpointAddress;
	}

	private InputStream toInputStream(CreationOfferDto offer) {

		try {

			ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

			outputter.output(XmlCreationOfferDtoConversor.toXml(offer),
					xmlOutputStream);

			return new ByteArrayInputStream(xmlOutputStream.toByteArray());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void validateStatusCode(int successCode, HttpResponse response)
			throws InputValidationException, ParsingException,
			IllegalStateException, InstanceNotFoundException,
			AlreadyInvalidatedException, AlreadyReservatedException,
			NotClaimableException, NotModifiableOfferException,
			ReservationTimeExpiredException {
		try {
			int statusCode = response.getStatusLine().getStatusCode();

			/* Success? */
			if (statusCode == successCode) {
				return;
			}

			/* Handler error. */

			switch (statusCode) {
			case HttpStatus.SC_NOT_FOUND:
				throw XmlExceptionConversor
						.fromInstanceNotFoundExceptionXml(response.getEntity()
								.getContent());
			case HttpStatus.SC_BAD_REQUEST:
				throw XmlExceptionConversor
						.fromInputValidationExceptionXml(response.getEntity()
								.getContent());
			case HttpStatus.SC_GONE:
				chooseGoneException(response.getEntity().getContent());
			default:
				throw new RuntimeException("HTTP error; status code = "
						+ statusCode);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private static void chooseGoneException(InputStream input)
			throws ParsingException, AlreadyInvalidatedException,
			AlreadyReservatedException, NotClaimableException,
			NotModifiableOfferException, ReservationTimeExpiredException {
		Integer code = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(input);
			Element rootElement = document.getRootElement();
			code = Integer.valueOf(rootElement.getChildTextTrim("internalCode",
					XmlOfferDtoConversor.XML_NS));
			input.reset();
			
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		}
		switch (code) {
		case 1:
			throw XmlExceptionConversor
					.fromAlreadyInvalidatedExceptionXml(input);
		case 2:
			throw XmlExceptionConversor
					.fromAlreadyReservatedExceptionXml(input);
		case 3:
			throw XmlExceptionConversor.fromNotClaimableExceptionXml(input);
		case 4:
			throw XmlExceptionConversor
					.fromNotModifiableOfferExceptionXml(input);
		case 5:
			throw XmlExceptionConversor
					.fromReservationTimeExpiredExceptionXml(input);
		default:
			throw new ParsingException("Unknown internal error code.");
		}

	}
}
