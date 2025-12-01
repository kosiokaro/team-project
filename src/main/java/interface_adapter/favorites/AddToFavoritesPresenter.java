package interface_adapter.favorites;

import use_case.favorites.addToFavorites.AddToFavoritesOutputData;
import use_case.favorites.addToFavorites.AddToFavoritesOutputBoundary;

public class AddToFavoritesPresenter implements AddToFavoritesOutputBoundary {
    private final AddToFavoritesViewModel viewModel;

    public AddToFavoritesPresenter(AddToFavoritesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccess(AddToFavoritesOutputData outputData) {
        final AddToFavoritesState viewState = viewModel.getState();
        viewState.setWasAdded(true);
//        outputData was added for T
        viewModel.firePropertyChange();
        System.out.println("✅ Presenter: Successfully added to favorites");
    }

    @Override
    public void presentError(String error) {
        final  AddToFavoritesState viewState = viewModel.getState();
        viewState.setAddError(error);
        viewModel.firePropertyChange();
    }
}