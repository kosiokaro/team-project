package interface_adapter.watchlist;

import entity.Movie;

import java.util.ArrayList;

public class AddToWatchListState {
    boolean wasAdded;

    public boolean getWasAdded(){
        return wasAdded;
    }
    public void setWasAdded(boolean wasAdded){
        this.wasAdded = wasAdded;
    }


}
