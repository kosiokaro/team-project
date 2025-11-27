package use_case.watchlist.addToWatchList;

public class AddToWatchListInteractor implements AddToWatchListInputBoundaryData {


    private final AddToWatchListDataAccessInterface dataAccessObject;
    private final AddToWatchListOutputBoundary presenter; // Assuming you have a presenter


    public AddToWatchListInteractor(AddToWatchListDataAccessInterface dataAccessObject, AddToWatchListOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;

    }

    public void addMovieToWatchlist(AddToWatchListInputData input) {
        try {
            String refNumber = input.refNumber;
            String username = input.username;
            this.dataAccessObject.addMovieToWatchlist(username, Integer.parseInt(refNumber));

            AddToWatchListOutputData outputData = new AddToWatchListOutputData(
                    input.username, Integer.parseInt(refNumber), "Movie added successfully");
            presenter.presentSuccess(outputData);
        } catch (Exception e) {
            presenter.presentError(e.getMessage());
        }
        }
}
