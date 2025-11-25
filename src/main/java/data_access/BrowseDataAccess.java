package data_access;

import entity.BrowsePage;
import entity.BrowseRequestBuilder;
import entity.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;



public class BrowseDataAccess implements use_case.browse.BrowseDataAccess {

    private static final String STATUS_CODE = "status_code";
    private static final int SUCCESS_CODE = 200;

    @Override
    public BrowsePage getPage(BrowseRequestBuilder browseRequestBuilder) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(browseRequestBuilder.getRequest())
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization","")
                .build();

        try{
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            return makeBrowsePage(responseBody);
            //TODO make another API call to get runtimes
        } catch (Exception e) {
            Movie[] movies = {};
            return new BrowsePage(movies,e);
        }

    }

    @Override
    public BrowsePage makeBrowsePage(JSONObject jsonObject) {
        JSONArray results = jsonObject.getJSONArray("results");
        Movie[] movies = new Movie[results.length()];

        for (int i = 0; i < results.length(); i++) {
            JSONObject o = results.getJSONObject(i);

            String title = o.optString("title", "");
            int referenceNumber = o.optInt("id", -1);

            JSONArray genreArray = o.optJSONArray("genre_ids");
            int[] genreIDs = new int[genreArray != null ? genreArray.length() : 0];
            for (int j = 0; j < genreIDs.length; j++) {
                genreIDs[j] = genreArray.getInt(j);
            }

            String overview = o.optString("overview", "");
            double rating = o.optDouble("vote_average", 0.0);
            String releaseDate = o.optString("release_date", "");
            int runtime = 0;

            String posterUrl = o.optString("poster_path", null);
            if (posterUrl != null) {
                posterUrl = "https://image.tmdb.org/t/p/w500" + posterUrl;
            }

            String language = o.optString("original_language", "");

            movies[i] = new Movie(
                    title,
                    referenceNumber,
                    genreIDs,
                    overview,
                    rating,
                    releaseDate,
                    runtime,
                    posterUrl,
                    language
            );
        }


        return new BrowsePage(movies,jsonObject.getInt("page"));

    }

}
