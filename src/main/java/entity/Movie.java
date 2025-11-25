package entity;

public class Movie extends Media {
    public String overview;
    public double rating;
    public final String releaseDate;
    public final int runtime;


    public Movie(String title, int referenceNumber, int[] genreIDs, String overview, double rating, String releaseDate, int runtime,String posterURL,String language) {
        super(title,referenceNumber, genreIDs,posterURL,language);
        this.genreIDs = genreIDs;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.runtime = runtime;

    }

    public String getTitle() { return title; }
    public int getReferenceNumber() {
        int referenceNumber = 0;
        return referenceNumber; }
//    public List<String> getGenres() { return genres; }
//    public String getOverview() { return overview; }
//    public double getVoteAverage() { return voteAverage; }
}
