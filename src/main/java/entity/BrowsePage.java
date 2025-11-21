package entity;


public class BrowsePage {
    public Movie[] movies;
    private final int pageNumber;

    public BrowsePage(Movie[] movies,int pageNumber) {
        this.pageNumber = pageNumber;
        this.movies = movies;
    }

    public int getPageNumber() {return pageNumber;}
}
