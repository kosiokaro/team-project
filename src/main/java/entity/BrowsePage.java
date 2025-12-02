package entity;


public class BrowsePage {
    private Movie[] movies;
    private final int pageNumber;
    private String error;

    public BrowsePage(Movie[] movies,int pageNumber) {
        this.pageNumber = pageNumber;
        this.movies = movies;
        this.error = "";
    }

    public BrowsePage(Movie[] movies,String error){
        this.movies = movies;
        this.error = error;
        this.pageNumber = 1;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Movie[] getMovies() {
        return movies;
    }

    public int getPageNumber() {return pageNumber;}

    public String getError() {return error;}
}
