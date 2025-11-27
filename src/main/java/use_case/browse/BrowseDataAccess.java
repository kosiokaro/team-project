package use_case.browse;

import entity.BrowsePage;
import entity.BrowseRequestBuilder;


public interface BrowseDataAccess {

    BrowsePage getPage(BrowseRequestBuilder browseRequestBuilder);

}
