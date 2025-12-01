package interface_adapter.favorites;

public class DeleteFromFavoritesState {
    private String error;
    private boolean wasDeleted;

    public String getError() {
        return this.error;
    }

    public void setWasDeleted(boolean wasDeleted){
        this.wasDeleted = wasDeleted;
    }

    public void setError(String error) {
        this.error = error;
    }
}
