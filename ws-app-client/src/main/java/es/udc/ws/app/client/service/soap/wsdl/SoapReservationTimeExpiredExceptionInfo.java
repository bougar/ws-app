
package es.udc.ws.app.client.service.soap.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para soapReservationTimeExpiredExceptionInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="soapReservationTimeExpiredExceptionInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="limitReservationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="offerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soapReservationTimeExpiredExceptionInfo", propOrder = {
    "limitReservationDate",
    "offerId"
})
public class SoapReservationTimeExpiredExceptionInfo {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar limitReservationDate;
    protected long offerId;

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

}
