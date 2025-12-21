package com.MagdhaPlace.hotel.repo;

import com.MagdhaPlace.hotel.exceptions.BookingNotFoundException;
import com.MagdhaPlace.hotel.exceptions.RoomNotFoundException;
import com.MagdhaPlace.hotel.model.BookingModel;

import java.io.IOException;
import java.util.List;

public interface BookingRepo {
    void gererateBooking(BookingModel b)throws IOException;
    List<BookingModel> findAll() throws IOException;
    void updateBooking(BookingModel b) throws IOException;
}
