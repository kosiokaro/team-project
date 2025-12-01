package use_case.favorites.deleteFromFavorites;

public class DeleteFromFavoritesInteractor implements  DeleteFromFavoritesInputBoundary {
    private final DeleteFromFavoritesDataAccessInterface dataAccessObject;
    private final DeleteFromFavoritesOutputBoundary presenter;

    public DeleteFromFavoritesInteractor(DeleteFromFavoritesDataAccessInterface dataAccessObject, DeleteFromFavoritesOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    public void deleteFromFavorites(DeleteFromFavoritesInputData input) {
        try {
            String refNumber = input.refNumber;
            String username = input.username;
            this.dataAccessObject.deleteFromFavorites(username, Integer.parseInt(refNumber));

            DeleteFromFavoritesOutputData outputData = new DeleteFromFavoritesOutputData(
                    input.username, Integer.parseInt(refNumber), "Movie removed successfully");
            presenter.presentSuccess(outputData);
        } catch (Exception e) {
            presenter.presentError(e.getMessage());
        }
    }
}
