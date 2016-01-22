package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

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
		descritpionElement.setText(offer.getDescription());
		offerElement.addContent(descritpionElement);

		Element limitReservationDate = calendarToJDOMElement(
				offer.getLimitReservationDate(), "limitReservationDate");
		offerElement.addContent(limitReservationDate);

		Element limitApplicationDate = calendarToJDOMElement(
				offer.getLimitApplicationDate(), "limitApplicationDate");
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

		Element fee = new Element("fee", XML_NS);
		fee.setText(Float.toString(offer.getFee()));
		offerElement.addContent(fee);

		return offerElement;
	}

	public static CreationOfferDto toOffer(InputStream input) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(input);
			Element rootElement = document.getRootElement();
			return toOffer(rootElement);
		} catch (ParsingException ex) {
			throw ex;
		} catch (Exception e) {
			throw new ParsingException(e);
		}
	}

	private static CreationOfferDto toOffer(Element e) throws ParsingException {
		if (!"offer".equals(e.getName()))
			throw new ParsingException("Unrecognized element '" + e.getName()
					+ "' ('offermovie' expected)");
		try {
			Long offerId = null;
			Element identifier = e.getChild("offerId", XML_NS);

			if (identifier != null)
				offerId = Long.valueOf(identifier.getTextTrim());
			String name = e.getChildTextNormalize("name", XML_NS);
			String description = e.getChildTextNormalize("description", XML_NS);
			Calendar limitReservationDate = JDOMElementToCalendar(e,
					"limitReservationDate");
			Calendar limitApplicationDate = JDOMElementToCalendar(e,
					"limitApplicationDate");
			float realPrice = Float.valueOf(e.getChildTextTrim("realPrice",
					XML_NS));
			float discountedPrice = Float.valueOf(e.getChildTextTrim(
					"discountedPrice", XML_NS));
			float fee = Float.valueOf(e.getChildTextTrim(
					"fee", XML_NS));
			boolean isValid = Boolean.valueOf(e.getChildTextNormalize(
					"isValid", XML_NS));
			return new CreationOfferDto(offerId, name, description,
					limitReservationDate, limitApplicationDate, realPrice,
					discountedPrice, fee, isValid);
		} catch (ParseException ex) {
			throw new ParsingException(
					"Error Parsing dates in from xml to offerDto.");
		}

	}

	private static Calendar JDOMElementToCalendar(Element e, String name)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatter.parse(e.getChildTextNormalize(name, XML_NS)));
		return cal;
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
