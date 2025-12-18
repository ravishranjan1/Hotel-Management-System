package com.MagdhaPlace.hotel.service;

import com.MagdhaPlace.hotel.model.BookingModel;
import com.MagdhaPlace.hotel.model.InvoiceModel;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingModel createBooking(String roomId, String customerId, LocalDate from, LocalDate to);
    BookingModel checkIn(String bookingId);
    InvoiceModel checkOut(String bookingId, LocalDate actualCheckOutDate);
    boolean cancelBooking(String bookingId);
    List<BookingModel> listBookingForCustomer(String customerId);
}
