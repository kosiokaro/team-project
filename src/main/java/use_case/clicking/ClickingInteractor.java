package use_case.clicking;
import entity.Media;
import entity.MediaDetailsResponse;

public class ClickingInteractor implements ClickingInputBoundary{
    private final ClickingOutputBoundary presenter;
    private ClickingDataAccessInterface repository;
    public ClickingInteractor(ClickingOutputBoundary presenter,
                              ClickingDataAccessInterface repository) {
        this.presenter = presenter;
        this.repository = repository;
    }
    public void execute(ClickingInputData inputData) {
        MediaDetailsResponse media = repository.fetchDetailsById(inputData.getMediaId());
        if (media == null) {
            presenter.prepareFailureView("Media not found. Please try again.");
            return;
        }

        ClickingOutputData outputData = new ClickingOutputData(
                media.getTitle(),
                media.getOverview(),
                media.getLanguage(),
                media.getRating(),
                media.getReleaseYear(),
                media.getPosterUrl(),
                media.getGenres()
        );
        presenter.prepareSuccessView(outputData);

    }
}
