package use_case.browse;

import entity.BrowsePage;
import org.json.JSONObject;

import java.io.IOException;

public interface BrowseDataAccess {

    BrowsePage getPage(String query) throws IOException;

    BrowsePage makeBrowsePage(JSONObject jsonObject);

}
