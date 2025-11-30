package use_case.favorites.deleteFromFavorites;

public class DeleteFromFavoritesOutputData {
    private final String username;
    private final int refNumber;
    private final String message;

    public DeleteFromFavoritesOutputData(String username, int refNumber, String message) {
        this.username = username;
        this.refNumber = refNumber;
        this.message = message;
    }

    public String getUsername() { return username; }
    public int getRefNumber() { return refNumber; }
    public String getMessage() { return message; }
}
