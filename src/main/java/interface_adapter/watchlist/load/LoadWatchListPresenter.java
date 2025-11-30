package interface_adapter.watchlist.load;

import use_case.watchlist.loadWatchList.LoadWatchListOutputData;
import use_case.watchlist.loadWatchList.LoadWatchListOutputBoundaryData;

public class LoadWatchListPresenter implements LoadWatchListOutputBoundaryData {

    private final LoadWatchListViewModel viewModel;

    public LoadWatchListPresenter(LoadWatchListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void presentWatchlist(LoadWatchListOutputData outputData) {
        final LoadWatchListState viewState = viewModel.getState();
        viewState.setMovies(outputData.getMovies());
        viewModel.firePropertyChange();
    }

    public void presentError(String error) {
//        viewModel.setMovies(new ArrayList<>());
//        viewModel.firePropertyChange("error");
    }
}

