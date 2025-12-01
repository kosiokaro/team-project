package use_case.favorites.deleteFromFavorites;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeleteFromFavoritesInteractorTest {

    @Test
    void successTest() {
        DeleteFromFavoritesInputData inputData = new DeleteFromFavoritesInputData();
        inputData.username = "userABC";
        inputData.refNumber = 101;

        DeleteFromFavoritesDataAccessInterface repository =
                mock(DeleteFromFavoritesDataAccessInterface.class);

        DeleteFromFavoritesOutputBoundary successPresenter = new DeleteFromFavoritesOutputBoundary() {
            @Override
            public void presentSuccess(DeleteFromFavoritesOutputData outputData) {
                assertEquals("userABC", outputData.getUsername());
                assertEquals(101, outputData.getRefNumber());
                assertEquals("Movie removed successfully", outputData.getMessage());
            }

            @Override
            public void presentError(String errorMessage) {
                fail("Unexpected error: " + errorMessage);
            }
        };

        DeleteFromFavoritesInputBoundary interactor =
                new DeleteFromFavoritesInteractor(repository, successPresenter);

        interactor.deleteFromFavorites(inputData);

        verify(repository, times(1))
                .deleteFromFavorites("userABC", 101);
    }

    @Test
    void failureRepositoryThrowsExceptionTest() {
        DeleteFromFavoritesInputData inputData = new DeleteFromFavoritesInputData();
        inputData.username = "userABC";
        inputData.refNumber = 101;

        DeleteFromFavoritesDataAccessInterface repository =
                mock(DeleteFromFavoritesDataAccessInterface.class);

        doThrow(new RuntimeException("DB failure"))
                .when(repository).deleteFromFavorites("userABC", 101);

        DeleteFromFavoritesOutputBoundary failurePresenter = new DeleteFromFavoritesOutputBoundary() {
            @Override
            public void presentSuccess(DeleteFromFavoritesOutputData outputData) {
                fail("Unexpected success");
            }

            @Override
            public void presentError(String errorMessage) {
                assertEquals("DB failure", errorMessage);
            }
        };

        DeleteFromFavoritesInputBoundary interactor =
                new DeleteFromFavoritesInteractor(repository, failurePresenter);

        interactor.deleteFromFavorites(inputData);

        verify(repository, times(1))
                .deleteFromFavorites("userABC", 101);
    }
}
