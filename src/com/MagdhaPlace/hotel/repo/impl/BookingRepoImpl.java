package com.MagdhaPlace.hotel.repo.impl;

import com.MagdhaPlace.hotel.model.BookingModel;
import com.MagdhaPlace.hotel.repo.BookingRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BookingRepoImpl implements BookingRepo {
    private final Path csvpath;

    public BookingRepoImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void gererateBooking(BookingModel b) throws IOException {

    }

    @Override
    public List<BookingModel> findAll() throws IOException {
        List<BookingModel> list = new ArrayList<>();
        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine();
        while((line= br.readLine()) != null){
            if(!line.trim().isEmpty()){
                BookingModel b = parseIntoBooking(line);
            }
        }
    }

    private void parseIntoBooking(String line){

    }

}
