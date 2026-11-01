package com.dgz3.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpService {
    private final String youTubeSearchUrl 
        = "https://www.youtube.com/results?search_query=";

    public String fetchYouTubeSearch(String searchQuery)
    {
        HttpResponse<String> response = null;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format("%s%s",youTubeSearchUrl,searchQuery)))
            .GET()
            .build();

        try {
            response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        } catch(Exception e){
            System.out.printf(
                "HttpService"
            );
        }
        if (response.statusCode() == 200){
            return(response.body());
        }
        /* TODO report error or something */
        return(null);
    }
}
