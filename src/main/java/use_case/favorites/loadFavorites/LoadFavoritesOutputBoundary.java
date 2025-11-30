package use_case.favorites.loadFavorites;

public interface LoadFavoritesOutputBoundary {
    void presentFavorites(LoadFavoritesOutputData outputData);
    void presentError(String error);
}
