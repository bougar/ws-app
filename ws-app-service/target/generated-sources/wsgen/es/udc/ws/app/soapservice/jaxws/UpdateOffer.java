
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "updateOffer", namespace = "http://soap.ws.app.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateOffer", namespace = "http://soap.ws.app.udc.es/")
public class UpdateOffer {

    @XmlElement(name = "arg0", namespace = "")
    private es.udc.ws.app.model.offer.Offer arg0;

    /**
     * 
     * @return
     *     returns Offer
     */
    public es.udc.ws.app.model.offer.Offer getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(es.udc.ws.app.model.offer.Offer arg0) {
        this.arg0 = arg0;
    }

}
