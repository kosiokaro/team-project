package BrowseTest;

import data_access.BrowseDataAccess;
import entity.BrowsePage;
import entity.BrowseRequestBuilder;
import entity.Movie;

public class BrowseDataAccessTest {
    public static void main(String[] args) {
        BrowseDataAccess browseDataAccess = new BrowseDataAccess();
        BrowseRequestBuilder browseRequestBuilder = new BrowseRequestBuilder();
        browseRequestBuilder.sortByRatingDesc();
        browseRequestBuilder.setReleaseYear("2006");
        BrowsePage browsePage = browseDataAccess.getPage(browseRequestBuilder);
        System.out.println(browsePage.getMovies().length);
        for (Movie movie : browsePage.getMovies()) {

            System.out.println(movie);
        }


    }

}
