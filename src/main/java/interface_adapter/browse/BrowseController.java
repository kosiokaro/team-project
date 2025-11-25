package interface_adapter.browse;

import use_case.browse.BrowseInputBoundary;
import use_case.browse.BrowseInputData;

public class BrowseController {

    private final BrowseInputBoundary browseUseCaseInteractor;
    public BrowseController(BrowseInputBoundary browseUseCaseInteractor) {
        this.browseUseCaseInteractor = browseUseCaseInteractor;

    }

    public void executeQuery(String year, String title, String pageNumber)
    {
        BrowseInputData inputData = new BrowseInputData(year, title, pageNumber);
        this.browseUseCaseInteractor.execute(inputData);

    }

    public void selectMovie(int movieRef)
    {
        BrowseInputData inputData = new BrowseInputData(movieRef);
        this.browseUseCaseInteractor.execute(inputData);

    }

    public void addFavorite(int movieRef)
    {
        BrowseInputData inputData = new BrowseInputData(movieRef);
        this.browseUseCaseInteractor.addToFavorite(inputData);

    }

    public void addWatchlist(int movieRef)
    {
        BrowseInputData inputData = new BrowseInputData(movieRef);
        this.browseUseCaseInteractor.addToWatchlist(inputData);

    }



}
