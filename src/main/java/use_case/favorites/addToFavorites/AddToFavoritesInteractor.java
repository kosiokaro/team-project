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
            String refNumber = input.refNumber;
            String username = input.username;
            this.dataAccessObject.addMovieToFavorites(username, Integer.parseInt(refNumber));

            AddToFavoritesOutputData outputData = new AddToFavoritesOutputData(
                    input.username, Integer.parseInt(refNumber), "Movie added successfully");
            presenter.presentSuccess(outputData);
        } catch (Exception e) {
            presenter.presentError(e.getMessage());
        }
    }
}