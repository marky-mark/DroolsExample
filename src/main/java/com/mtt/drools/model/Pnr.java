package com.mtt.drools.model;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pnr extends BaseEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(Pnr.class);

    private DateTime dateTime;

    private String bookingNumber;

    private PnrType pnrType;

    public Pnr(DateTime time, String bookingNumber, PnrType pnrType) {
        this.dateTime = time;
        this.bookingNumber = bookingNumber;
        this.pnrType = pnrType;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public PnrType getPnrType() {
        return pnrType;
    }

    public void setPnrType(PnrType pnrType) {
        this.pnrType = pnrType;
    }

    public boolean isWithinMinutes(int minutes) {
        DateTime currentDateTime = new DateTime();
        DateTime windowDateTime = currentDateTime.plusMinutes(minutes);
        return dateTime.compareTo(currentDateTime) >= 0 && dateTime.compareTo(windowDateTime) <= 0;
    }

    @Override
    public void fire() {
        fire(LOGGER, "Air Pnr: ", bookingNumber, " fired!");
    }
}
