package use_case.browse;

import entity.BrowsePage;

public interface BrowseInputBoundary {

    void execute(BrowseInputData browseInputData);

    void selectMovie(BrowseInputData browseInputData);


}
