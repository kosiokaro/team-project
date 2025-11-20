package data_access;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import entity.Movie;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;


public class TMDbAccountDataBaseImpl implements TMDbAccountDataBase {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private final OkHttpClient client;
    private final String bearerToken;

    private static String getAPIToken() {
        String token = System.getenv("TMDB_BEARER_TOKEN");
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("TMDB_BEARER_TOKEN environment variable not set!");
        }
        return token;
    }

    public TMDbAccountDataBaseImpl() {
        this.client = new OkHttpClient();
        this.bearerToken = getAPIToken();
    }

    @Override
    public Movie[] getMovies() throws JSONException {
        // This is the correct endpoint for discovering popular movies
        String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + bearerToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                // 1. Get the main JSON object from the response body
                JSONObject responseBody = new JSONObject(response.body().string());

                // 2. Extract the 'results' array
                JSONArray results = responseBody.getJSONArray("results");

                // 3. Initialize the array of Movie entities
                Movie[] movies = new Movie[results.length()];

                // 4. Iterate and parse each movie object
                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieJson = results.getJSONObject(i);

                    // Extract necessary data fields
                    int id = movieJson.getInt("id");
                    String title = movieJson.getString("title");
                    String overview = movieJson.getString("overview");
                    String releaseDate = movieJson.getString("release_date");
                    double voteAverage = movieJson.getDouble("vote_average");

                    // Create a new Movie entity (assuming your Movie class supports these fields)
                    Movie movie = new Movie(id, title, overview, releaseDate, voteAverage);
                    movies[i] = movie;
                }

                System.out.println("Popular movies fetched and parsed successfully.");
                return movies; // Return the array of Movie objects
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return an empty array if the request fails
        return new Movie[0];
    }

    @Override
    public int getRating() throws JSONException {
        Request request = new Request.Builder()
                .url(BASE_URL + "guest_session/guest_session_id/rated/movies?language=en-US&page=1&sort_by=created_at.asc")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + bearerToken)  // Use instance variable
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JSONObject responseBody = new JSONObject(response.body().string());
                // TODO: Parse rating from response
                return responseBody.optInt("rating", 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
