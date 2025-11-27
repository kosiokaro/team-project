package data_access;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import entity.MediaDetailsResponse;
import use_case.clicking.ClickingDataAccessInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClickingDataAccessTMDb implements ClickingDataAccessInterface {

    private static final String API_KEY = "aaebf7ad961711073ad8cc634faaa700";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private final Gson gson = new Gson();

    @Override
    public MediaDetailsResponse fetchDetailsById(int id) {
        try {
            String endpoint = "https://api.themoviedb.org/3/movie/" + id
                    + "?api_key=" + API_KEY + "&language=en-US";

            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.err.println("HTTP Error: " + responseCode);
                return null;
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            TMDbMovieResponse tmdbResponse = gson.fromJson(reader, TMDbMovieResponse.class);
            reader.close();
            conn.disconnect();

            if (tmdbResponse == null) {
                System.err.println("Failed to parse response for movie ID: " + id);
                return null;
            }

            return convertToMediaDetailsResponse(tmdbResponse);

        } catch (Exception e) {
            System.err.println("Error fetching movie details for ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Converts TMDb API response to our domain entity
     */
    private MediaDetailsResponse convertToMediaDetailsResponse(TMDbMovieResponse tmdb) {
        int year = 0;
        if (tmdb.releaseDate != null && tmdb.releaseDate.length() >= 4) {
            try {
                year = Integer.parseInt(tmdb.releaseDate.substring(0, 4));
            } catch (NumberFormatException e) {
                System.err.println("Could not parse year from: " + tmdb.releaseDate);
            }
        }

        String fullPosterUrl = "";
        if (tmdb.posterPath != null && !tmdb.posterPath.isEmpty()) {
            fullPosterUrl = IMAGE_BASE_URL + tmdb.posterPath;
        }

        List<String> genreNames = new ArrayList<>();
        if (tmdb.genres != null) {
            for (TMDbMovieResponse.Genre genre : tmdb.genres) {
                genreNames.add(genre.name);
            }
        }

        return new MediaDetailsResponse(
                tmdb.title != null ? tmdb.title : "Unknown Title",
                year,
                tmdb.originalLanguage != null ? tmdb.originalLanguage : "en",
                tmdb.voteAverage,
                genreNames,
                tmdb.overview != null ? tmdb.overview : "No overview available.",
                fullPosterUrl
        );
    }

    /**
     * DTO class that matches TMDb API JSON structure
     */
    private static class TMDbMovieResponse {
        @SerializedName("title")
        String title;

        @SerializedName("overview")
        String overview;

        @SerializedName("original_language")
        String originalLanguage;

        @SerializedName("poster_path")
        String posterPath;

        @SerializedName("release_date")
        String releaseDate;

        @SerializedName("vote_average")
        double voteAverage;

        @SerializedName("genres")
        List<Genre> genres;

        static class Genre {
            @SerializedName("id")
            int id;

            @SerializedName("name")
            String name;
        }
    }
}