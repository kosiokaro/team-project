package interface_adapter.favorites;

import interface_adapter.ViewModel;

public class AddToFavoritesViewModel extends ViewModel<AddToFavoritesState> {
    public AddToFavoritesViewModel() {
        super("addToFavorites");
        setState(new AddToFavoritesState());
    }
}