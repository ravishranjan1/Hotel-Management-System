package com.MagdhaPlace.hotel.handler;

import com.MagdhaPlace.hotel.exceptions.BookingNotFoundException;
import com.MagdhaPlace.hotel.exceptions.HotelException;
import com.MagdhaPlace.hotel.exceptions.RoomNotAvailableException;
import com.MagdhaPlace.hotel.model.BookingModel;
import com.MagdhaPlace.hotel.model.CustomerModel;
import com.MagdhaPlace.hotel.model.InvoiceModel;
import com.MagdhaPlace.hotel.model.RoomModel;
import com.MagdhaPlace.hotel.service.BookingService;
import com.MagdhaPlace.hotel.service.CustomerService;
import com.MagdhaPlace.hotel.service.RoomService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class HandleBooking {
    private final BookingService bookingService;
    private final RoomService roomService;
    private final CustomerService customerService;
    private final Logger log = Logger.getLogger(HandleBooking.class.getName());

    public HandleBooking(BookingService bookingService, RoomService roomService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.customerService = customerService;
    }

    public void handleCreateBooking(Scanner sc) {

        try {
            System.out.print("Enter Room Id : ");
            String roomId = sc.nextLine();

            RoomModel r = roomService.findById(roomId);
            if (r == null) {
                System.out.println("Room not found with id : " + roomId);
                return;
            }

            System.out.print("Enter Customer Id : ");
            String customerId = sc.nextLine();

            CustomerModel c = customerService.findById(customerId);
            if (c == null) {
                System.out.println("Customer not found with id : " + customerId);
                return;
            }

            System.out.print("Enter From Date (yyyy-MM-dd) : ");
            LocalDate from = LocalDate.parse(sc.nextLine());

            System.out.print("Enter To Date (yyyy-MM-dd) : ");
            LocalDate to = LocalDate.parse(sc.nextLine());

            if (!to.isAfter(from)) {
                System.out.println("Invalid date range");
                return;
            }

            BookingModel b = bookingService.createBooking(roomId, customerId, from, to);

            System.out.println("Room booked successfully with Room Id : " + roomId);

            System.out.println("id     | roomId | customerId | checkIn    | checkOut   | status   | prepaid | total");
            System.out.println("-------------------------------------------------------------------------------");

            System.out.printf(
                    "%-6s | %-6s | %-10s | %-10s | %-10s | %-8s | %-7.2f | %-7.2f%n",
                    b.getId(),
                    b.getRoomId(),
                    b.getCustomerId(),
                    b.getCheckInDate(),
                    b.getCheckOutDate(),
                    b.getStatus(),
                    b.getPrepaidAmount(),
                    b.getTotalAmount()
            );

        } catch (RoomNotAvailableException e) {
            log.severe("RoomNotAvailableException occurred, "+e.getMessage());
            System.out.println("Room is not available for the selected date range. Please choose different dates.");
        }
    }

    public void handleCheckIn(Scanner sc){
        System.out.print("Enter Booking Id : ");
        String bookingId = sc.nextLine().trim();

        try {
            BookingModel b = bookingService.checkIn(bookingId);

            System.out.println(
                    "Check-in successful! Customer " + b.getCustomerId() +
                            " checked into Room " + b.getRoomId() +
                            " on " + b.getCheckInDate()
            );

        } catch (BookingNotFoundException e) {
            log.severe("BookingNotFoundException occurred, "+e.getMessage());
            System.out.println("Check-in failed. Booking ID not found. Please verify the Booking ID and try again.");
        } catch (HotelException e) {
            log.severe("HotelException occurred, "+e.getMessage());
            System.out.println("Check-in failed !");
        }
    }

    public void handleCheckOut(Scanner sc){
        System.out.print("Enter Booking Id : ");
        String id = sc.nextLine().trim();

        System.out.print("Enter From Date (yyyy-MM-dd) : ");
        LocalDate checkOut = LocalDate.parse(sc.nextLine());

        try {
            InvoiceModel invoice = bookingService.checkOut(id, checkOut);
            System.out.println("\nINVOICE DETAILS");
            System.out.println("invoiceId | bookingId | roomCharge | taxes  | additional | totalDue");
            System.out.println("---------------------------------------------------------------------");

            System.out.printf(
                    "%-9s | %-9s | %-10.2f | %-6.2f | %-10.2f | %-8.2f%n",
                    invoice.getId(),
                    invoice.getBookingId(),
                    invoice.getRoomCharges(),
                    invoice.getTaxes(),
                    invoice.getAdditionalCharges(),
                    invoice.getTotalDue()
            );

        } catch (BookingNotFoundException e) {
            log.severe("BookingNotFoundException occurred, "+e.getMessage());
            System.out.println("Check-out failed. Booking ID not found. Please verify the Booking ID and try again.");
        } catch (HotelException e) {
            log.severe("HotelException occurred, "+e.getMessage());
            System.out.println("Check-out failed !");
        }
    }


    public void handleCancel(Scanner sc){
        System.out.print("Enter Booking Id : ");
        String bookingId = sc.nextLine().trim();

        try {
            boolean res = bookingService.cancelBooking(bookingId);
            if(res){
                System.out.println("Cancellation booking successful");
            }else{
                System.out.println("Cancel failed");
            }

        } catch (BookingNotFoundException e) {
            log.severe("BookingNotFoundException occurred, "+e.getMessage());
            System.out.println("Cancel failed. Booking ID not found. Please verify the Booking ID and try again.");
        } catch (HotelException e) {
           log.severe("HotelException occurred, "+e.getMessage());
        }
    }

    public void handleListOfCustomer(Scanner sc){
        System.out.print("Enter Customer Id : ");
        String customerId = sc.nextLine().trim();
        List<BookingModel> list = bookingService.listBookingForCustomer(customerId);

        if(list.isEmpty()){
            System.out.println("No Record Found !");
            return;
        }
        System.out.println("id     | roomId | customerId | checkIn    | checkOut   | status   | prepaid | total");
        System.out.println("-------------------------------------------------------------------------------");
        int i;
        for(i=0;i<list.size();i++){
            BookingModel b = list.get(i);
            System.out.printf(
                    "%-6s | %-6s | %-10s | %-10s | %-10s | %-8s | %-7.2f | %-7.2f%n",
                    b.getId(),
                    b.getRoomId(),
                    b.getCustomerId(),
                    b.getCheckInDate(),
                    b.getCheckOutDate(),
                    b.getStatus(),
                    b.getPrepaidAmount(),
                    b.getTotalAmount()
            );
        }
    }


}
