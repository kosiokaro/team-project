package use_case.watchlist.addToWatchList;

public class AddToWatchListOutputData {
    private final String username;
    private final int refNumber;
    private final String message;

    public AddToWatchListOutputData(String username, int refNumber, String message) {
        this.username = username;
        this.refNumber = refNumber;
        this.message = message;
    }

    public String getUsername() { return username; }
    public int getRefNumber() { return refNumber; }
    public String getMessage() { return message; }
}
