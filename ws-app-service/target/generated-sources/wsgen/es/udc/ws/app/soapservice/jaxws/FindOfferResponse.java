
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findOfferResponse", namespace = "http://soap.ws.app.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findOfferResponse", namespace = "http://soap.ws.app.udc.es/")
public class FindOfferResponse {

    @XmlElement(name = "return", namespace = "")
    private es.udc.ws.app.dto.OfferDto _return;

    /**
     * 
     * @return
     *     returns OfferDto
     */
    public es.udc.ws.app.dto.OfferDto getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(es.udc.ws.app.dto.OfferDto _return) {
        this._return = _return;
    }

}
