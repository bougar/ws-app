package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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
            .getNamespace("http://ws.udc.es/reservations/xml");

    public static Document toResponse(ReservationDto reservation)
            throws IOException {

        Element reservationElement = toXml(reservation);

        return new Document(reservationElement);
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
                    .getRequestDate());
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
            NumberFormatException {
        if (!"reservation".equals(reservationElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + reservationElement.getName() + "' ('reservation' expected)");
        }
        
        Element reservationIdElement = reservationElement.getChild("reservationId", XML_NS);
        long reservationId;
        if (reservationIdElement != null) {
            reservationId = Long.valueOf(reservationIdElement.getTextTrim());
        }

        Element offerIdElement = reservationElement.getChild("offerId", XML_NS);
        long offerId;
        if (offerIdElement != null) {
            offerId = Long.valueOf(offerIdElement.getTextTrim());
        }

        String email = reservationElement.getChildTextTrim("email", XML_NS);
        
        String state = reservationElement.getChildTextTrim("state", XML_NS);
        
        Calendar requestDate = getRequestDate(reservationElement.getChild(
                "requestDate", XML_NS));
        
        String creditCardNumber = reservationElement.getChildTextTrim("creditCardNumber", XML_NS);
        
        Element reservationPriceElement = reservationElement.getChild("reservationPrice", XML_NS);
        float reservationPrice;
        if (reservationPriceElement != null) {
            reservationPrice = Float.valueOf(reservationPriceElement.getTextTrim());
        }

        return new ReservationDto(email,offerId, state, requestDate, reservationId, creditCardNumber,
    			reservationPrice);
    }

    private static Calendar getRequestDate(Element requestDateElement)
            throws DataConversionException {

        if (requestDateElement == null) {
            return null;
        }
        int day = requestDateElement.getAttribute("day").getIntValue();
        int month = requestDateElement.getAttribute("month").getIntValue();
        int year = requestDateElement.getAttribute("year").getIntValue();
        Calendar releaseDate = Calendar.getInstance();

        releaseDate.set(Calendar.DAY_OF_MONTH, day);
        releaseDate.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        releaseDate.set(Calendar.YEAR, year);

        return releaseDate;

    }

    private static Element getRequestDate(Calendar requestDate) {

        Element releaseDateElement = new Element("requestDate", XML_NS);
        int day = requestDate.get(Calendar.DAY_OF_MONTH);
        int month = requestDate.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = requestDate.get(Calendar.YEAR);

        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));

        return releaseDateElement;

    }

}
