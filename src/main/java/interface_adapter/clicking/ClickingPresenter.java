package interface_adapter.clicking;

import interface_adapter.ViewManagerModel;
import interface_adapter.rate_and_comment.CommentViewModel;
import use_case.clicking.ClickingOutputBoundary;
import use_case.clicking.ClickingOutputData;

public class ClickingPresenter implements ClickingOutputBoundary {

    private final ClickingViewModel clickingViewModel;
    private final ViewManagerModel viewManagerModel;



    public ClickingPresenter(ClickingViewModel vm, ViewManagerModel vmm) {
        this.clickingViewModel = vm;
        this.viewManagerModel = vmm;
    }

    @Override
    public void prepareSuccessView(ClickingOutputData outputData) {
        ClickingState state = clickingViewModel.getState();
        state.setErrorMessage("");
        state.setTitle(outputData.getTitle());
        state.setYear(outputData.getReleaseYear());
        state.setLanguage(outputData.getLanguage());
        state.setRating(outputData.getRating());
        state.setGenres(outputData.getGenres());
        state.setOverview(outputData.getOverview());
        state.setPosterUrl(outputData.getPosterUrl());

        viewManagerModel.setState(clickingViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        ClickingState state = clickingViewModel.getState();
        state.setErrorMessage(errorMessage);
        clickingViewModel.firePropertyChange();

        viewManagerModel.setState(clickingViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}


