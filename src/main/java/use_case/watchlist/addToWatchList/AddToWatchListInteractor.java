package use_case.watchlist.addToWatchList;

public class AddToWatchListInteractor implements AddToWatchListInputBoundaryData {

    private final AddToWatchListDataAccessInterface dataAccessObject;
    private final AddToWatchListOutputBoundary presenter;

    public AddToWatchListInteractor(AddToWatchListDataAccessInterface dataAccessObject,
                                    AddToWatchListOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    public void addMovieToWatchlist(AddToWatchListInputData input) {
        try {

            Integer movieId = input.refNumber;
            String username = input.username;
            this.dataAccessObject.addMovieToWatchlist(username, movieId);

            AddToWatchListOutputData outputData = new AddToWatchListOutputData(
                    username, movieId, "Movie added successfully");
            presenter.presentSuccess(outputData);
        } catch (Exception e) {
            e.printStackTrace();
            presenter.presentError(e.getMessage());
        }
    }
}