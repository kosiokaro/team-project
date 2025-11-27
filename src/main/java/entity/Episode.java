package entity;

public class Episode {
    private int ShowID;
    private int referenceNumber;
    private int seasonNumber;
    private int episodeNumber;
    public String title;
    public String overview;
    public final int runtime;
    public double rating;
    public final String releaseDate;

    public Episode(int id,int refnumber, int season,int episodenumber,String title,String overview,int runtime,double rating,String releaseDate) {
        this.ShowID = id;
        this.referenceNumber = refnumber;
        this.seasonNumber = season;
        this.episodeNumber = episodenumber;
        this.title = title;
        this.overview = overview;
        this.runtime = runtime;
        this.rating = rating;
        this.releaseDate = releaseDate;


    }
}
