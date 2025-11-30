package use_case.favorites.deleteFromFavorites;

public interface DeleteFromFavoritesOutputBoundary {
    void presentSuccess(DeleteFromFavoritesOutputData outputData);
    void presentError(String error);
}
