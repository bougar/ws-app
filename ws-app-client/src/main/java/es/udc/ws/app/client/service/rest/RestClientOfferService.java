package es.udc.ws.app.client.service.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
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
import es.udc.ws.app.xml.XmlCreationOfferDtoConversor;
import es.udc.ws.app.xml.XmlOfferDtoConversor;
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
					.Post(getEndpointAddress() + "movies")
					.bodyStream(toInputStream(offer),
							ContentType.create("application/xml")).execute()
					.returnResponse();
			validateStatusCode(HttpStatus.SC_CREATED, response);
			return XmlOfferDtoConversor.toOffer(response.getEntity()
					.getContent());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateOffer(CreationOfferDto offer)
			throws InputValidationException, InstanceNotFoundException,
			NotModifiableOfferException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeOffer(long offerId) throws InstanceNotFoundException,
			NotModifiableOfferException {
		// TODO Auto-generated method stub

	}

	@Override
	public OfferDto findOffer(long offerId) throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OfferDto> findOffers(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long reserveOffer(long offerId, String email, String creditCardNumber)
			throws InputValidationException, InstanceNotFoundException,
			AlreadyInvalidatedException, ReservationTimeExpiredException,
			AlreadyReservatedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void claimOffer(long reservationId, String email)
			throws InstanceNotFoundException, NotClaimableException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ReservationDto> findReservationByOfferId(long offerId)
			throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservationDto> findReservationByUser(String email,
			boolean state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void offerInvalidation(long offerId)
			throws InstanceNotFoundException, AlreadyInvalidatedException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserOfferDto> getUserOffersInfo(String user)
			throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		return null;
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

	private void validateStatusCode(int successCode, HttpResponse response) {

		int statusCode = response.getStatusLine().getStatusCode();

		/* Success? */
		if (statusCode == successCode) {
			return;
		}

		/* Handler error. */
		switch (statusCode) {
		default:
			throw new RuntimeException("HTTP error; status code = "
					+ statusCode);
		}

	}

}
