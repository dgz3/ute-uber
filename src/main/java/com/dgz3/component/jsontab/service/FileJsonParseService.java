package com.dgz3.component.jsontab.service;

import java.io.File;
import java.util.List;

import com.dgz3.component.jsontab.model.Track;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileJsonParseService {
    public List<Track> parse(File file) 
    {
        Track[] trackList = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            trackList = objectMapper.readValue(
                file,
                Track[].class
            );
        } catch(Exception e) {
            System.out.printf("FileJsonParseService Error: %s", e.getMessage());
        }
        return( List.of(trackList) );
    }
}
