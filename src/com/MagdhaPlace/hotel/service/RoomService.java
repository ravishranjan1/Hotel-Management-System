package com.MagdhaPlace.hotel.service;

import com.MagdhaPlace.hotel.model.RoomModel;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    void addRoom(RoomModel r);
    List<RoomModel>searchAvailable(LocalDate from, LocalDate to);
    RoomModel findById(String id);
}
