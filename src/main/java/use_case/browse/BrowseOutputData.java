package use_case.browse;

import entity.BrowsePage;
import entity.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrowseOutputData {

    private final List<MovieCardData> movies = new ArrayList<>();
    private int pageNumber;


    public BrowseOutputData(BrowsePage browsePage) {
        for (Movie movie : browsePage.getMovies()) {
            movies.add(new MovieCardData(
                    movie.title,
                    movie.rating,
                    movie.runtime,
                    Arrays.toString(movie.genreNames),
                    movie.posterUrl,
                    movie.getReferenceNumber()
            ));
        }
        pageNumber = browsePage.getPageNumber();
    }

    public BrowseOutputData(int referenceNumber) {
        MovieCardData movieCardData = new MovieCardData("",0,0,"","",referenceNumber);
        movies.add(movieCardData);
    }

    public static class MovieCardData {
        public String title;
        public double rating;
        public int runtime;
        public String genres;
        public String posterURL;
        private int movieID;

        public MovieCardData(String t, double r, int run, String g,String posterURL,int movieID) {
            this.title = t;
            this.rating = r;
            this.runtime = run;
            this.genres = g;
            this.posterURL = posterURL;
            this.movieID = movieID;
        }

       public int getMovieID() {
            return movieID;
       }
    }

    public List<MovieCardData> getMovies() {
        return movies;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
