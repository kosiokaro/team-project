package use_case.watchlist.addToWatchList;

public class AddToWatchListInteractor implements AddToWatchListInputBoundaryData {


    final AddToWatchListDataAccessInterface dataAccessObject;
    // final AddToWatchListOutputBoundary presenter; // Assuming you have a presenter


    public AddToWatchListInteractor(AddToWatchListDataAccessInterface dataAccessObject, AddToWatchListOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;


    }


    public void addMovieToWatchlist(AddToWatchListInputData input) {
        String refNumber = input.refNumber;
        String username = input.username;


        this.dataAccessObject.addMovieToWatchlist(username, Integer.parseInt(refNumber));
    }
}
