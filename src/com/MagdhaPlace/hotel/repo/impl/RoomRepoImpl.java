package com.MagdhaPlace.hotel.repo.impl;

import com.MagdhaPlace.hotel.model.RoomModel;
import com.MagdhaPlace.hotel.model.RoomStatus;
import com.MagdhaPlace.hotel.repo.RoomRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomRepoImpl implements RoomRepo {
    private final Path csvpath ;
    private final String HEADER = "id,type,beds,nightlyRate,amenities,status";

    public RoomRepoImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void addRoom(RoomModel r) throws IOException {
        List<RoomModel> list = findAll();
        list.add(r);
        writeAll(list);
    }

    private void writeAll(List<RoomModel> list) throws IOException {
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        bw.write(HEADER);
        bw.newLine();
        String line;
        for(RoomModel r : list){
            String amenities = String.join(",",r.getAmenities());
            line = r.getId()+","+
                    r.getType()+","+
                    r.getBeds()+","+
                    r.getNightlyRate()+",\""+
                    amenities+"\","+
                    r.getStatus();
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }


    @Override
    public List<RoomModel> findAll() throws IOException {
        List<RoomModel> list = new ArrayList<>();
        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine();
        while((line= br.readLine()) != null){
            if(!line.trim().isEmpty()){
                RoomModel r = parseIntoRoomModel(line);
                list.add(r);
            }
        }
        br.close();
        return list;
    }

    private RoomModel parseIntoRoomModel(String line){
        String[] word = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        RoomModel r = new RoomModel();
        r.setId(word[0]);
        r.setType(word[1]);
        r.setBeds(Integer.parseInt(word[2]));
        r.setNightlyRate(Double.parseDouble(word[3]));

        Set<String> amenities = new HashSet<>();
        String am = word[4].replace("\"", "");
        for (String a : am.split(",")) {
            amenities.add(a.trim());
        }
        r.setAmenities(amenities);

        r.setStatus(RoomStatus.valueOf(word[5]));
        return r;
    }

}
