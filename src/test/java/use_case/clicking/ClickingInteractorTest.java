package use_case.clicking;

import entity.MediaDetailsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for ClickingInteractor to ensure 100% code coverage.
 * Tests both main flow (successful media fetch) and alternate flow (media not found).
 */
public class ClickingInteractorTest {
    private ClickingDataAccessInterface mockRepository;
    private ClickingOutputBoundary mockPresenter;
    private ClickingInteractor interactor;

    @BeforeEach
    public void setup() {
        mockRepository = mock(ClickingDataAccessInterface.class);
        mockPresenter = mock(ClickingOutputBoundary.class);
        interactor = new ClickingInteractor(mockPresenter, mockRepository);
    }

    /**
     * Test the main flow: successful media retrieval and display
     */
    @Test
    public void testExecuteSuccess_MainFlow() {
        // Arrange
        int mediaId = 550;
        List<String> genres = Arrays.asList("Drama", "Thriller");

        MediaDetailsResponse mockMediaResponse = new MediaDetailsResponse(
                "Fight Club",
                1999,
                "en",
                8.4,
                genres,
                "A ticking-time-bomb insomniac and a slippery soap salesman...",
                "https://image.tmdb.org/t/p/w500/poster.jpg"
        );

        when(mockRepository.fetchDetailsById(mediaId)).thenReturn(mockMediaResponse);

        ClickingInputData inputData = new ClickingInputData(mediaId);

        // Act
        interactor.execute(inputData);

        // Assert
        verify(mockRepository, times(1)).fetchDetailsById(mediaId);
        verify(mockPresenter, times(1)).prepareSuccessView(any(ClickingOutputData.class));
        verify(mockPresenter, never()).prepareFailureView(anyString());
    }

    /**
     * Test the alternate flow: media not found (API failure)
     */
    @Test
    public void testExecuteFailure_MediaNotFound() {
        // Arrange
        int mediaId = 999999;

        when(mockRepository.fetchDetailsById(mediaId)).thenReturn(null);

        ClickingInputData inputData = new ClickingInputData(mediaId);

        // Act
        interactor.execute(inputData);

        // Assert
        verify(mockRepository, times(1)).fetchDetailsById(mediaId);
        verify(mockPresenter, times(1)).prepareFailureView("Media not found. Please try again.");
        verify(mockPresenter, never()).prepareSuccessView(any(ClickingOutputData.class));
    }

    /**
     * Test that output data is correctly transformed from MediaDetailsResponse
     */
    @Test
    public void testExecuteSuccess_VerifyOutputDataTransformation() {
        // Arrange
        int mediaId = 278;
        List<String> genres = Arrays.asList("Drama", "Crime");

        MediaDetailsResponse mockMediaResponse = new MediaDetailsResponse(
                "The Shawshank Redemption",
                1994,
                "en",
                9.3,
                genres,
                "Two imprisoned men bond over a number of years...",
                "https://image.tmdb.org/t/p/w500/shawshank.jpg"
        );

        when(mockRepository.fetchDetailsById(mediaId)).thenReturn(mockMediaResponse);

        ClickingInputData inputData = new ClickingInputData(mediaId);

        interactor.execute(inputData);

        ArgumentCaptor<ClickingOutputData> captor = ArgumentCaptor.forClass(ClickingOutputData.class);
        verify(mockPresenter).prepareSuccessView(captor.capture());

        ClickingOutputData capturedOutput = captor.getValue();

        // Verify all fields are correctly transformed
        assertEquals("The Shawshank Redemption", capturedOutput.getTitle());
        assertEquals("Two imprisoned men bond over a number of years...", capturedOutput.getOverview());
        assertEquals("en", capturedOutput.getLanguage());
        assertEquals(9.3, capturedOutput.getRating(), 0.01);
        assertEquals(1994, capturedOutput.getReleaseYear());
        assertEquals("https://image.tmdb.org/t/p/w500/shawshank.jpg", capturedOutput.getPosterUrl());
        assertEquals(Arrays.asList("Drama", "Crime"), capturedOutput.getGenres());
    }

    /**
     * Test with empty genres list
     */
    @Test
    public void testExecuteSuccess_EmptyGenresList() {

        int mediaId = 100;
        List<String> emptyGenres = Arrays.asList();

        MediaDetailsResponse mockMediaResponse = new MediaDetailsResponse(
                "Test Movie",
                2024,
                "en",
                7.0,
                emptyGenres,
                "A test overview",
                "https://example.com/poster.jpg"
        );

        when(mockRepository.fetchDetailsById(mediaId)).thenReturn(mockMediaResponse);

        ClickingInputData inputData = new ClickingInputData(mediaId);

        //Act
        interactor.execute(inputData);

        // Assert
        ArgumentCaptor<ClickingOutputData> captor = ArgumentCaptor.forClass(ClickingOutputData.class);
        verify(mockPresenter).prepareSuccessView(captor.capture());

        ClickingOutputData capturedOutput = captor.getValue();
        assertTrue(capturedOutput.getGenres().isEmpty());
    }

    /**
     * Test with single genre
     */
    @Test
    public void testExecuteSuccess_SingleGenre() {
        // Arrange
        int mediaId = 200;
        List<String> singleGenre = Arrays.asList("Action");

        MediaDetailsResponse mockMediaResponse = new MediaDetailsResponse(
                "Action Movie",
                2024,
                "en",
                8.0,
                singleGenre,
                "An action-packed adventure",
                "https://example.com/action.jpg"
        );

        when(mockRepository.fetchDetailsById(mediaId)).thenReturn(mockMediaResponse);

        ClickingInputData inputData = new ClickingInputData(mediaId);

        interactor.execute(inputData);

        ArgumentCaptor<ClickingOutputData> captor = ArgumentCaptor.forClass(ClickingOutputData.class);
        verify(mockPresenter).prepareSuccessView(captor.capture());

        ClickingOutputData capturedOutput = captor.getValue();
        assertEquals(1, capturedOutput.getGenres().size());
        assertEquals("Action", capturedOutput.getGenres().get(0));
    }
}