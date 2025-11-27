package use_case.clicking;

public interface ClickingOutputBoundary {
    void prepareSuccessView(ClickingOutputData outputData);
    void prepareFailureView(String errorMessage);
}
