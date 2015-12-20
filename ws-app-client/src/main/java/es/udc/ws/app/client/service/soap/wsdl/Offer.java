
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para offer complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="offer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="discountedPrice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="faceBookId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fee" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="likes" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
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
    "faceBookId",
    "fee",
    "likes",
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
    protected String faceBookId;
    protected float fee;
    protected Long likes;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar limitApplicationDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar limitReservationDate;
    protected String name;
    protected long offerId;
    protected float realPrice;
    protected boolean valid;

    /**
     * Obtiene el valor de la propiedad description.
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
     * Define el valor de la propiedad description.
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
     * Obtiene el valor de la propiedad discountedPrice.
     * 
     */
    public float getDiscountedPrice() {
        return discountedPrice;
    }

    /**
     * Define el valor de la propiedad discountedPrice.
     * 
     */
    public void setDiscountedPrice(float value) {
        this.discountedPrice = value;
    }

    /**
     * Obtiene el valor de la propiedad faceBookId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaceBookId() {
        return faceBookId;
    }

    /**
     * Define el valor de la propiedad faceBookId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaceBookId(String value) {
        this.faceBookId = value;
    }

    /**
     * Obtiene el valor de la propiedad fee.
     * 
     */
    public float getFee() {
        return fee;
    }

    /**
     * Define el valor de la propiedad fee.
     * 
     */
    public void setFee(float value) {
        this.fee = value;
    }

    /**
     * Obtiene el valor de la propiedad likes.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLikes() {
        return likes;
    }

    /**
     * Define el valor de la propiedad likes.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLikes(Long value) {
        this.likes = value;
    }

    /**
     * Obtiene el valor de la propiedad limitApplicationDate.
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
     * Define el valor de la propiedad limitApplicationDate.
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
     * Obtiene el valor de la propiedad limitReservationDate.
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
     * Define el valor de la propiedad limitReservationDate.
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
     * Obtiene el valor de la propiedad name.
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
     * Define el valor de la propiedad name.
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
     * Obtiene el valor de la propiedad offerId.
     * 
     */
    public long getOfferId() {
        return offerId;
    }

    /**
     * Define el valor de la propiedad offerId.
     * 
     */
    public void setOfferId(long value) {
        this.offerId = value;
    }

    /**
     * Obtiene el valor de la propiedad realPrice.
     * 
     */
    public float getRealPrice() {
        return realPrice;
    }

    /**
     * Define el valor de la propiedad realPrice.
     * 
     */
    public void setRealPrice(float value) {
        this.realPrice = value;
    }

    /**
     * Obtiene el valor de la propiedad valid.
     * 
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Define el valor de la propiedad valid.
     * 
     */
    public void setValid(boolean value) {
        this.valid = value;
    }

}
