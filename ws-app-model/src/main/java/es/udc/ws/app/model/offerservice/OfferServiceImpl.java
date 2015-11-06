package es.udc.ws.app.model.offerservice;

import es.udc.ws.app.exceptions.InputValidationException;
import es.udc.ws.app.exceptions.InstanceNotFoundException;
import es.udc.ws.app.exceptions.NotModifiableOfferException;
import es.udc.ws.app.exceptions.NotClaimableException;
import static es.udc.ws.app.model.ModelConstants.OFFER_DATA_SOURCE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import es.udc.ws.app.model.offer.Offer;
import es.udc.ws.app.model.offer.SqlOfferDao;
import es.udc.ws.app.model.offer.SqlOfferDaoFactory;
import es.udc.ws.app.model.reservation.Reservation;
import es.udc.ws.app.model.reservation.SqlReservationDao;
import es.udc.ws.app.model.reservation.SqlReservationDaoFactory;
import es.udc.ws.app.sql.DataSourceLocator;
import es.udc.ws.app.validation.PropertyValidator;

public class OfferServiceImpl {

    /*
     * IMPORTANT: Some JDBC drivers require "setTransactionIsolation" to
     * be called before "setAutoCommit".
     */

    private DataSource dataSource;
    private SqlOfferDao offerDao = null;
    private SqlReservationDao reservationDao = null;
    
    public OfferServiceImpl() {
        dataSource = DataSourceLocator.getDataSource(OFFER_DATA_SOURCE);
        offerDao = SqlOfferDaoFactory.getDao();
        reservationDao = SqlReservationDaoFactory.getDao();
    }
    
    private void validateMovie(Offer offer) throws InputValidationException {
	        PropertyValidator.validateMandatoryString("title", offer.getName());
	        PropertyValidator.validateMandatoryString("description", offer.getDescription());
	        PropertyValidator.validatePastDate("limitReservationDate", offer.getLimitReservationDate());
	        PropertyValidator.validatePastDate("limitApplicationDate", offer.getLimitApplicationDate());
	        PropertyValidator.validateTwoDates("limitReservationDate", offer.getLimitReservationDate(), 
	        			offer.getLimitApplicationDate());
	        PropertyValidator.validateFloat("realPrice", offer.getRealPrice(), 0,
	        		Float.MAX_VALUE);
	        PropertyValidator.validateFloat("discountedPrice", offer.getDiscountedPrice(), 0,
	        		offer.getRealPrice());
	        PropertyValidator.validateFloat("fee", offer.getFee(), 0, 100);
    }
    
}
