package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.dto.OfferDto;
import es.udc.ws.app.xml.ParsingException;

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

	// Porque tira IOException?
	public static Document toXml(List<OfferDto> offers) throws IOException {
		Element elements = new Element("offers", XML_NS);
		for (OfferDto o : offers) {
			Element offerElement = toJDOMElement(o);
			elements.addContent(offerElement);

		}
		return new Document(elements);

	}

	public static List<OfferDto> toOffers(InputStream input) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(input);
			Element rootElement = document.getRootElement();
			if (!"offers".equals(rootElement.getName())) {
				throw new ParsingException("Unrecognized element '"
						+ rootElement.getName() + "' ('offers' expected)");
			}
		
			List<Element> children = rootElement.getChildren();
			List<OfferDto> offers = new ArrayList<OfferDto> (children.size());
			for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                offers.add(toOffer(element));
            }
			return offers;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
	}

	public static OfferDto toOffer(InputStream input) {
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

	public static OfferDto toOffer(Element e) throws ParsingException {
		if (!"offer".equals(e.getName()))
			throw new ParsingException("Unrecognized element '" + e.getName()
					+ "' ('offerDto' expected)");
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
			long likes = Long.valueOf(e.getChildTextTrim("likes", XML_NS));
			boolean isValid = Boolean.valueOf(e.getChildTextNormalize(
					"isValid", XML_NS));
			return new OfferDto(offerId, name, description,
					limitReservationDate, limitApplicationDate, realPrice,
					discountedPrice, isValid, likes);
		} catch (ParseException ex) {
			throw new ParsingException(
					"Error Parsing dates in from xml to offerDto.");
		}

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

		Element likes = new Element("likes", XML_NS);
		likes.setText(offer.getLikes().toString());
		offerElement.addContent(likes);

		Element isValid = new Element("isValid", XML_NS);
		isValid.setText(Boolean.toString(offer.isValid()));
		offerElement.addContent(isValid);

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

	private static Calendar JDOMElementToCalendar(Element e, String name)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatter.parse(e.getChildTextNormalize(name, XML_NS)));
		return cal;
	}

}
