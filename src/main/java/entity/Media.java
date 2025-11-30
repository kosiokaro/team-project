package entity;

public abstract class Media {
    public String title;
    private final int referenceNumber;
    public String[] genreNames;
    public String posterUrl;
    public String language;
    public int[] genreIDs;

    public Media(String title, int referenceNumber, String[] genreNames, String posterUrl, String language) {
        this.title = title;
        this.referenceNumber = referenceNumber;
        this.genreIDs = new int[]{};
        this.posterUrl = posterUrl;
        this.language = language;
        this.genreNames = genreNames;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getLanguage() {
        return language;
    }

    public String[] getGenres() {
        return genreNames;
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }
}