package use_case.browse;

public interface BrowseOutputBoundary {

    void populateView(BrowseOutputData browseOutputData);


    void prepareError(String errorMessage);

    void prepareSelectMovieView();

}
