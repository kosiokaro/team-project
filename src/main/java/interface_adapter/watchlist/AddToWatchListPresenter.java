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
        viewModel.setState(outputData.getMessage());
        viewModel.firePropertyChange();
    }

    @Override
    public void presentError(String error) {
        viewModel.setState(error);
        viewModel.firePropertyChange();
    }
}