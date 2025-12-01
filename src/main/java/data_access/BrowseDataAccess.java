package data_access;

import com.google.gson.JsonObject;
import entity.BrowsePage;
import entity.BrowseRequestBuilder;
import entity.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class BrowseDataAccess implements use_case.browse.BrowseDataAccess {
    private final String API_KEY;

    public BrowseDataAccess(){
        this.API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNzJmZGIzYmQ2OWNmNmFmZDRhYmI5NzZiNTdjMWIxYSIsIm5iZiI6MTc2MTkxODY4MC4xMzMsInN1YiI6IjY5MDRiZWQ4MzU3M2VmMTQ4MDQ2MzY5MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GQkgkyQZ6-GvLMOJqIOu0jfwYXjuHjrdNDBBbuzswsM";
    }

    @Override
    public BrowsePage getPage(BrowseRequestBuilder browseRequestBuilder) {
        OkHttpClient client = new OkHttpClient();
        System.out.println(browseRequestBuilder.getRequest());
        Request request = new Request.Builder()
                .url(browseRequestBuilder.getRequest())
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization",API_KEY)
                .build();

        try{
            final Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                final JSONObject responseBody = new JSONObject(response.body().string());

                List<JSONObject> missingDataResponses = new ArrayList<>();
                Request[] requests = makeMovieIdRequests(responseBody);
                for(Request req: requests){
                    Response resp = client.newCall(req).execute();
                    if(resp.isSuccessful()){
                        JSONObject body = new JSONObject(resp.body().string());
                        missingDataResponses.add(body);
                    }
                    Thread.sleep(50);
                }
                return makeBrowsePage(responseBody,missingDataResponses);
            }
        } catch (Exception e) {
            Movie[] movies = {};
            return new BrowsePage(movies,e);
        }
        return null;

    }

    // Make a new request to retrieve the Movie runtimes and Genres
    public Request[] makeMovieIdRequests(JSONObject jsonObject) {

        JSONArray results = jsonObject.getJSONArray("results");
        Request[] idRequests = new Request[results.length()];
        for (int i = 0; i < results.length(); i++) {
            JSONObject o = results.getJSONObject(i);
            int referenceNumber = o.optInt("id", 10000);
            idRequests[i] = new Request.Builder()
                    .url(new BrowseRequestBuilder(referenceNumber).getRequest())
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization",API_KEY)
                    .build();
        }
        return idRequests;

    }

    public BrowsePage makeBrowsePage(JSONObject jsonObject,List<JSONObject> jsonObject2) {
        JSONArray results = jsonObject.getJSONArray("results");
        Movie[] movies = new Movie[results.length()];
        String[] genreNames = null;
        for (int i = 0; i < results.length(); i++) {
            JSONObject fullMovie = jsonObject2.get(i);
            JSONObject o = results.getJSONObject(i);

            String title = o.optString("title", "");
            int referenceNumber = o.optInt("id", -1);
            JSONArray genreObjArray = fullMovie.optJSONArray("genres");
            if (genreObjArray != null) {
                genreNames = new String[genreObjArray.length()];
                for (int j = 0; j < genreNames.length; j++) {
                    JSONObject g = genreObjArray.getJSONObject(j);
                    genreNames[j] = g.optString("name", "");
                }
            }


            String overview = o.optString("overview", "");
            double rating = o.optDouble("vote_average", 0.0);
            String releaseDate = o.optString("release_date", "");
            int runtime = fullMovie.optInt("runtime", 0);

            String posterUrl = o.optString("poster_path", null);
            if (posterUrl != null) {
                posterUrl = "https://image.tmdb.org/t/p/w500" + posterUrl;
            }

            String language = o.optString("original_language", "");

            movies[i] = new Movie(
                    title,
                    referenceNumber,
                    genreNames,
                    overview,
                    rating,
                    releaseDate,
                    runtime,
                    posterUrl,
                    language
            );
        }

        for (Movie movie : movies) {
            System.out.println(movie.toString());
        }
        return new BrowsePage(movies,jsonObject.getInt("page"));

    }




}
