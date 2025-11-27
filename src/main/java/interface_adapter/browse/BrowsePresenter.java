package interface_adapter.browse;

import interface_adapter.ViewManagerModel;
import use_case.browse.BrowseOutputBoundary;
import use_case.browse.BrowseOutputData;

import java.util.List;

public class BrowsePresenter implements BrowseOutputBoundary {
    private final BrowseViewModel browseViewModel;
    private final ViewManagerModel viewManagerModel;


    public BrowsePresenter(BrowseViewModel browseViewModel, ViewManagerModel viewManagerModel) {
        this.browseViewModel = browseViewModel;
        this.viewManagerModel = viewManagerModel;
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
        //TODO IMPLEMENT
    }

    @Override
    public void prepareSelectMovieView() {
        //TODO Implement with other view
    }
}
