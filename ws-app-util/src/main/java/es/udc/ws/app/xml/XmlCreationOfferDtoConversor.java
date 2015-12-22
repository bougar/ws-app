package es.udc.ws.app.xml;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import es.udc.ws.app.dto.CreationOfferDto;

public class XmlCreationOfferDtoConversor {
	public final static Namespace XML_NS = Namespace
			.getNamespace("http://ws.udc.es/offers/xml");
	public static Document toXml(CreationOfferDto offer) throws IOException {

		Element offerElement = toJDOMElement(offer);

		return new Document(offerElement);
	}

	public static Element toJDOMElement(CreationOfferDto offer) {

		Element offerElement = new Element("offer", XML_NS);

		Element identifierElement = new Element("offerId", XML_NS);
		identifierElement.setText(((Long) offer.getOfferId()).toString());
		offerElement.addContent(identifierElement);

		Element nameElement = new Element("name", XML_NS);
		nameElement.setText(offer.getName());
		offerElement.addContent(nameElement);

		Element descritpionElement = new Element("description", XML_NS);
		descritpionElement.setText(offer.getName());
		offerElement.addContent(descritpionElement);

		Element limitReservationDate = calendarToJDOMElement(
				offer.getLimitReservationDate(), "limitReservationDate");
		offerElement.addContent(limitReservationDate);

		Element limitApplicationDate = calendarToJDOMElement(
				offer.getLimitReservationDate(), "limitApplicationDate");
		offerElement.addContent(limitApplicationDate);

		Element realPrice = new Element("realPrice", XML_NS);
		realPrice.setText(Float.toString(offer.getRealPrice()));
		offerElement.addContent(realPrice);

		Element discountedPrice = new Element("discountedPrice", XML_NS);
		discountedPrice.setText(Float.toString(offer.getDiscountedPrice()));
		offerElement.addContent(discountedPrice);

		Element offerId = new Element("offerId", XML_NS);
		offerId.setText(Long.toString(offer.getOfferId()));
		offerElement.addContent(offerId);

		return offerElement;
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
