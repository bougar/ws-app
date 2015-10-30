-- ---------- Table for validation queries from the connection pool -----------



-- -----------------------------------------------------------------------------
-- Drop tables. NOTE: before dropping a table (when re-executing the script),
-- the tables having columns acting as foreign keys of the table to be dropped,
-- must be dropped first (otherwise, the corresponding checks on those tables
-- could not be done).

DROP TABLE Reservation;
DROP TABLE Offer;

-- --------------------------------- Movie ------------------------------------
CREATE TABLE Offer ( offerId BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) COLLATE latin1_bin NOT NULL,
    description VARCHAR(255) COLLATE latin1_bin NOT NULL,
    limitReservationDate TIMESTAMP DEFAULT 0 NOT NULL,
    limitApplicationDate TIMESTAMP DEFAULT 0 NOT NULL,
    realPrice FLOAT NOT NULL,
    discountedPrice FLOAT NOT NULL,
    fee FLOAT NOT NULL,
    valid BIT NOT NULL,
    CONSTRAINT OfferPK PRIMARY KEY(offerId),
    CONSTRAINT validRealPrize CHECK (realprize >= 0),
    CONSTRAINT validDiscountedPrize CHECK (discountedPrize >= 0),
    CONSTRAINT validFee CHECK (fee >= 0) ) ENGINE = InnoDB;

-- --------------------------------- Sale ------------------------------------

CREATE TABLE Reservation ( reservationId BIGINT NOT NULL AUTO_INCREMENT,
    offerId BIGINT NOT NULL,
    email VARCHAR(12) COLLATE latin1_bin NOT NULL,
    state ENUM ('EXPIRED', 'INVALID', 'CLAIMED', 'NOT_CLAIMED') NOT NULL,
    requestDate TIMESTAMP DEFAULT 0 NOT NULL,
    creditCardNumber VARCHAR(16) NOT NULL,
    CONSTRAINT ReservationPK PRIMARY KEY(reservationId),
    CONSTRAINT ReservationOfferIdFK FOREIGN KEY(offerId)
        REFERENCES Offer(offerId) ON DELETE CASCADE ) ENGINE = InnoDB;
