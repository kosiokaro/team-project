package interface_adapter.favorites;

public class AddToFavoritesState {
    private boolean wasAdded;
    private String error;

    public boolean getWasAdded(){
        return wasAdded;
    }
    public void setWasAdded(boolean wasAdded){
        this.wasAdded = wasAdded;
    }
    public void setAddError(String error){this.error = error;}
}