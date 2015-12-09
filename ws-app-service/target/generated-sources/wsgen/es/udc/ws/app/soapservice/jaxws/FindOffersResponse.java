
package es.udc.ws.app.soapservice.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findOffersResponse", namespace = "http://soap.ws.app.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findOffersResponse", namespace = "http://soap.ws.app.udc.es/")
public class FindOffersResponse {

    @XmlElement(name = "return", namespace = "")
    private List<es.udc.ws.app.dto.OfferDto> _return;

    /**
     * 
     * @return
     *     returns List<OfferDto>
     */
    public List<es.udc.ws.app.dto.OfferDto> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<es.udc.ws.app.dto.OfferDto> _return) {
        this._return = _return;
    }

}
