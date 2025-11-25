package interface_adapter.browse;

import interface_adapter.ViewManagerModel;
import use_case.browse.BrowseOutputBoundary;
import use_case.browse.BrowseOutputData;

public class BrowsePresenter implements BrowseOutputBoundary {
    private final BrowseViewModel browseViewModel;
    private final ViewManagerModel viewManagerModel;


    public BrowsePresenter(BrowseViewModel browseViewModel, ViewManagerModel viewManagerModel) {
        this.browseViewModel = browseViewModel;
        this.viewManagerModel = viewManagerModel;
    }


    @Override
    public void populateView(BrowseOutputData browseOutputData) {
        final BrowseState browseState = browseViewModel.getState();
        browseState.addtitles(browseOutputData.getTitles());
        browseState.addimages(browseOutputData.getImages());
        browseState.addGenreIDs(browseOutputData.getGenreIDS());
        browseState.addreferenceNumbers(browseOutputData.getReferenceNumbers());
        browseState.addrunTimes(browseOutputData.getRunTimes());

        browseViewModel.firePropertyChange();
        //TODO FIX

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
