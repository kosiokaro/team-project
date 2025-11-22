package interface_adapter.clicking;

import use_case.clicking.ClickingInputData;
import use_case.clicking.ClickingInputBoundary;

public class ClickingController {
    private final ClickingInputBoundary interactor;
    public ClickingController(ClickingInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void onClick(int mediaID){
        interactor.execute(new ClickingInputData(mediaID));
    }

}

