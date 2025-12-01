package interface_adapter.favorites;

import interface_adapter.ViewModel;

public class LoadFavoritesViewModel extends ViewModel<LoadFavoritesState> {
    public LoadFavoritesViewModel() {
        super("loadFavorites");
        setState(new LoadFavoritesState());
    }
}
