package com.MagdhaPlace.hotel.repo.impl;

import com.MagdhaPlace.hotel.model.BookingModel;
import com.MagdhaPlace.hotel.model.BookingStatus;
import com.MagdhaPlace.hotel.repo.BookingRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingRepoImpl implements BookingRepo {
    private final Path csvpath;
    private final String HEADER = "id,roomId,customerId,checkInDate,checkOutDate,status,prepaidAmount,totalAmount";

    public BookingRepoImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void gererateBooking(BookingModel b) throws IOException {
        List<BookingModel> list = findAll();
        list.add(b);
        writeAll(list);
    }

    private void writeAll(List<BookingModel> list) throws IOException {
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        bw.write(HEADER);
        bw.newLine();
        String line;
        for(BookingModel b : list){
            line = b.getId()+","+
                    b.getRoomId()+","+
                    b.getCustomerId()+","+
                    b.getCheckInDate()+","+
                    b.getCheckOutDate()+","+
                    b.getStatus()+","+
                    b.getPrepaidAmount()+","+
                    b.getTotalAmount();
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }


    @Override
    public List<BookingModel> findAll() throws IOException {
        List<BookingModel> list = new ArrayList<>();
        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine();
        while((line= br.readLine()) != null){
            if(!line.trim().isEmpty()){
                BookingModel b = parseIntoBooking(line);
                list.add(b);
            }
        }
        br.close();
        return list;
    }

    private BookingModel parseIntoBooking(String line){
        String[] d = line.split("," ,-1);
        BookingModel b = new BookingModel();
        b.setId(d[0].trim());
        b.setRoomId(d[1].trim());
        b.setCustomerId(d[2].trim());
        b.setCheckInDate(LocalDate.parse(d[3].trim()));
        b.setCheckOutDate(LocalDate.parse(d[4].trim()));
        b.setStatus(BookingStatus.valueOf(d[5].trim()));
        b.setPrepaidAmount(Double.parseDouble(d[6].trim()));
        b.setTotalAmount(Double.parseDouble(d[7].trim()));

        return b;
    }

}
