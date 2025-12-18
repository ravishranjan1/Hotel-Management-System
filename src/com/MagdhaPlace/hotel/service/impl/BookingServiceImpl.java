package com.MagdhaPlace.hotel.service.impl;

import com.MagdhaPlace.hotel.model.BookingModel;
import com.MagdhaPlace.hotel.model.InvoiceModel;
import com.MagdhaPlace.hotel.service.BookingService;
import com.MagdhaPlace.hotel.util.IdGenerator;

import java.time.LocalDate;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    @Override
    public BookingModel createBooking(String roomId, String customerId, LocalDate from, LocalDate to) {
        BookingModel b = new BookingModel();
        String id = IdGenerator.recordId();
        b.setId(id);
        b.setRoomId(roomId);
        b.setCustomerId(customerId);

    }

    @Override
    public BookingModel checkIn(String bookingId) {
        return null;
    }

    @Override
    public InvoiceModel checkOut(String bookingId, LocalDate actualCheckOutDate) {
        return null;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        return false;
    }

    @Override
    public List<BookingModel> listBookingForCustomer(String customerId) {
        return List.of();
    }
}
