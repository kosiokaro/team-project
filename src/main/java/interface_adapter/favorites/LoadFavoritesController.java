package interface_adapter.favorites;

import use_case.favorites.loadFavorites.LoadFavoritesInputBoundary;
import use_case.favorites.loadFavorites.LoadFavoritesInputData;

public class LoadFavoritesController {

    private final LoadFavoritesInputBoundary interactor;

    public LoadFavoritesController(LoadFavoritesInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadFavorites(String username) {
        LoadFavoritesInputData inputData = new LoadFavoritesInputData(username);
        interactor.loadFavorites(inputData);
    }
}
