
package es.udc.ws.app.soapservice.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findReservationByOfferIdResponse", namespace = "http://soap.ws.app.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findReservationByOfferIdResponse", namespace = "http://soap.ws.app.udc.es/")
public class FindReservationByOfferIdResponse {

    @XmlElement(name = "return", namespace = "")
    private List<es.udc.ws.app.dto.ReservationDto> _return;

    /**
     * 
     * @return
     *     returns List<ReservationDto>
     */
    public List<es.udc.ws.app.dto.ReservationDto> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<es.udc.ws.app.dto.ReservationDto> _return) {
        this._return = _return;
    }

}
