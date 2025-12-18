package com.MagdhaPlace.hotel.repo;

import com.MagdhaPlace.hotel.exceptions.RoomNotFoundException;
import com.MagdhaPlace.hotel.model.RoomModel;

import java.io.IOException;
import java.util.List;

public interface RoomRepo {
    void addRoom(RoomModel r) throws IOException;
    List<RoomModel> findAll() throws IOException;
    void update(RoomModel r)throws IOException, RoomNotFoundException;
}
