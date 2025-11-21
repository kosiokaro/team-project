package use_case.browse;

public interface BrowseInputBoundary {

    void execute(BrowseInputData browseInputData);

    void updatePage();

    void selectMovie();

    void addToFavorite();

    void addToWatchlist();

}
