package data_access;

import entity.Movie;
import entity.BrowseRequestBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.watchlist.loadWatchList.LoadWatchListDataAccessInterface;

public class WatchlistMovieDataAccess implements LoadWatchListDataAccessInterface {
    private final String API_KEY;

    public WatchlistMovieDataAccess(String apiKey) {
        this.API_KEY = apiKey;
    }

    @Override
    public Movie getMovieById(int movieId) {
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
}
