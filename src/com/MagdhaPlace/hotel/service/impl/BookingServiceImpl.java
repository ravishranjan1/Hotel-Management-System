package com.MagdhaPlace.hotel.service.impl;

import com.MagdhaPlace.hotel.exceptions.BookingNotFoundException;
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
    public BookingModel checkIn(String bookingId) throws BookingNotFoundException {
        BookingModel booking = findByBookingId(bookingId);
        if(booking == null){
            throw new BookingNotFoundException("Booking with this Id : "+bookingId+" is not found");
        }
        booking.setStatus(BookingStatus.CHECKED_IN);
        try {
            bookingRepo.updateBooking(booking);
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return booking;
    }

    @Override
    public InvoiceModel checkOut(String bookingId, LocalDate actualCheckOutDate) throws BookingNotFoundException {
        BookingModel booking = findByBookingId(bookingId);
        if(booking == null){
            throw new BookingNotFoundException("Booking with this Id : "+bookingId+" is not found");
        }

        RoomModel room = roomService.findById(booking.getRoomId());
        double nightRate = room.getNightlyRate();

        int nights = DateUtil.nightsRented(booking.getCheckInDate(), booking.getCheckOutDate());
        int lateNights = DateUtil.nightsRented(booking.getCheckOutDate(), actualCheckOutDate);
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

        InvoiceModel invoice = new InvoiceModel();
        String id = String.valueOf(UUID.randomUUID());
        invoice.setId(id);
        invoice.setBookingId(bookingId);
        invoice.setRoomCharges(totalCharge);
        double tax = totalCharge*0.18;
        invoice.setTaxes(tax);
        double due = (totalCharge+tax)-booking.getPrepaidAmount();
        invoice.setTotalDue(due);

        invoiceService.generateInvoice(invoice);
        InvoiceModel i = invoiceService.findByBookingId(bookingId);
        return i;

    }

    @Override
    public boolean cancelBooking(String bookingId) {
        return false;
    }

    @Override
    public List<BookingModel> listBookingForCustomer(String customerId) {
        return List.of();
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
