package com.example.NISearch.controller;

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
public class SearchController {

    private static final String API_KEY = "AIzaSyDSboFWal0n329ZHOpXm_tI_b5KDiOb6EI";
    private static final String SEARCH_ENGINE_ID = "f040a3e3b0a98415e";
    private static final String SEARCH_URL = "https://www.googleapis.com/customsearch/v1";

    @GetMapping("/")
    public String showSearchForm() {
        return "index";
    }

    @GetMapping("/search") // Handle both GET and POST requests to /search
    public String showSearchResults(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            try {
                JSONArray searchResults = fetchSearchResults(query);

                if (searchResults != null) {
                    List<SearchResult> results = new ArrayList<>();
                    for (int i = 0; i < searchResults.length(); i++) {
                        JSONObject item = searchResults.getJSONObject(i);
                        String title = item.getString("title");
                        String link = item.getString("link");
                        String snippet = item.getString("snippet");

                        SearchResult searchResult = new SearchResult(title, link, snippet);
                        results.add(searchResult);
                    }

                    model.addAttribute("results", results);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        model.addAttribute("query", query); // Add the query to the model for display on the results page
        return "results";
    }

    @PostMapping ("/search")
    private JSONArray fetchSearchResults(String query) throws IOException, JSONException {
        String url = SEARCH_URL + "?key=" + API_KEY + "&cx=" + SEARCH_ENGINE_ID + "&q=" + query;
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

//    @GetMapping("/search") // Handle GET requests to /search
//    public String showSearchForm(@RequestParam(required = false) String query, Model model) {
//        if (query != null) {
//            try {
//                JSONArray searchResults = fetchSearchResults(query);
//
//                List<SearchResult> results = new ArrayList<>();
//                for (int i = 0; i < searchResults.length(); i++) {
//                    JSONObject item = searchResults.getJSONObject(i);
//                    String title = item.getString("title");
//                    String link = item.getString("link");
//                    String snippet = item.getString("snippet");
//
//                    SearchResult searchResult = new SearchResult(title, link, snippet);
//                    results.add(searchResult);
//                }
//
//                model.addAttribute("results", results);
//                model.addAttribute("query", query); // Add the query to the model for display on the results page
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//
//            return "results";
//        } else {
//            // If the query is null, return the search form without any results
//            model.addAttribute("results", new ArrayList<SearchResult>());
//            return "index";
//        }
//    }
//
//    @PostMapping("/search")
//    public String performSearch(@RequestParam("query") String query) {
//        // Redirect to the results page with the query as a path variable
//        return "redirect:/search/" + query;
//    }


//    @GetMapping("/search")
//    public String showSearchForm(@RequestParam(required = false) String query, Model model) {
//        model.addAttribute("query", query != null ? query : ""); // Pass the query to the results page
//        model.addAttribute("results", new ArrayList<SearchResult>());
//        return "results";
//    }
//
//    @PostMapping("/search")
//    public String performSearch(@RequestParam("query") String query, Model model) {
//        try {
//            JSONArray searchResults = fetchSearchResults(query);
//
//            List<SearchResult> results = new ArrayList<>();
//            for (int i = 0; i < searchResults.length(); i++) {
//                JSONObject item = searchResults.getJSONObject(i);
//                String title = item.getString("title");
//                String link = item.getString("link");
//                String snippet = item.getString("snippet");
//
//                SearchResult searchResult = new SearchResult(title, link, snippet);
//                results.add(searchResult);
//            }
//
//            model.addAttribute("results", results);
//            model.addAttribute("query", query); // Add the query to the model for display on the results page
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        return "results";
//    }


//    @PostMapping("/search")
//    public String performSearch(@RequestParam("newQuery") String query, Model model) {
//        try {
//            JSONArray searchResults = fetchSearchResults(query);
//
//            List<SearchResult> results = new ArrayList<>();
//            for (int i = 0; i < searchResults.length(); i++) {
//                JSONObject item = searchResults.getJSONObject(i);
//                String title = item.getString("title");
//                String link = item.getString("link");
//                String snippet = item.getString("snippet");
//
//                SearchResult searchResult = new SearchResult(title, link, snippet);
//                results.add(searchResult);
//            }
//
//            model.addAttribute("results", results);
//            model.addAttribute("query", query); // Add the query to the model to display it on the results page
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        return "results";
//    }


//    @PostMapping("/search")
//    public String performSearch(@RequestParam("query") String query, Model model) {
//        try {
//            JSONArray searchResults = fetchSearchResults(query);
//
//            List<SearchResult> results = new ArrayList<>();
//            for (int i = 0; i < searchResults.length(); i++) {
//                JSONObject item = searchResults.getJSONObject(i);
//                String title = item.getString("title");
//                String link = item.getString("link");
//                String snippet = item.getString("snippet");
//
//                SearchResult searchResult = new SearchResult(title, link, snippet);
//                results.add(searchResult);
//            }
//
//            model.addAttribute("results", results);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        return "results";
//    }
//
//    @PostMapping("/search")
//    private JSONArray fetchSearchResults(String query) throws IOException, JSONException {
//        String url = SEARCH_URL + "?key=" + API_KEY + "&cx=" + SEARCH_ENGINE_ID + "&q=" + query;
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
//            return jsonResponse.getJSONArray("items");
//        } else {
//            System.out.println("Error: " + responseCode);
//            return null;
//        }
//    }
//
//
//}
//
//

//
//// TextSearchController.java
//package com.example.NISearch.controller;
//
//import com.example.NISearch.model.SearchResult;
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
//public class SearchController {
//
//    private static final String API_KEY = "03.1493574861:a22adca69ead4504323c0dc3da15f147";
//    private static final String USER_KEY = "nicktaser";
//    private static final String SEARCH_URL = "https://yandex.com/search/xml";
//
//
//    @GetMapping("/")
//    public String showSearchForm() {
//        return "index";
//    }
//
//    @GetMapping("/results")
//    public String showSearchResults() {
//        return "results";
//    }
//
//
//    @PostMapping("/search")
//    public String performSearch(@RequestParam("query") String query, Model model) {
//        try {
//            List<SearchResult> results = fetchSearchResults(query);
//            model.addAttribute("results", results);
//            model.addAttribute("query", query);
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//
//        return "results";
//    }
//
//    private List<SearchResult> fetchSearchResults(String query) throws IOException, JSONException {
//        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
//        String url = SEARCH_URL + "?user=" + USER_KEY + "&key=" + API_KEY + "&query=" + encodedQuery;
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
//            JSONArray resultsArray = jsonResponse.getJSONObject("response").getJSONArray("results");
//            List<SearchResult> results = new ArrayList<>();
//
//            for (int i = 0; i < resultsArray.length(); i++) {
//                JSONObject item = resultsArray.getJSONObject(i);
//                String title = item.getString("title");
//                String link = item.getString("url");
//                String snippet = item.getString("passages").replaceAll("<[^>]*>", ""); // Remove HTML tags from snippet
//
//                SearchResult searchResult = new SearchResult(title, link, snippet);
//                results.add(searchResult);
//            }
//
//            return results;
//        } else {
//            System.out.println("Error: " + responseCode);
//            return null;
//        }
//    }
//}
