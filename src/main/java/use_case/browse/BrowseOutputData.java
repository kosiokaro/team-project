package use_case.browse;

import entity.BrowsePage;
import entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class BrowseOutputData {

    private int currentPageNumber;
    private List<String> titles;
    private List<String> images;
    private List<Integer> referenceNumbers;
    private List<Integer> runTimes;
    private List<int[]> genreIDS;

    public BrowseOutputData(BrowsePage outputPage){
        currentPageNumber = outputPage.getPageNumber();
        titles = new ArrayList<>();
        images = new ArrayList<>();
        referenceNumbers = new ArrayList<>();
        runTimes = new ArrayList<>();
        genreIDS = new ArrayList<>();


        for(Movie movie: outputPage.getMovies()){
            titles.add(movie.title);
            images.add(movie.posterUrl);
            referenceNumbers.add(movie.getReferenceNumber());
            runTimes.add(movie.runtime);
            genreIDS.add(movie.genreIDs);
        }


    }
    public int getCurrentPageNumber() {return currentPageNumber;}
    public List<String> getTitles() {return titles;}
    public List<String> getImages() {return images;}
    public List<Integer> getReferenceNumbers() {return referenceNumbers;}
    public List<Integer> getRunTimes() {return runTimes;}
    public List<int[]> getGenreIDS() {return genreIDS;}

}
