package com.MagdhaPlace.hotel.handler;

import com.MagdhaPlace.hotel.model.CustomerModel;
import com.MagdhaPlace.hotel.model.RoomModel;
import com.MagdhaPlace.hotel.service.BookingService;
import com.MagdhaPlace.hotel.service.CustomerService;
import com.MagdhaPlace.hotel.service.RoomService;

import java.time.LocalDate;
import java.util.Scanner;

public class HandleBooking {
    private final BookingService bookingService;
    private final RoomService roomService;
    private final CustomerService customerService;

    public HandleBooking(BookingService bookingService, RoomService roomService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.customerService = customerService;
    }

    public void handleCreateBooking(Scanner sc){
        System.out.print("Enter Room Id : ");
        String roomId = sc.nextLine();
        RoomModel r = roomService.findById(roomId);
        if(r!=null){
            System.out.print("Enter Customer Id : ");
            String customerId = sc.nextLine();
            CustomerModel c = customerService.findById(customerId);
            if(c!=null){
                System.out.print("Enter From Date(yyyy-MM-dd) : ");
                String fromDate = sc.nextLine();
                LocalDate from = LocalDate.parse(fromDate);

                System.out.print("Enter To Date(yyyy-MM-dd) : ");
                String toDate = sc.nextLine();
                LocalDate to = LocalDate.parse(toDate);
            }else{
                System.out.println("Customer not found with id : "+customerId);
            }
        }else{
            System.out.println("Room not found with id : "+roomId);
        }
    }
}
