package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import es.udc.ws.app.dto.UserOfferDto;

public class XmlUserOfferDtoConversor {
	public final static Namespace XML_NS = Namespace
			.getNamespace("http://ws.udc.es/offers/xml");
	
	public static Document toXml(UserOfferDto offer) throws IOException {

		Element offerElement = toJDOMElement(offer);

		return new Document(offerElement);
	}
	
	public static Document toXml(List<UserOfferDto> offers) throws IOException {
		Element elements = new Element("offers", XML_NS);
		for (UserOfferDto o : offers) {
			Element offerElement = toJDOMElement(o);
			elements.addContent(offerElement);

		}
		return new Document(elements);

	}
	
	public static Element toJDOMElement(UserOfferDto offer) {
		Element offerElement = new Element("userOfferDto", XML_NS);

		Element descritpionElement = new Element("description", XML_NS);
		descritpionElement.setText(offer.getDescription());
		offerElement.addContent(descritpionElement);

		Element discountedPrice = new Element("discountedPrice", XML_NS);
		discountedPrice.setText(Float.toString(offer.getDiscountedPrice()));
		offerElement.addContent(discountedPrice);

		if (offer.getReservationDate() != null) {
			Element requestDateElement = calendarToJDOMElement(
					offer.getReservationDate(), "requestDate");
			offerElement.addContent(requestDateElement);
		}
		
		return offerElement;
	}
	
	public static UserOfferDto toOffer(Element e) throws ParsingException {
		if (!"userOfferDto".equals(e.getName()))
			throw new ParsingException("Unrecognized element '" + e.getName()
					+ "' ('userOfferDto' expected)");
		try {
			String description = e.getChildTextNormalize("description", XML_NS);
			Calendar requestDate = JDOMElementToCalendar(e,
					"requestDate");
			float discountedPrice = Float.valueOf(e.getChildTextTrim(
					"discountedPrice", XML_NS));
			return new UserOfferDto(description,discountedPrice,requestDate);
		} catch (ParseException ex) {
			throw new ParsingException(
					"Error Parsing dates in from xml to offerDto.");
		}

	}
	
	public static UserOfferDto toOffer(InputStream input) {
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
	
	public static List<UserOfferDto> toOffers(InputStream input) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(input);
			Element rootElement = document.getRootElement();
			if (!"offers".equals(rootElement.getName())) {
				throw new ParsingException("Unrecognized element '"
						+ rootElement.getName() + "' ('offers' expected)");
			}
		
			List<Element> children = rootElement.getChildren();
			List<UserOfferDto> offers = new ArrayList<UserOfferDto> (children.size());
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
