package use_case.clicking;

import entity.MediaDetailsResponse;

/**
 * Data access interface for retrieving media details.
 */
public interface ClickingDataAccessInterface {
    /**
     * Fetches media details by the given ID.
     *
     * @param id the unique identifier of the media item
     * @return MediaDetailsResponse containing the media details
     */
    MediaDetailsResponse fetchDetailsById(int id);
}
