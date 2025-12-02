package interface_adapter.clicking;

import use_case.clicking.ClickingInputBoundary;
import use_case.clicking.ClickingInputData;

/**
 * Controller for handling clicking interactions.
 */
public class ClickingController {
    private final ClickingInputBoundary interactor;

    /**
     * Constructs a ClickingController with the specified interactor.
     *
     * @param interactor the input boundary for clicking use case
     */
    public ClickingController(ClickingInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Handles a click event for the specified media.
     *
     * @param mediaID the ID of the media that was clicked
     */
    public void onClick(int mediaID) {
        interactor.execute(new ClickingInputData(mediaID));
    }
}