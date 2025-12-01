package interface_adapter.watchlist;

import use_case.watchlist.addToWatchList.AddToWatchListOutputData;
import use_case.watchlist.addToWatchList.AddToWatchListOutputBoundary;

public class AddToWatchListPresenter implements AddToWatchListOutputBoundary {
    private final AddToWatchListViewModel viewModel;

    public AddToWatchListPresenter(AddToWatchListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccess(AddToWatchListOutputData outputData) {
        final AddToWatchListState viewState = viewModel.getState();
        viewState.setWasAdded(true);
        viewModel.firePropertyChange();
    }

    @Override
    public void presentError(String error) {
        final AddToWatchListState viewState = viewModel.getState();
        viewState.setWasAdded(false);
        viewModel.firePropertyChange();
    }
}