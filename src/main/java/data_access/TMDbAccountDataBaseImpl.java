package data_access;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Media;
import entity.Watchlist;
import entity.User;
import entity.Movie;
import entity.TVShow;
import entity.Episode;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;


public class TMDbAccountDataBaseImpl implements TMDbAccountDataBase {
    private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNzJmZGIzYmQ2OWNmNmFmZDRhYmI5NzZiNTdjMWIxYSIsIm5iZiI6MTc2MTkxODY4MC4xMzMsInN1YiI6IjY5MDRiZWQ4MzU3M2VmMTQ4MDQ2MzY5MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GQkgkyQZ6-GvLMOJqIOu0jfwYXjuHjrdNDBBbuzswsM ";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String ACCOUNT_ID = "account_id";
    private final OkHttpClient client;

    private TMDbAccountDataBaseImpl() {
        this.client = new OkHttpClient();
    }

    //    Watchlist modification helper methods
    private boolean sendWatchlistRequest(Media media, boolean add){
        String mediaType = "movie";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("media_type", mediaType);
            jsonBody.put("media_id", media.getId());
            jsonBody.put("watchlist", add); // true for add, false for remove
        } catch (JSONException e) {
            System.err.println("Error creating JSON body: " + e.getMessage());
            return false;
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/account/" + ACCOUNT_ID + "/watchlist")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", "Bearer " + BEARER_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            // TMDb returns 201 for success, or 200 if the item status was simply updated
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addToWatchList(Media media, Watchlist watchlist) throws JSONException {
        // Note: The Watchlist parameter is ignored as TMDb API manages the single list per user.
        return sendWatchlistRequest(media, true);
    }

    @Override
    public boolean removeFromWatchList(Media media, Watchlist watchlist, User user) throws JSONException {
        // Note: Watchlist and User parameters are ignored as the token handles auth/account.
        return sendWatchlistRequest(media, false);
    }

    @Override
    public Media[] getWatchlistMedia() throws JSONException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/account/" + ACCOUNT_ID + "/watchlist/movies")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + BEARER_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                // Parsing data logic
                JSONObject responseBody = new JSONObject(response.body().string());
                JSONArray results = responseBody.getJSONArray("results");
                // The results JSONArray must be iterated over to build Media[] entities.
                System.out.println("Watchlist fetched successfully. Implement JSON parsing to convert to Media entities.");
                return new Media[results.length()]; // Placeholder return
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Media[0];
    }

    // Favorites
    private boolean sendFavoriteRequest(Media media, boolean favorite) {
        String mediaType = "movie";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("media_type", mediaType);
            jsonBody.put("media_id", media.getId());
            jsonBody.put("favorite", favorite); // true for add, false for remove
        } catch (JSONException e) {
            System.err.println("Error creating JSON body: " + e.getMessage());
            return false;
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL + "/account/" + ACCOUNT_ID + "/favorite")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", "Bearer " + BEARER_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addToFavorites(Media media) throws JSONException {
        return sendFavoriteRequest(media, true);
    }

    @Override
    public boolean removeFromFavorites(Media media) throws JSONException {
        return sendFavoriteRequest(media, false);
    }

    @Override
    public Media[] getFavorites() throws JSONException {
        // Implementation is nearly identical to getWatchlistMovies, but uses the "/favorites/movies" endpoint
        Request request = new Request.Builder()
                .url(BASE_URL + "/account/" + ACCOUNT_ID + "/favorite/movies")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + BEARER_TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                // PUT PARSING LOGIC HERE
                System.out.println("Favorites fetched successfully. Implement JSON parsing to convert to Media entities.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Media[0];
    }

    @Override
    public int getRating(Media media) throws JSONException {
        // NOTE: TMDb API requires a guest_session_id or a full session_id for rating, so we need to
        // ensure we can generate a session ID first.
        System.out.println("Rating functionality requires a user session ID.");
        return 0;
    }

}
