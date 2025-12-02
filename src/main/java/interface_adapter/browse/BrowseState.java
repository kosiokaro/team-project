package interface_adapter.browse;

import use_case.browse.BrowseOutputData;

import java.util.ArrayList;
import java.util.List;

public class BrowseState {

    private int currentPageNumber;
    private List<List<BrowseOutputData.MovieCardData>> movieCards;
    private SearchTabState searchState;
    private String errorMessage;

    public class SearchTabState{
        private String query;
        private String year;
        private Boolean sortAscending;
        private Boolean sortDescending;
        private int pageNumber;

        public SearchTabState(){
            query = "";
            year = "";
            sortAscending = true;
            sortDescending = false;
            pageNumber = 1;
        }
        public String getQuery() {
            return query;
        }
        public void setQuery(String query) {this.query  = query;}
        public String getYear() {return year;}
        public void setYear(String year) {this.year = year;}
        public Boolean SortAscending() {return sortAscending;}
        public Boolean SortDescending() {return sortDescending;}
        public void setSortAscending(){
            this.sortAscending = true;
            this.sortDescending = false;
        }
        public void setSortDescending(){
            this.sortAscending = false;
            this.sortDescending = true;
        }
        public String getPageNumber(){
            return ""+this.pageNumber;
        }

    }

    public BrowseState() {
        this.searchState = new SearchTabState();
        movieCards = new ArrayList<>();
        this.currentPageNumber = 1;
        this.errorMessage = "";
    }

    public SearchTabState getSearchState() {
        return searchState;
    }

    public List<BrowseOutputData.MovieCardData> getMovies(int page) {
        return movieCards.get(page-1);
    }

    public void setMovies(List<BrowseOutputData.MovieCardData> m) {
        movieCards.add(m);
    }

    public void setError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {return errorMessage;}

    public void resetCurrentPage(){
        this.currentPageNumber = 1;
        movieCards.clear();
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }
    public void incrementPage() {
        this.currentPageNumber++;
    }
}
