package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.dto.ReservationDto;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class XmlReservationDtoConversor {

    public final static Namespace XML_NS = Namespace
            .getNamespace("http://ws.app.udc.es/reservations/xml");

    public static Document toResponse(ReservationDto reservation)
            throws IOException {

        Element reservationElement = toXml(reservation);

        return new Document(reservationElement);
    }

	public static Document toXml(List<ReservationDto> reservations) throws IOException {
		Element elements = new Element("reservations", XML_NS);
		for (ReservationDto r : reservations) {
			Element reservationElement = toJDOMElement(r);
			elements.addContent(reservationElement);

		}
		return new Document(elements);

	}
    
	public static List<ReservationDto> toReservations(InputStream input) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(input);
			Element rootElement = document.getRootElement();
			if (!"reservations".equals(rootElement.getName())) {
				throw new ParsingException("Unrecognized element '"
						+ rootElement.getName() + "' ('reservations' expected)");
			}
		
			List<Element> children = rootElement.getChildren();
			List<ReservationDto> reservations = new ArrayList<ReservationDto> (children.size());
			for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                reservations.add(toReservation(element));
            }
			return reservations;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
	}
	
    public static ReservationDto toReservation(InputStream reservationXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(reservationXml);
            Element rootElement = document.getRootElement();

            return toReservation(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Element toXml(ReservationDto reservation) {

        Element reservationElement = new Element("reservation", XML_NS);

        Element reservationIdElement = new Element("reservationId", XML_NS);
        reservationIdElement.setText(Long.toString(reservation.getReservationId()));
        reservationElement.addContent(reservationIdElement);

        Element offerIdElement = new Element("offerId", XML_NS);
        offerIdElement.setText(Long.toString(reservation.getOfferId()));
        reservationElement.addContent(offerIdElement);

        Element emailElement = new Element("email", XML_NS);
        emailElement.setText(reservation.getEmail());
        reservationElement.addContent(emailElement);
        
        Element stateElement = new Element("state", XML_NS);
        stateElement.setText(reservation.getState());
        reservationElement.addContent(stateElement);
        
        if (reservation.getRequestDate() != null) {
            Element requestDateElement = getRequestDate(reservation
                    .getRequestDate(),"requestDate");
            reservationElement.addContent(requestDateElement);
        }
        
        Element creditCardElement = new Element("creditCardNumber", XML_NS);
        creditCardElement.setText(reservation.getCreditCardNumber());
        reservationElement.addContent(creditCardElement);
        
        Element reservationPriceElement = new Element("reservationPrice", XML_NS);
        reservationPriceElement.setText(Float.toString(reservation.getReservationPrice()));
        reservationElement.addContent(reservationPriceElement); 

        return reservationElement;
    }

    private static ReservationDto toReservation(Element reservationElement)
            throws ParsingException, DataConversionException,
            NumberFormatException, ParseException {
        if (!"reservation".equals(reservationElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + reservationElement.getName() + "' ('reservation' expected)");
        }
        
        Element reservationIdElement = reservationElement.getChild("reservationId", XML_NS);
        long reservationId=-1;
        if (reservationIdElement != null) {
            reservationId = Long.valueOf(reservationIdElement.getTextTrim());
        }

        Element offerIdElement = reservationElement.getChild("offerId", XML_NS);
        long offerId=-1;
        if (offerIdElement != null) {
            offerId = Long.valueOf(offerIdElement.getTextTrim());
        }

        String email = reservationElement.getChildTextTrim("email", XML_NS);
        
        String state = reservationElement.getChildTextTrim("state", XML_NS);
        
        Calendar requestDate = getRequestDate(reservationElement,"requestDate");
        
        String creditCardNumber = reservationElement.getChildTextTrim("creditCardNumber", XML_NS);
        
        Element reservationPriceElement = reservationElement.getChild("reservationPrice", XML_NS);
        float reservationPrice=-1;
        if (reservationPriceElement != null) {
            reservationPrice = Float.valueOf(reservationPriceElement.getTextTrim());
        }

        return new ReservationDto(email,offerId, state, requestDate, reservationId, creditCardNumber,
    			reservationPrice);
    }

    private static Calendar getRequestDate(Element e,String name)
            throws DataConversionException, ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatter.parse(e.getChildTextNormalize(name, XML_NS)));
		return cal;

    }

    private static Element getRequestDate(Calendar cal, String name) {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		Element element = new Element(name, XML_NS);
		element.setAttribute("type", "xs:dateTime");
		formatter.format(cal.getTime());
		element.setText(formatter.format(cal.getTime()));
		return element;

    }
    
	public static Element toJDOMElement(ReservationDto reservation) {

		Element reservationElement = new Element("reservation", XML_NS);

		Element identifierElement = new Element("reservationId", XML_NS);
		identifierElement.setText(((Long) reservation.getReservationId()).toString());
		reservationElement.addContent(identifierElement);

		Element emailElement = new Element("email", XML_NS);
		emailElement.setText(reservation.getEmail());
		reservationElement.addContent(emailElement);

		Element offerIdElement = new Element("offerId", XML_NS);
		offerIdElement.setText(Long.toString(reservation.getOfferId()));
		reservationElement.addContent(offerIdElement);

		Element stateElement= new Element("state", XML_NS);
		stateElement.setText(reservation.getState());
		reservationElement.addContent(stateElement);

		Element requestDateElement = calendarToJDOMElement(
				reservation.getRequestDate(), "requestDate");
		reservationElement.addContent(requestDateElement);

		Element creditCardElement= new Element("creditCardNumber", XML_NS);
		creditCardElement.setText(reservation.getCreditCardNumber());
		reservationElement.addContent(creditCardElement);
		
		Element reservationPriceElement = new Element("reservationPrice", XML_NS);
		reservationPriceElement.setText(Float.toString(reservation.getReservationPrice()));
		reservationElement.addContent(reservationPriceElement);

		return reservationElement;
	}

	private static Element calendarToJDOMElement(Calendar cal, String name) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		Element element = new Element(name, XML_NS);
		element.setAttribute("type", "xs:dateTime");
		formatter.format(cal.getTime());
		element.setText(formatter.format(cal.getTime()));
		return element;
	}
	
}
