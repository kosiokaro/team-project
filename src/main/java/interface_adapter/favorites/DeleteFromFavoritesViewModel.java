package interface_adapter.favorites;

import interface_adapter.ViewModel;

public class DeleteFromFavoritesViewModel extends ViewModel<DeleteFromFavoritesState> {
    public DeleteFromFavoritesViewModel() {
        super("addToFavorites");
        setState(new DeleteFromFavoritesState());
    }
}