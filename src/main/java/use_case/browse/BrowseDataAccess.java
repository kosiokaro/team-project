package use_case.browse;

import entity.BrowsePage;
import entity.BrowseRequestBuilder;
import org.json.JSONObject;

import java.io.IOException;

public interface BrowseDataAccess {

    BrowsePage getPage(BrowseRequestBuilder browseRequestBuilder);

    BrowsePage makeBrowsePage(JSONObject jsonObject);

}
