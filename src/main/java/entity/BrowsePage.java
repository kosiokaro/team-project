package entity;


public class BrowsePage {
    private Movie[] movies;
    private final int pageNumber;
    public final Exception error;

    public BrowsePage(Movie[] movies,int pageNumber) {
        this.pageNumber = pageNumber;
        this.movies = movies;
        this.error = null;
    }

    public BrowsePage(Movie[] movies,Exception e){
        this.movies = movies;
        this.error = e;
        this.pageNumber = 1;
    }

    public Movie[] getMovies() {
        return movies;
    }

    public int getPageNumber() {return pageNumber;}
}
