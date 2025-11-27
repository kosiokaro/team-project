package interface_adapter.watchlist;

import use_case.watchlist.deleteFromWatchList.DeleteFromWatchListOutputData;
import use_case.watchlist.deleteFromWatchList.DeleteFromWatchListOutputBoundary;

public class DeleteFromWatchListPresenter implements DeleteFromWatchListOutputBoundary {

    private final DeleteFromWatchListViewModel viewModel;

    public DeleteFromWatchListPresenter(DeleteFromWatchListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccess(DeleteFromWatchListOutputData outputData) {
        viewModel.setState(outputData.getMessage());
        viewModel.firePropertyChange();
    }

    @Override
    public void presentError(String error) {
        viewModel.setState(error);
        viewModel.firePropertyChange();
    }
}