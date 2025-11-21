package entity;

public abstract class Media {
    public String title;
    private final int referenceNumber;
    public int[] genreIDs;
    public String posterUrl;
    public String language;

    public Media(String title, int referenceNumber, int[] genreIDs,String posterUrl,String language) {
        this.title = title;
        this.referenceNumber = referenceNumber;
        this.genreIDs = genreIDs;
        this.posterUrl = posterUrl;
        this.language =  language;
    }

    public int  getReferenceNumber() {
        return referenceNumber;
    }
}
