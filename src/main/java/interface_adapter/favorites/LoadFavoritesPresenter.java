package interface_adapter.favorites;

import entity.Movie;
import interface_adapter.home.HomeState;
import use_case.favorites.loadFavorites.LoadFavoritesOutputData;
import use_case.favorites.loadFavorites.LoadFavoritesOutputBoundary;
import interface_adapter.favorites.LoadFavoritesState;

import java.util.ArrayList;

public class LoadFavoritesPresenter implements LoadFavoritesOutputBoundary {

    private final LoadFavoritesViewModel viewModel;

    public LoadFavoritesPresenter(LoadFavoritesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void presentFavorites(LoadFavoritesOutputData outputData) {
        final LoadFavoritesState viewState = viewModel.getState();
        viewState.setMovies(outputData.getMovies());
        viewModel.firePropertyChange();
    }

    public void presentError(String error) {
        final LoadFavoritesState viewState = viewModel.getState();
        viewState.setError(error);
        viewModel.firePropertyChange();
    }
}
