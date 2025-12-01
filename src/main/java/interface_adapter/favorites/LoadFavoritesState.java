package interface_adapter.favorites;

import entity.Movie;
import java.util.ArrayList;

public class LoadFavoritesState {
    ArrayList<Movie> movies;
    String error;

    public ArrayList<Movie> getMovies(){
        return movies;
    }
    public void setMovies(ArrayList<Movie> movies){
        this.movies = movies;
    }
    public void setError(String error){this.error = error;}
}