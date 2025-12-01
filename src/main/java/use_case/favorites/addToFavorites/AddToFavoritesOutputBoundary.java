package use_case.favorites.addToFavorites;

public interface AddToFavoritesOutputBoundary {
    void presentSuccess(AddToFavoritesOutputData outputData);
    void presentError(String error);
}
