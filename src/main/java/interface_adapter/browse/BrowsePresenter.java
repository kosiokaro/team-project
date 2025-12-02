package interface_adapter.browse;

import interface_adapter.ViewManagerModel;
import interface_adapter.clicking.ClickingController;
import interface_adapter.watchlist.WatchListController;
import interface_adapter.clicking.ClickingState;
import interface_adapter.clicking.ClickingViewModel;
import interface_adapter.login.LoginState;
import interface_adapter.watchlist.WatchListController;
import use_case.browse.BrowseOutputBoundary;
import use_case.browse.BrowseOutputData;
import use_case.clicking.ClickingInteractor;
import view.ClickingView;

import java.util.List;

public class BrowsePresenter implements BrowseOutputBoundary {
    private final BrowseViewModel browseViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ClickingViewModel clickingViewModel;
    private final ClickingController  clickingController;


    public BrowsePresenter(BrowseViewModel browseViewModel, ViewManagerModel viewManagerModel,ClickingViewModel clickingViewModel, ClickingController clickingController) {
        this.browseViewModel = browseViewModel;
        this.viewManagerModel = viewManagerModel;
        this.clickingViewModel = clickingViewModel;
        this.clickingController = clickingController;
    }

    @Override
    public void populateView(BrowseOutputData browseOutputData) {
        System.out.println("Reached Presenter");
        final BrowseState browseState = browseViewModel.getState();
        browseState.setMovies(browseOutputData.getMovies());
        this.browseViewModel.firePropertyChange();
    }

    @Override
    public void prepareError(BrowseOutputData browseOutputData) {
        System.out.println("Reached Error");
        final BrowseState browseState = browseViewModel.getState();
        browseState.setMovies(browseOutputData.getMovies());
        System.out.println(browseOutputData.getErrorMessage());
        browseState.setError(browseOutputData.getErrorMessage());
        this.browseViewModel.firePropertyChange();

    }

    @Override
    public void prepareSelectMovieView(BrowseOutputData browseOutputData) {

        int movieID = browseOutputData.getMovies().get(0).getMovieID();
        System.out.println("passed on: " + movieID);
        this.viewManagerModel.setState(clickingViewModel.getViewName());
        this.clickingController.onClick(movieID);
        this.clickingViewModel.firePropertyChange();

    }

}
