package interface_adapter.favorites;

import use_case.favorites.deleteFromFavorites.DeleteFromFavoritesOutputBoundary;
import use_case.favorites.deleteFromFavorites.DeleteFromFavoritesOutputData;

public class DeleteFromFavoritesPresenter implements DeleteFromFavoritesOutputBoundary {

    private final DeleteFromFavoritesViewModel viewModel;

    public DeleteFromFavoritesPresenter(DeleteFromFavoritesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccess(DeleteFromFavoritesOutputData outputData) {
        final DeleteFromFavoritesState viewState = viewModel.getState();
        viewState.setWasDeleted(true);
        viewModel.firePropertyChange();
    }

    @Override
    public void presentError(String error) {
        final DeleteFromFavoritesState viewState = viewModel.getState();
        viewState.setError(error);
        viewModel.firePropertyChange();
    }
}