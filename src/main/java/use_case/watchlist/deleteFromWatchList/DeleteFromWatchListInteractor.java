package use_case.watchlist.deleteFromWatchList;



public class DeleteFromWatchListInteractor implements DeleteFromWatchListInputBoundaryData{
    private final DeleteFromWatchListDataAccessInterface dataAccessObject;
    private final DeleteFromWatchListOutputBoundary presenter;

    public DeleteFromWatchListInteractor(DeleteFromWatchListDataAccessInterface dataAccessObject, DeleteFromWatchListOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;

    }

    public void deleteFromWatchlist(DeleteFromWatchListInputData input) {
        try {
            String refNumber = input.refNumber;
            String username = input.username;
            this.dataAccessObject.deleteFromWatchlist(username, Integer.parseInt(refNumber));

            DeleteFromWatchListOutputData outputData = new DeleteFromWatchListOutputData(
                    input.username, Integer.parseInt(refNumber), "Movie removed successfully");
            presenter.presentSuccess(outputData);
        } catch (Exception e) {
            presenter.presentError(e.getMessage());
        }
    }
}

