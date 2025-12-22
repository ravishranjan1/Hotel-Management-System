package com.MagdhaPlace.hotel.service.impl;

import com.MagdhaPlace.hotel.exceptions.BookingNotFoundException;
import com.MagdhaPlace.hotel.exceptions.HotelException;
import com.MagdhaPlace.hotel.exceptions.RoomNotAvailableException;
import com.MagdhaPlace.hotel.model.*;
import com.MagdhaPlace.hotel.repo.BookingRepo;
import com.MagdhaPlace.hotel.service.BookingService;
import com.MagdhaPlace.hotel.service.InvoiceService;
import com.MagdhaPlace.hotel.service.RoomService;
import com.MagdhaPlace.hotel.util.DateUtil;
import com.MagdhaPlace.hotel.util.IdGenerator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final RoomService roomService;
    private final InvoiceService invoiceService;
    private static final Logger log = Logger.getLogger(BookingServiceImpl.class.getName());

    public BookingServiceImpl(BookingRepo bookingRepo, RoomService roomService, InvoiceService invoiceService) {
        this.bookingRepo = bookingRepo;
        this.roomService = roomService;
        this.invoiceService = invoiceService;
    }

    @Override
    public BookingModel createBooking(String roomId, String customerId, LocalDate from, LocalDate to) throws RoomNotAvailableException {

        if (!isRoomAvailable(roomId, from, to)) {
            throw new RoomNotAvailableException("Room with Id " + roomId + " is not available for given date range");
        }

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

            RoomModel room = roomService.findById(roomId);
            room.setStatus(RoomStatus.OCCUPIED);
            roomService.updateRoom(room);

            log.info("Booking Room successfully");
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return b;
    }

    @Override
    public BookingModel checkIn(String bookingId) throws BookingNotFoundException, HotelException {
        BookingModel booking = findByBookingId(bookingId);
        if(booking == null){
            throw new BookingNotFoundException("Booking with this Id : "+bookingId+" is not found");
        }
        if (booking.getStatus() != BookingStatus.RESERVED) {
            throw new HotelException("Check-in not allowed. Booking status is " + booking.getStatus() + ". Only RESERVED bookings can be checked in.");
        }
        booking.setStatus(BookingStatus.CHECKED_IN);
        try {
            bookingRepo.updateBooking(booking);
            return booking;
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return null;
    }

    @Override
    public InvoiceModel checkOut(String bookingId, LocalDate actualCheckOutDate) throws BookingNotFoundException, HotelException {
        BookingModel booking = findByBookingId(bookingId);
        if(booking == null){
            throw new BookingNotFoundException("Booking with this Id : "+bookingId+" is not found");
        }
        if (booking.getStatus() != BookingStatus.CHECKED_IN) {
            throw new HotelException("Checkout not allowed. Booking status is " + booking.getStatus() + ". Only CHECKED_IN bookings can be checked out.");
        }

        RoomModel room = roomService.findById(booking.getRoomId());
        double nightRate = room.getNightlyRate();

        int nights = DateUtil.nightsRented(booking.getCheckInDate(), booking.getCheckOutDate());
        int lateNights = 0;
        if (actualCheckOutDate.isAfter(booking.getCheckOutDate())) {
            lateNights = DateUtil.nightsRented(booking.getCheckOutDate(), actualCheckOutDate);
        }
        double charge = nights*nightRate;
        double lateCharge = lateNights*(nightRate*1.2);

        double totalCharge = charge+lateCharge;

        booking.setTotalAmount(totalCharge);
        booking.setCheckOutDate(actualCheckOutDate);
        booking.setStatus(BookingStatus.CHECKED_OUT);

        room.setStatus(RoomStatus.AVAILABLE);

        try {
            bookingRepo.updateBooking(booking);
            roomService.updateRoom(room);
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        invoiceService.generateInvoice(bookingId, totalCharge, booking.getPrepaidAmount());
        return invoiceService.findByBookingId(bookingId);

    }

    @Override
    public boolean cancelBooking(String bookingId) throws BookingNotFoundException, HotelException {
        BookingModel booking = findByBookingId(bookingId);
        if(booking == null){
            throw new BookingNotFoundException("Booking with this Id : "+bookingId+" is not found");
        }
        if (booking.getStatus() != BookingStatus.RESERVED) {
            throw new HotelException("Cancellation not allowed. Booking status is " + booking.getStatus() + ". Only RESERVED bookings can be cancelled.");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        try {
            bookingRepo.updateBooking(booking);
            return true;
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }

        return false;
    }

    @Override
    public List<BookingModel> listBookingForCustomer(String customerId) {
        List<BookingModel> res = new ArrayList<>();
        try {
            List<BookingModel> list = bookingRepo.findAll();
            for(BookingModel b : list){
                if(b.getCustomerId().equalsIgnoreCase(customerId)){
                    res.add(b);
                }
            }
        } catch (IOException e) {
            log.severe("IOException occurred,"+e.getMessage());
        }
        return  res;
    }

    public BookingModel findByBookingId(String bookingId){
        try {
            List<BookingModel> list = bookingRepo.findAll();
            for(BookingModel b : list){
                if(b.getId().equalsIgnoreCase(bookingId))
                    return b;
            }
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return null;
    }


    @Override
    public boolean isRoomAvailable(String roomId, LocalDate from, LocalDate to) {

        List<BookingModel> bookings = null;
        try {
            bookings = bookingRepo.findAll();
            for (BookingModel b : bookings) {

                if (b.getRoomId().equalsIgnoreCase(roomId)
                        && b.getStatus() != BookingStatus.CANCELLED) {

                    LocalDate existingFrom = b.getCheckInDate();
                    LocalDate existingTo = b.getCheckOutDate();

                    if (from.isBefore(existingTo) && to.isAfter(existingFrom)) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            log.severe("IOException occurred "+e.getMessage());
        }
        return true;
    }

}
