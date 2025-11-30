package interface_adapter.favorites;

import use_case.favorites.addToFavorites.AddToFavoritesInputBoundary;
import use_case.favorites.addToFavorites.AddToFavoritesInputData;
import use_case.favorites.deleteFromFavorites.DeleteFromFavoritesInputBoundary;
import use_case.favorites.deleteFromFavorites.DeleteFromFavoritesInputData;

public class FavoritesController {
    private final AddToFavoritesInputBoundary addToFavoritesInteractor;
    private final DeleteFromFavoritesInputBoundary deleteFavoritesListInteractor;

    public FavoritesController(AddToFavoritesInputBoundary addToFavoritesInteractor,
                               DeleteFromFavoritesInputBoundary deleteFromFavoritesInteractor) {
        this.addToFavoritesInteractor = addToFavoritesInteractor;
        this.deleteFavoritesListInteractor = deleteFromFavoritesInteractor;
    }

    public void addToFavorites(String username, String refNumber) {
        AddToFavoritesInputData inputData = new AddToFavoritesInputData();
        inputData.username = username;
        inputData.refNumber = refNumber;
        addToFavoritesInteractor.addMovieToFavorites(inputData);
    }

    public void removeFromFavorites(String username, String refNumber) {
        DeleteFromFavoritesInputData inputData = new DeleteFromFavoritesInputData();
        inputData.username = username;
        inputData.refNumber = refNumber;
        deleteFavoritesListInteractor.deleteFromFavorites(inputData);
    }
}
