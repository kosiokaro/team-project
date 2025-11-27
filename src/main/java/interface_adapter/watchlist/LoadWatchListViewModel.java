package interface_adapter.watchlist;

import interface_adapter.ViewModel;
import entity.Movie;
import java.util.ArrayList;

public class LoadWatchListViewModel extends ViewModel {
    public LoadWatchListViewModel() {
        super("loadWatchlist");
        setState(new ArrayList<>());
    }

    public ArrayList<Movie> getMovies() {
        return (ArrayList<Movie>) getState();
    }

    public void setMovies(ArrayList<Movie> movies) {
        setState(movies);
    }
}
