package com.ttapractice.module;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttapractice.payload.pojos.Bookingdates;
import com.ttapractice.payload.pojos.Bookings;

public class PayloadModule {

    public String createPayload() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Bookings booking = new Bookings();
        booking.setFirstname("Nishant");
        booking.setLastname("Khanna");
        booking.setTotalprice(2598);
        booking.setDepositpaid(true);
        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2023-05-01");
        bookingdates.setCheckout("2023-05-02");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        String payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);

        return payload;
    }


}
