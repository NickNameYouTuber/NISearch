package com.example.NISearch.controller;

//import com.example.NISearch.model.VideoResult;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.jackson.JacksonFactory;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.model.SearchListResponse;
//import com.google.api.services.youtube.model.SearchResult;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class VideoSearchController {
//
//    private static final String APPLICATION_NAME = "NISearch";
//    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();
//    private static final String YOUTUBE_API_KEY = "AIzaSyB2MftzZgW2eS-FXrigyknU6wI2Qz6P12A";
//
//    @GetMapping("/videos")
//    public String performVideoSearch(@RequestParam("query") String query, Model model) {
//        try {
//            YouTube youtubeService = new YouTube.Builder(
//                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
//                    .setApplicationName(APPLICATION_NAME)
//                    .build();
//
//            YouTube.Search.List request = youtubeService.search()
//                    .list(List.of("id,snippet").toString());
//
//            String apiKey = YOUTUBE_API_KEY; // Replace with your YouTube API key
//            request.setKey(apiKey);
//            request.setQ(query);
//            request.setType(List.of("video").toString());
//
//            SearchListResponse response = request.execute();
//            List<SearchResult> searchResults = response.getItems();
//
//            List<VideoResult> results = new ArrayList<>();
//            for (SearchResult result : searchResults) {
//                String videoId = result.getId().getVideoId();
//                String title = result.getSnippet().getTitle();
//                String description = result.getSnippet().getDescription();
//                String thumbnailUrl = result.getSnippet().getThumbnails().getDefault().getUrl();
//
//                VideoResult videoResult = new VideoResult(videoId, title, description, thumbnailUrl);
//                results.add(videoResult);
//            }
//
//            model.addAttribute("results", results);
//            model.addAttribute("query", query);
//
//        } catch (GeneralSecurityException | IOException e) {
//            e.printStackTrace();
//        }
//
//        return "video_results";
//    }
//}


//import com.example.NISearch.model.VideoResult;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpRequest;
//import com.google.api.client.http.HttpRequestInitializer;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson.JacksonFactory;
//import com.google.api.client.util.DateTime;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.YouTubeRequestInitializer;
//import com.google.api.services.youtube.model.SearchListResponse;
//import com.google.api.services.youtube.model.SearchResult;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class VideoSearchController {
//
//    private static final String APPLICATION_NAME = "NISearch";
//    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
//    private static final String YOUTUBE_API_KEY = "AIzaSyB2MftzZgW2eS-FXrigyknU6wI2Qz6P12A";
//
//    @GetMapping("/videos")
//    public String performVideoSearch(@RequestParam("query") String query, Model model) {
//        try {
//            YouTube youtubeService = new YouTube.Builder(
//                    GoogleNetHttpTransport.newTrustedTransport(),
//                    JSON_FACTORY,
//                    getHttpRequestInitializer())
//                    .setApplicationName(APPLICATION_NAME)
//                    .build();
//
//            YouTube.Search.List request = youtubeService.search()
//                    .list(List.of("id,snippet").toString());
//
//            String apiKey = YOUTUBE_API_KEY; // Replace with your YouTube API key
//            request.setKey(apiKey);
//            request.setQ(query);
//            request.setType(List.of("video").toString());
//
//
//            SearchListResponse response = request.execute();
//            List<SearchResult> searchResults = response.getItems();
//
//            List<VideoResult> results = new ArrayList<>();
//            for (SearchResult result : searchResults) {
//                String videoId = result.getId().getVideoId();
//                String title = result.getSnippet().getTitle();
//                String description = result.getSnippet().getDescription();
//                String thumbnailUrl = result.getSnippet().getThumbnails().getDefault().getUrl();
//                DateTime publishedAt = result.getSnippet().getPublishedAt();
//
//                VideoResult videoResult = new VideoResult(videoId, title, description, thumbnailUrl);
//                results.add(videoResult);
//            }
//
//            model.addAttribute("results", results);
//            model.addAttribute("query", query);
//
//        } catch (GeneralSecurityException | IOException e) {
//            e.printStackTrace();
//        }
//
//        return "video_results";
//    }
//
//    private HttpRequestInitializer getHttpRequestInitializer() {
//        return new HttpRequestInitializer() {
//            @Override
//            public void initialize(HttpRequest request) throws IOException {
//                YouTubeRequestInitializer requestInitializer = new YouTubeRequestInitializer(YOUTUBE_API_KEY);
//                requestInitializer.initialize(request);
//            }
//        };
//    }
//
//}


import com.example.NISearch.model.VideoResult;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VideoSearchController {

    private static final String API_KEY = "AIzaSyB2MftzZgW2eS-FXrigyknU6wI2Qz6P12A";

    @GetMapping("/videos")
    public String performVideoSearch(@RequestParam("query") String query, Model model) {
        try {
            List<VideoResult> results = fetchVideoSearchResults(query);
            model.addAttribute("results", results);
            model.addAttribute("query", query);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        return "video_results";
    }

    private List<VideoResult> fetchVideoSearchResults(String query) throws IOException, GeneralSecurityException {
        // Подготовка YouTube Data API клиента
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory jsonFactory = new JacksonFactory();

        YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, httpRequest -> {
        }).setApplicationName("NISearch").build();

        // Выполнение запроса к YouTube Data API
        YouTube.Search.List search = youtube.search().list("snippet");
        search.setKey(API_KEY);
        search.setQ(query);
        search.setType("video");
        search.setMaxResults(10L); // Максимальное количество результатов (в данном случае - 10)

        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResults = searchResponse.getItems();

        List<VideoResult> results = new ArrayList<>();
        for (SearchResult searchResult : searchResults) {
            String videoId = searchResult.getId().getVideoId();
            String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
            String title = searchResult.getSnippet().getTitle();
            String description = searchResult.getSnippet().getDescription();
            String channelTitle = searchResult.getSnippet().getChannelTitle();
            long publishedAtMillis = searchResult.getSnippet().getPublishedAt().getValue();
            Instant publishedAt = Instant.ofEpochMilli(publishedAtMillis);
            LocalDateTime publishDateTime = LocalDateTime.ofInstant(publishedAt, ZoneId.systemDefault());
            String thumbnailUrl = searchResult.getSnippet().getThumbnails().getDefault().getUrl();

            VideoResult videoResult = new VideoResult(title, videoUrl, description, channelTitle, publishDateTime);
            videoResult.setThumbnailUrl(thumbnailUrl); // Set the thumbnail URL for the VideoResult
            results.add(videoResult);

        }

        return results;
    }
    // Метод преобразования объекта DateTime в объект Instant
    private Instant toInstant(DateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Instant.ofEpochMilli(dateTime.getValue());
    }

    // Метод преобразования объекта DateTime в объект LocalDateTime
    private LocalDateTime toLocalDateTime(DateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime.getValue()), ZoneId.systemDefault());
    }
}