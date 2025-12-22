package com.MagdhaPlace.hotel.handler;

import com.MagdhaPlace.hotel.exceptions.RoomAlreadyPresentException;
import com.MagdhaPlace.hotel.model.RoomModel;
import com.MagdhaPlace.hotel.model.RoomStatus;
import com.MagdhaPlace.hotel.service.RoomService;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

public class HandleRoom {
    private final RoomService roomService;
    private static final Logger log = Logger.getLogger(HandleRoom.class.getName());

    public HandleRoom(RoomService roomService) {
        this.roomService = roomService;
    }

    public void handleAddRoom(Scanner sc){
        System.out.print("Enter id : ");
        String id = sc.nextLine().trim();

        if(roomService.findById(id) == null){
            System.out.print("Enter Room Type (Single/Double/Suite) : ");
            String type = sc.nextLine();

            System.out.print("Enter Number of Beds : ");
            int beds = sc.nextInt();

            System.out.print("Enter Nightly Rate : ");
            double rate = sc.nextDouble();

            Set<String> amenities = new HashSet<>();
            System.out.println("Enter amenities one by one (type 'done' to stop):");
            while (true) {
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("done")) {
                    break;
                }
                amenities.add(input);
            }

            RoomModel r = new RoomModel();
            r.setId(id);
            r.setType(type);
            r.setBeds(beds);
            r.setNightlyRate(rate);
            r.setAmenities(amenities);
            r.setStatus(RoomStatus.AVAILABLE);

            roomService.addRoom(r);
            System.out.println("Room added successfully");
        }else{
            try {
                throw new RoomAlreadyPresentException("Room with Id : "+id+" already present");
            } catch (RoomAlreadyPresentException e) {
                log.severe("RoomAlreadyPresentException occurred, "+e.getMessage());
            }
            System.out.println("Room Not Added");
        }
    }
}
