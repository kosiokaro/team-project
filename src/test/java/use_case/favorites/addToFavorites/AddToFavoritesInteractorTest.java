package use_case.favorites.addToFavorites;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AddToFavoritesInteractorTest {
    @Test
    void successTest() {
        AddToFavoritesInputData inputData =
                new AddToFavoritesInputData();
        inputData.username = "user123";
        inputData.refNumber = 900667;

        AddToFavoritesDataAccessInterface repository =
                mock(AddToFavoritesDataAccessInterface.class);

        AddToFavoritesOutputBoundary successPresenter = new AddToFavoritesOutputBoundary() {
            @Override
            public void presentSuccess(AddToFavoritesOutputData outputData) {
                assertEquals("user123", outputData.getUsername());
                assertEquals(900667, outputData.getRefNumber());
                assertEquals("Movie added successfully", outputData.getMessage());
            }

            @Override
            public void presentError(String errorMessage) {
                fail("Unexpected failure: " + errorMessage);
            }
        };

        AddToFavoritesInputBoundary interactor =
                new AddToFavoritesInteractor(repository, successPresenter);

        interactor.addMovieToFavorites(inputData);

        verify(repository, times(1))
                .addMovieToFavorites("user123", 900667);
    }

    @Test
    void failureRepositoryThrowsExceptionTest() {
        AddToFavoritesInputData inputData =
                new AddToFavoritesInputData();
        inputData.username = "user123";
        inputData.refNumber = 900667;

        AddToFavoritesDataAccessInterface repository =
                mock(AddToFavoritesDataAccessInterface.class);

        doThrow(new RuntimeException("DB error"))
                .when(repository).addMovieToFavorites("user123", 900667);

        AddToFavoritesOutputBoundary failurePresenter = new AddToFavoritesOutputBoundary() {
            @Override
            public void presentSuccess(AddToFavoritesOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentError(String errorMessage) {
                assertEquals("DB error", errorMessage);
            }
        };

        AddToFavoritesInputBoundary interactor =
                new AddToFavoritesInteractor(repository, failurePresenter);

        interactor.addMovieToFavorites(inputData);

        verify(repository, times(1))
                .addMovieToFavorites("user123", 900667);
    }
}
