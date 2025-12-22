package com.MagdhaPlace.hotel.service;

import com.MagdhaPlace.hotel.exceptions.BookingNotFoundException;
import com.MagdhaPlace.hotel.exceptions.HotelException;
import com.MagdhaPlace.hotel.exceptions.RoomNotAvailableException;
import com.MagdhaPlace.hotel.model.BookingModel;
import com.MagdhaPlace.hotel.model.InvoiceModel;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingModel createBooking(String roomId, String customerId, LocalDate from, LocalDate to) throws RoomNotAvailableException;
    BookingModel checkIn(String bookingId) throws BookingNotFoundException, HotelException;
    InvoiceModel checkOut(String bookingId, LocalDate actualCheckOutDate) throws BookingNotFoundException, HotelException;
    boolean cancelBooking(String bookingId) throws BookingNotFoundException, HotelException;
    List<BookingModel> listBookingForCustomer(String customerId);
    boolean isRoomAvailable(String roomId, LocalDate from, LocalDate to);
}
