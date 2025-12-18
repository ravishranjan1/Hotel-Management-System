package com.MagdhaPlace.hotel.service.impl;

import com.MagdhaPlace.hotel.model.*;
import com.MagdhaPlace.hotel.repo.BookingRepo;
import com.MagdhaPlace.hotel.service.BookingService;
import com.MagdhaPlace.hotel.service.RoomService;
import com.MagdhaPlace.hotel.util.IdGenerator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final RoomService roomService;
    private static final Logger log = Logger.getLogger(BookingServiceImpl.class.getName());

    public BookingServiceImpl(BookingRepo bookingRepo, RoomService roomService) {
        this.bookingRepo = bookingRepo;
        this.roomService = roomService;
    }

    @Override
    public BookingModel createBooking(String roomId, String customerId, LocalDate from, LocalDate to) {
        RoomModel room = roomService.findById(roomId);

        BookingModel b = new BookingModel();
        String id = IdGenerator.recordId();
        b.setId(id);
        b.setRoomId(roomId);
        b.setCustomerId(customerId);
        b.setCheckInDate(from);
        b.setCheckOutDate(to);
        b.setStatus(BookingStatus.RESERVED);
        b.setPrepaidAmount(500.0);
        b.setTotalAmount(0.0);

        try {
            bookingRepo.gererateBooking(b);
            room.setStatus(RoomStatus.OCCUPIED);
            roomService.updateRoom(room);
            log.info("Booking Room successfully");
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return b;
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
