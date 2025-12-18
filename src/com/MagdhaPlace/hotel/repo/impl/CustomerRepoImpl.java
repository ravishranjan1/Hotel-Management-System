package com.MagdhaPlace.hotel.repo.impl;

import com.MagdhaPlace.hotel.exceptions.CustomerAlreadyPresentException;
import com.MagdhaPlace.hotel.model.CustomerModel;
import com.MagdhaPlace.hotel.repo.CustomerRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepoImpl implements CustomerRepo {
    private final Path csvpath;
    private final String HEADER = "id,name,phone,email";

    public CustomerRepoImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void addCustomer(CustomerModel c) throws IOException{
        List<CustomerModel> list = findAll();
        list.add(c);
        ensureHeaderInFile();
        writeAll(list);
    }
    private void writeAll(List<CustomerModel> list) throws IOException {
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        ensureHeaderInFile();
        bw.write(HEADER);
        bw.newLine();

        String line;
        for(CustomerModel c : list){
            line = c.getId()+","+
                    c.getName()+","+
                    c.getPhone()+","+
                    c.getEmail();
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }


    @Override
    public List<CustomerModel> findAll() throws IOException {
        List<CustomerModel> list = new ArrayList<>();
        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine();
        while((line = br.readLine()) != null){
            if(!line.trim().isEmpty()){
                CustomerModel c = parseIntoCustomer(line);
                list.add(c);
            }
        }
        br.close();
        return list;
    }

    private CustomerModel parseIntoCustomer(String line){
        String[] word = line.split(",", -1);
        CustomerModel c = new CustomerModel();
        c.setId(word[0]);
        c.setName(word[1]);
        c.setPhone(word[2]);
        c.setEmail(word[3]);
        return c;
    }

    private void ensureHeaderInFile() throws IOException{
        if(Files.notExists(csvpath)){
            Files.createDirectories(csvpath.getParent());
            BufferedWriter bw = Files.newBufferedWriter(csvpath);
            bw.write(HEADER);
            bw.newLine();
            bw.close();
        }
    }

}
