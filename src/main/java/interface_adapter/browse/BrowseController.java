package interface_adapter.browse;

import use_case.browse.BrowseInputBoundary;
import use_case.browse.BrowseInputData;

public class BrowseController {

    private final BrowseInputBoundary browseUseCaseInteractor;
    public BrowseController(BrowseInputBoundary browseUseCaseInteractor) {
        this.browseUseCaseInteractor = browseUseCaseInteractor;

    }

    public void executeQuery(String year, String title, String pageNumber, boolean sortAscending, boolean sortDescending)
    {
        System.out.println(year);
        System.out.println(title);
        System.out.println(pageNumber);
        System.out.println(sortAscending);
        System.out.println(sortDescending);
        System.out.println("Executing query");
        BrowseInputData inputData = new BrowseInputData(year, title, pageNumber, sortAscending, sortDescending);
        this.browseUseCaseInteractor.execute(inputData);

    }

    public void selectMovie(int movieRef)
    {
        BrowseInputData inputData = new BrowseInputData(movieRef);
        this.browseUseCaseInteractor.selectMovie(inputData);
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
