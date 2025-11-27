package interface_adapter.watchlist;

import entity.Movie;

import java.util.ArrayList;
public class LoadWatchListState {
    ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies(){
        return movies;
    }
    public void setMovies(ArrayList<Movie> movies){
        this.movies = movies;
    }
}
