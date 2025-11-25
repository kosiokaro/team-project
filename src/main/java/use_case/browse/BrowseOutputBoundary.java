package use_case.browse;

public interface BrowseOutputBoundary {

    void populateView(BrowseOutputData browseOutputData);

    void prepareError(BrowseOutputData browseOutputData);

    void prepareSelectMovieView();

}
