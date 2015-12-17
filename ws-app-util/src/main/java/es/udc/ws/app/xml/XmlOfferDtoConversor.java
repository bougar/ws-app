package es.udc.ws.app.xml;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.dto.OfferDto;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class XmlOfferDtoConversor {
	public final static Namespace XML_NS = Namespace
			.getNamespace("http://ws.udc.es/offers/xml");

	public static Document toXml(OfferDto offer) throws IOException {

		Element offerElement = toJDOMElement(offer);

		return new Document(offerElement);
	}
	
	//Porque tira IOException?
	public static Document toXml(List<OfferDto> offers) throws IOException {
		Element elements = new Element("offers", XML_NS);
		for (OfferDto o : offers) {
			Element offerElement = toJDOMElement(o);
			elements.addContent(offerElement);
		}
		return new Document(elements);

	}

	public static Element toJDOMElement(OfferDto offer) {

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

		Element isValid = new Element("isValid", XML_NS);
		isValid.setText(Boolean.toString(offer.isValid()));

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
