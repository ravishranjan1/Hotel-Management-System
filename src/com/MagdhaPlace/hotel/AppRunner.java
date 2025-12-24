package com.MagdhaPlace.hotel;

import com.MagdhaPlace.hotel.handler.HandleBooking;
import com.MagdhaPlace.hotel.handler.HandleCustomer;
import com.MagdhaPlace.hotel.handler.HandleRoom;

import java.util.Scanner;

public class AppRunner {
    private final HandleCustomer handleCustomer;
    private final HandleRoom handleRoom;
    private final HandleBooking handleBooking;

    public AppRunner(HandleCustomer handleCustomer, HandleRoom handleRoom, HandleBooking handleBooking) {
        this.handleCustomer = handleCustomer;
        this.handleRoom = handleRoom;
        this.handleBooking = handleBooking;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        System.out.println("""
╔════════════════════════════════════════════╗
║                                            ║
║     🏨  WELCOME TO MAGADH PLACE HOTEL      ║
║                                            ║
╚════════════════════════════════════════════╝
""");

        printHelp();
        boolean running = true;
        while(running){
            System.out.println("\n> ");
            String command = sc.nextLine().trim();
            try{
                String normalizeCommand = command.toLowerCase().trim();
                switch(normalizeCommand){
                    case "help" -> printHelp();
                    case "register-customer" -> handleCustomer.handleAddCustomer(sc);
                    case "add-room"  -> handleRoom.handleAddRoom(sc);
                    case "booking"  -> handleBooking.handleCreateBooking(sc);
                    case "check-in"  -> handleBooking.handleCheckIn(sc);
                    case "check-out"  -> handleBooking.handleCheckOut(sc);
                    case "cancel"  -> handleBooking.handleCancel(sc);
                    case "list-booking"  -> handleBooking.handleListOfCustomer(sc);
                    case "exit","quit"   -> running = false;
                    default ->  System.out.println("Unknown command. Type 'help' for list.");
                }
            }catch(Exception e){
                System.out.println("Unexpected Error "+e.getMessage());
            }
        }

    }

    private void printHelp(){
        System.out.println("commands: ");
        System.out.println("  register-customer     - add a new customer");
        System.out.println("  add-room              - add a new room");
        System.out.println("  booking               - book a room");
        System.out.println("  check-in              - Check_In the room");
        System.out.println("  check-out             - check_Out the room");
        System.out.println("  cancel                - cancel the booking room");
        System.out.println("  list-booking          - list of booking of customer");
        System.out.println("  help                  - show this help");
        System.out.println("  exit / quit           - exit application");
    }

}
