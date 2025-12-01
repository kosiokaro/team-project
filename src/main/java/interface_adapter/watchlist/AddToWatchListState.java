package interface_adapter.watchlist;

import entity.Movie;
import java.util.ArrayList;

public class AddToWatchListState {
    private boolean wasAdded;
    private String message;

    public AddToWatchListState() {
        this.wasAdded = false;
        this.message = "";
    }

    public boolean getWasAdded() {
        return wasAdded;
    }

    public void setWasAdded(boolean wasAdded) {
        this.wasAdded = wasAdded;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void reset() {
        this.wasAdded = false;
        this.message = "";
    }
}