package com.example.NISearch.controller;

import com.example.NISearch.model.ImageResult;
import com.example.NISearch.model.SearchResult;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

@Controller
public class ImageSearchController {

    private static final String API_KEY = "AIzaSyDSboFWal0n329ZHOpXm_tI_b5KDiOb6EI";
    private static final String SEARCH_ENGINE_ID = "f040a3e3b0a98415e";
    private static final String IMAGE_SEARCH_URL = "https://www.googleapis.com/customsearch/v1";
    private static final int NUM_IMAGES = 10;

    @GetMapping("/images")
    public String showImageSearchForm(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            model.addAttribute("query", query);
            return performImageSearch(query, model);
        } else {
            return "image_results";
        }
    }

    @PostMapping("/images")
    public String performImageSearch(@RequestParam(value = "query", required = false) String query, Model model) {
        try {
            JSONArray imageSearchResults = fetchImageSearchResults(query);

            if (imageSearchResults != null) {
                List<ImageResult> results = new ArrayList<>();
                for (int i = 0; i < imageSearchResults.length(); i++) {
                    JSONObject item = imageSearchResults.getJSONObject(i);
                    String imageUrl = item.getString("link");
                    String imageAltText = item.getString("snippet");

                    ImageResult imageResult = new ImageResult(imageUrl, imageAltText);
                    results.add(imageResult);
                }

                List<List<ImageResult>> imageRows = new ArrayList<>();
                for (int i = 0; i < results.size(); i += 4) {
                    int endIndex = Math.min(i + 4, results.size());
                    imageRows.add(results.subList(i, endIndex));
                }

                model.addAttribute("imageRows", imageRows);
            }

            model.addAttribute("query", query);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return "image_results";
    }




    private JSONArray fetchImageSearchResults(String query) throws IOException, JSONException {
        String url = IMAGE_SEARCH_URL + "?key=" + API_KEY + "&cx=" + SEARCH_ENGINE_ID
                + "&q=" + query + "&searchType=image" + "&num=" + NUM_IMAGES; // Добавляем параметр num
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getJSONArray("items");
        } else {
            System.out.println("Error: " + responseCode);
            return null;
        }
    }
}



//
//// ImageSearchController.java
//package com.example.NISearch.controller;
//
//import com.example.NISearch.model.ImageResult;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class ImageSearchController {
//
//    private static final String API_KEY = "03.1493574861:a22adca69ead4504323c0dc3da15f147";
//    private static final String USER_KEY = "nicktaser";
//    private static final String IMAGE_SEARCH_URL = "https://yandex.com/search/xml";
//
//
//    @GetMapping("/images")
//    public String showImageSearchForm() {
//        return "image_search";
//    }
//
//    @PostMapping("/images")
//    public String performImageSearch(@RequestParam("query") String query, Model model) {
//        try {
//            List<ImageResult> results = fetchImageSearchResults(query);
//            model.addAttribute("imageRows", partitionIntoRows(results, 4));
//            model.addAttribute("query", query);
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//
//        return "image_results";
//    }
//
//    private List<ImageResult> fetchImageSearchResults(String query) throws IOException, JSONException {
//        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
//        String url = IMAGE_SEARCH_URL + "?apikey=" + API_KEY + "&text=" + encodedQuery + "&format=json";
//        URL apiUrl = new URL(url);
//        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
//        connection.setRequestMethod("GET");
//
//        int responseCode = connection.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder response = new StringBuilder();
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                response.append(line);
//            }
//
//            reader.close();
//
//            JSONObject jsonResponse = new JSONObject(response.toString());
//            JSONArray resultsArray = jsonResponse.getJSONArray("items");
//            List<ImageResult> results = new ArrayList<>();
//
//            for (int i = 0; i < resultsArray.length(); i++) {
//                JSONObject item = resultsArray.getJSONObject(i);
//                String imageUrl = item.getString("preview");
//                String imageAltText = item.getString("snippet");
//
//                ImageResult imageResult = new ImageResult(imageUrl, imageAltText);
//                results.add(imageResult);
//            }
//
//            return results;
//        } else {
//            System.out.println("Error: " + responseCode);
//            return null;
//        }
//    }
//
//    private List<List<ImageResult>> partitionIntoRows(List<ImageResult> list, int size) {
//        List<List<ImageResult>> imageRows = new ArrayList<>();
//        for (int i = 0; i < list.size(); i += size) {
//            int endIndex = Math.min(i + size, list.size());
//            imageRows.add(list.subList(i, endIndex));
//        }
//        return imageRows;
//    }
//}
