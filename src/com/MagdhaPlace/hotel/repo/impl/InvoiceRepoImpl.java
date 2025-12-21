package com.MagdhaPlace.hotel.repo.impl;

import com.MagdhaPlace.hotel.model.InvoiceModel;
import com.MagdhaPlace.hotel.repo.InvoiceRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepoImpl implements InvoiceRepo {
    private final Path csvpath;

    public InvoiceRepoImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void gererateInvoice(InvoiceModel i) throws IOException {
        List<InvoiceModel> list = findAll();
        list.add(i);
        writeAll(list);
    }

    private void writeAll(List<InvoiceModel> list) throws IOException {
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        String line;
        for(InvoiceModel i : list){
            line = i.getId()+","+
                    i.getBookingId()+","+
                    i.getRoomCharges()+","+
                    i.getTaxes()+","+
                    i.getAdditionalCharges()+","+
                    i.getTotalDue();
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }


    @Override
    public List<InvoiceModel> findAll() throws IOException {
        List<InvoiceModel> list = new ArrayList<>();
        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine();
        while(line != null){
            if(!line.trim().isEmpty()){
                InvoiceModel i = parseIntoInvoice(line);
                list.add(i);
            }
        }
        br.close();
        return list;
    }

    private InvoiceModel parseIntoInvoice(String line){
        String[] word = line.split(",",-1);
        InvoiceModel i = new InvoiceModel();
        i.setId(word[0]);
        i.setBookingId(word[1]);
        i.setRoomCharges(Double.parseDouble(word[2]));
        i.setTaxes(Double.parseDouble(word[3]));
        i.setAdditionalCharges(Double.parseDouble(word[4]));
        i.setTotalDue(Double.parseDouble(word[5]));
        return i;
    }

}
