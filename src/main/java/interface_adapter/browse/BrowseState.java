package interface_adapter.browse;

import use_case.browse.BrowseOutputData;

import java.util.ArrayList;
import java.util.List;

public class BrowseState {

    private int currentPageNumber;
    private List<List<BrowseOutputData.MovieCardData>> movieCards;


    public BrowseState() {
        movieCards = new ArrayList<>();
        this.currentPageNumber = 1;
    }

    public List<BrowseOutputData.MovieCardData> getMovies(int page) {
        return movieCards.get(page);
    }

    public void setMovies(List<BrowseOutputData.MovieCardData> m) {
        movieCards.add(m);
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }
    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }
}