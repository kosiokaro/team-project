package use_case.favorites.addToFavorites;

public class AddToFavoritesInteractor implements AddToFavoritesInputBoundary {
    private final AddToFavoritesDataAccessInterface dataAccessObject;
    private final AddToFavoritesOutputBoundary presenter;

    public AddToFavoritesInteractor(AddToFavoritesDataAccessInterface dataAccessObject, AddToFavoritesOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    public void addMovieToFavorites(AddToFavoritesInputData input) {
        try {
            Integer refNumber = input.refNumber;
            String username = input.username;
            this.dataAccessObject.addMovieToFavorites(username, refNumber);

            AddToFavoritesOutputData outputData = new AddToFavoritesOutputData(
                    input.username, refNumber, "Movie added successfully");
            presenter.presentSuccess(outputData);
        } catch (Exception e) {
            presenter.presentError(e.getMessage());
        }
    }
}