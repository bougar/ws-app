
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for offer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="offer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="discountedPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="fee" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="limitApplicationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="limitReservationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="offerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="realPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="valid" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "offer", propOrder = {
    "description",
    "discountedPrice",
    "fee",
    "limitApplicationDate",
    "limitReservationDate",
    "name",
    "offerId",
    "realPrice",
    "valid"
})
public class Offer {

    protected String description;
    protected float discountedPrice;
    protected float fee;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar limitApplicationDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar limitReservationDate;
    protected String name;
    protected long offerId;
    protected float realPrice;
    protected boolean valid;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the discountedPrice property.
     * 
     */
    public float getDiscountedPrice() {
        return discountedPrice;
    }

    /**
     * Sets the value of the discountedPrice property.
     * 
     */
    public void setDiscountedPrice(float value) {
        this.discountedPrice = value;
    }

    /**
     * Gets the value of the fee property.
     * 
     */
    public float getFee() {
        return fee;
    }

    /**
     * Sets the value of the fee property.
     * 
     */
    public void setFee(float value) {
        this.fee = value;
    }

    /**
     * Gets the value of the limitApplicationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLimitApplicationDate() {
        return limitApplicationDate;
    }

    /**
     * Sets the value of the limitApplicationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLimitApplicationDate(XMLGregorianCalendar value) {
        this.limitApplicationDate = value;
    }

    /**
     * Gets the value of the limitReservationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLimitReservationDate() {
        return limitReservationDate;
    }

    /**
     * Sets the value of the limitReservationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLimitReservationDate(XMLGregorianCalendar value) {
        this.limitReservationDate = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the offerId property.
     * 
     */
    public long getOfferId() {
        return offerId;
    }

    /**
     * Sets the value of the offerId property.
     * 
     */
    public void setOfferId(long value) {
        this.offerId = value;
    }

    /**
     * Gets the value of the realPrice property.
     * 
     */
    public float getRealPrice() {
        return realPrice;
    }

    /**
     * Sets the value of the realPrice property.
     * 
     */
    public void setRealPrice(float value) {
        this.realPrice = value;
    }

    /**
     * Gets the value of the valid property.
     * 
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Sets the value of the valid property.
     * 
     */
    public void setValid(boolean value) {
        this.valid = value;
    }

}
