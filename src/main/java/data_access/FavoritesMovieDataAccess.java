package data_access;

import entity.Movie;
import entity.BrowseRequestBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.favorites.loadFavorites.LoadFavoritesDataAccessInterface;
import use_case.favorites.addToFavorites.AddToFavoritesDataAccessInterface;
import use_case.favorites.deleteFromFavorites.DeleteFromFavoritesDataAccessInterface;

import java.io.*;
import java.util.*;

public class FavoritesMovieDataAccess implements
        LoadFavoritesDataAccessInterface,
        AddToFavoritesDataAccessInterface,
        DeleteFromFavoritesDataAccessInterface {

    private final String API_KEY;
    private final Map<String, List<Integer>> userFavorites; // username -> list of movie IDs
    private final String favoritesFilePath = "favorites.json";

    public FavoritesMovieDataAccess() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        this.API_KEY = dotenv.get("API_KEY");
        this.userFavorites = new HashMap<>();
        loadFavoritesFromFile();
    }

    @Override
    public void addMovieToFavorites(String username, int refNumber) {
        List<Integer> favorites = userFavorites.getOrDefault(username, new ArrayList<>());

        if (!favorites.contains(refNumber)) {
            favorites.add(refNumber);
            userFavorites.put(username, favorites);
            saveFavoritesToFile();
            System.out.println("✅ Saved movie " + refNumber + " to favorites for " + username);
            System.out.println("Current favorites: " + favorites);
        } else {
            System.out.println("⚠️ Movie " + refNumber + " already in favorites");
        }
    }

    @Override
    public void deleteFromFavorites(String username, int refNumber) {
        List<Integer> favorites = userFavorites.get(username);

        if (favorites != null) {
            favorites.remove(Integer.valueOf(refNumber));
            saveFavoritesToFile();
        }
    }

    @Override
    public ArrayList<Integer> getFavorites(String username) {
        return (ArrayList<Integer>) userFavorites.getOrDefault(username, new ArrayList<>());
    }

    @Override
    public Movie getMovieById(int movieId) {
        // Your existing implementation stays the same
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(new BrowseRequestBuilder(movieId).getRequest())
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", API_KEY)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject fullMovie = new JSONObject(response.body().string());

                String title = fullMovie.optString("title", "");
                int referenceNumber = fullMovie.optInt("id", -1);

                JSONArray genreObjArray = fullMovie.optJSONArray("genres");
                String[] genreNames = null;
                if (genreObjArray != null) {
                    genreNames = new String[genreObjArray.length()];
                    for (int j = 0; j < genreNames.length; j++) {
                        JSONObject g = genreObjArray.getJSONObject(j);
                        genreNames[j] = g.optString("name", "");
                    }
                }

                String overview = fullMovie.optString("overview", "");
                double rating = fullMovie.optDouble("vote_average", 0.0);
                String releaseDate = fullMovie.optString("release_date", "");
                int runtime = fullMovie.optInt("runtime", 0);

                String posterUrl = fullMovie.optString("poster_path", null);
                if (posterUrl != null) {
                    posterUrl = "https://image.tmdb.org/t/p/w500" + posterUrl;
                }

                String language = fullMovie.optString("original_language", "");

                return new Movie(title, referenceNumber, genreNames, overview,
                        rating, releaseDate, runtime, posterUrl, language);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper methods to persist favorites
    private void loadFavoritesFromFile() {
        File file = new File(favoritesFilePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0];
                    String[] movieIds = parts[1].split(",");
                    List<Integer> favorites = new ArrayList<>();
                    for (String id : movieIds) {
                        if (!id.isEmpty()) {
                            favorites.add(Integer.parseInt(id.trim()));
                        }
                    }
                    userFavorites.put(username, favorites);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFavoritesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(favoritesFilePath))) {
            for (Map.Entry<String, List<Integer>> entry : userFavorites.entrySet()) {
                String username = entry.getKey();
                List<Integer> favorites = entry.getValue();

                StringBuilder sb = new StringBuilder(username).append(":");
                for (int i = 0; i < favorites.size(); i++) {
                    sb.append(favorites.get(i));
                    if (i < favorites.size() - 1) {
                        sb.append(",");
                    }
                }
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Optional: method to get all favorites for a user
    public List<Integer> getFavoriteIds(String username) {
        return userFavorites.getOrDefault(username, new ArrayList<>());
    }
}