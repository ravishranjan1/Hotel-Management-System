package com.MagdhaPlace.hotel.service.impl;

import com.MagdhaPlace.hotel.model.RoomModel;
import com.MagdhaPlace.hotel.repo.RoomRepo;
import com.MagdhaPlace.hotel.service.RoomService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class RoomServiceImpl implements RoomService {
    private final RoomRepo roomRepo;
    private static final Logger log = Logger.getLogger(RoomServiceImpl.class.getName());

    public RoomServiceImpl(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }


    @Override
    public void addRoom(RoomModel r) {
        try {
            roomRepo.addRoom(r);
            log.info("Room added successfully");
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
    }

    @Override
    public List<RoomModel> searchAvailable(LocalDate from, LocalDate to) {
        return List.of();
    }

    @Override
    public RoomModel findById(String id) {
        try {
            List<RoomModel> list = roomRepo.findAll();
            for(RoomModel r : list){
                if(r.getId().equalsIgnoreCase(id)){
                    log.info("Room found with id"+id);
                    return r;
                }
            }
        } catch (IOException e) {
            log.severe("IOException occurred, "+e.getMessage());
        }
        return null;
    }
}
