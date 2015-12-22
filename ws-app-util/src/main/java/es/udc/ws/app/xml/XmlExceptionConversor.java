package es.udc.ws.app.xml;

import es.udc.ws.app.exceptions.AlreadyInvalidatedException;
import es.udc.ws.app.exceptions.AlreadyReservatedException;
import es.udc.ws.app.exceptions.NotClaimableException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.ReservationTimeExpiredException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class XmlExceptionConversor {

	public final static String CONVERSION_PATTERN = "EEE, d MMM yyyy HH:mm:ss Z";

	public final static Namespace XML_NS = XmlOfferDtoConversor.XML_NS;

	public static InputValidationException fromInputValidationExceptionXml(
			InputStream ex) throws ParsingException {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();

			Element message = rootElement.getChild("message", XML_NS);

			return new InputValidationException(message.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static InstanceNotFoundException fromInstanceNotFoundExceptionXml(
			InputStream ex) throws ParsingException {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();

			Element instanceId = rootElement.getChild("instanceId", XML_NS);
			Element instanceType = rootElement.getChild("instanceType", XML_NS);

			return new InstanceNotFoundException(instanceId.getText(),
					instanceType.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static AlreadyInvalidatedException fromAlreadyInvalidatedExceptionXml(
			InputStream ex) throws ParsingException {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();

			Element offerId = rootElement.getChild("offerId", XML_NS);

			return new AlreadyInvalidatedException(Long.parseLong(offerId
					.getTextTrim()));
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static NotClaimableException fromNotClaimableExceptionXml(
			InputStream ex) throws ParsingException {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();

			Element reservationId = rootElement.getChild("reservationId",
					XML_NS);
			Element expirationDate = rootElement.getChild("expirationDate",
					XML_NS);
			Element email = rootElement.getChild("email", XML_NS);
			Element state = rootElement.getChild("state", XML_NS);

			/* Reservation aplication expired */
			Calendar calendar = null;
			if (expirationDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(CONVERSION_PATTERN,
						Locale.ENGLISH);
				calendar = Calendar.getInstance();
				calendar.setTime(sdf.parse(expirationDate.getText()));
				return new NotClaimableException(Long.parseLong(reservationId
						.getTextTrim()), calendar);
			}

			/* User didnt make the reservation */
			if (email != null) {
				return new NotClaimableException(email.getText());
			}

			/* State exception */
			return new NotClaimableException(Long.parseLong(reservationId
					.getTextTrim()), state.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static ReservationTimeExpiredException fromReservationTimeExpiredExceptionXml(
			InputStream ex) throws ParsingException {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();

			Element instanceId = rootElement.getChild("offerId", XML_NS);
			Element reservationDate = rootElement.getChild("reservationDate",
					XML_NS);

			Calendar calendar = null;
			if (reservationDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(CONVERSION_PATTERN,
						Locale.ENGLISH);
				calendar = Calendar.getInstance();
				calendar.setTime(sdf.parse(reservationDate.getText()));
			}

			return new ReservationTimeExpiredException(
					Long.parseLong(instanceId.getTextTrim()), calendar);
		} catch (JDOMException | IOException | ParseException
				| NumberFormatException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static AlreadyReservatedException fromAlreadyReservatedExceptionXml(
			InputStream ex) throws ParsingException {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();

			Element offerId = rootElement.getChild("offerId", XML_NS);
			Element email = rootElement.getChild("email", XML_NS);

			return new AlreadyReservatedException(Long.parseLong(offerId
					.getTextTrim()), email.getText());
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static NotModifiableOfferException fromNotModifiableOfferExceptionXml(
			InputStream ex) throws ParsingException {
		try {

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(ex);
			Element rootElement = document.getRootElement();

			Element offerId = rootElement.getChild("offerId", XML_NS);

			return new NotModifiableOfferException(Long.parseLong(offerId
					.getTextTrim()));
		} catch (JDOMException | IOException e) {
			throw new ParsingException(e);
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	public static Document toInputValidationExceptionXml(
			InputValidationException ex) throws IOException {

		Element exceptionElement = new Element("InputValidationException",
				XML_NS);

		Element messageElement = new Element("message", XML_NS);
		messageElement.setText(ex.getMessage());
		exceptionElement.addContent(messageElement);

		return new Document(exceptionElement);
	}
	
	public static Document toInstanceNotFoundException(
			InstanceNotFoundException ex) throws IOException {

		Element exceptionElement = new Element("InstanceNotFoundException",
				XML_NS);

		if (ex.getInstanceId() != null) {
			Element instanceIdElement = new Element("instanceId", XML_NS);
			instanceIdElement.setText(ex.getInstanceId().toString());

			exceptionElement.addContent(instanceIdElement);
		}

		if (ex.getInstanceType() != null) {
			Element instanceTypeElement = new Element("instanceType", XML_NS);
			instanceTypeElement.setText(ex.getInstanceType());

			exceptionElement.addContent(instanceTypeElement);
		}
		return new Document(exceptionElement);
	}

	/*******INTERNAL ERROR CODES:*********
	**************************************
	*AlreadyInvalidatedException:		1*
	*AlreadyReservatedException:		2*
	*NotClaimableException:				3*
	*NotModifiableOfferException:		4*
	*ReservationTimeExpiredException:	5*
	**************************************/
	
	public static Document toReservationTimeExpiredException(
			ReservationTimeExpiredException ex) throws IOException {

		Element exceptionElement = new Element(
				"ReservationTimeExpiredException", XML_NS);
		
		Element internalCodeElement = new Element("internalCode", XML_NS);
		internalCodeElement.setText("5");
		exceptionElement.addContent(internalCodeElement);
		Element offerIdElement = new Element("offerId", XML_NS);
		offerIdElement.setText(ex.getOfferId().toString());
		exceptionElement.addContent(offerIdElement);

		if (ex.getLimitReservationDate() != null) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(
					CONVERSION_PATTERN, Locale.ENGLISH);

			Element reservationDateElement = new Element(
					"limitReservationDate", XML_NS);
			reservationDateElement.setText(dateFormatter.format(ex
					.getLimitReservationDate().getTime()));

			exceptionElement.addContent(reservationDateElement);
		}

		return new Document(exceptionElement);
	}

	public static Document toAlreadyInvalidatedException(
			AlreadyInvalidatedException ex) throws IOException {

		Element exceptionElement = new Element("AlreadyInvalidatedException",
				XML_NS);
		
		Element internalCodeElement = new Element("internalCode", XML_NS);
		internalCodeElement.setText("1");
		exceptionElement.addContent(internalCodeElement);
		Element offerIdElement = new Element("offerId", XML_NS);
		offerIdElement.setText(ex.getOfferId().toString());
		exceptionElement.addContent(offerIdElement);

		return new Document(exceptionElement);

	}
	
	public static Document toAlreadyReservatedException(
			AlreadyReservatedException ex) throws IOException {

		Element exceptionElement = new Element("AlreadyReservatedException",
				XML_NS);
		
		Element internalCodeElement = new Element("internalCode", XML_NS);
		internalCodeElement.setText("2");
		exceptionElement.addContent(internalCodeElement);
		Element offerIdElement = new Element("offerId", XML_NS);
		offerIdElement.setText(ex.getOfferId().toString());
		exceptionElement.addContent(offerIdElement);
		Element emailElement = new Element("email", XML_NS);
		emailElement.setText(ex.getUser());
		exceptionElement.addContent(emailElement);

		return new Document(exceptionElement);

	}

	public static Document toNotClaimableException(
			NotClaimableException ex) throws IOException {
		
		Element exceptionElement = new Element("NotClaimableExceptionException",
				XML_NS);
		
		Element internalCodeElement = new Element("internalCode", XML_NS);
		internalCodeElement.setText("3");
		exceptionElement.addContent(internalCodeElement);

		if (ex.getEmail() != null) {
			Element emailElement = new Element("email", XML_NS);
			emailElement.setText(ex.getEmail());
			exceptionElement.addContent(emailElement);
			return new Document(exceptionElement);
		}
		
		Element reservationIdElement = new Element("reservationId", XML_NS);
		reservationIdElement.setText(ex.getReservationId().toString());
		exceptionElement.addContent(reservationIdElement);
		
		if (ex.getExpirationDate() != null) {
			Element expirationDateElement = new Element("expirationDate", XML_NS);
			expirationDateElement.setText(ex.getExpirationDate().toString());
			exceptionElement.addContent(expirationDateElement);
			return new Document(exceptionElement);
		}

		Element stateElement = new Element("state", XML_NS);
		stateElement.setText(ex.getState());
		exceptionElement.addContent(stateElement);
		return new Document(exceptionElement);

	}
	
	public static Document toNotModifiableOfferException(
			NotModifiableOfferException ex) throws IOException {

		Element exceptionElement = new Element("NotModifiableOfferException",
				XML_NS);
		
		Element internalCodeElement = new Element("internalCode", XML_NS);
		internalCodeElement.setText("4");
		exceptionElement.addContent(internalCodeElement);
		Element offerIdElement = new Element("offerId", XML_NS);
		offerIdElement.setText(ex.getOfferId().toString());
		exceptionElement.addContent(offerIdElement);

		return new Document(exceptionElement);

	}
}
