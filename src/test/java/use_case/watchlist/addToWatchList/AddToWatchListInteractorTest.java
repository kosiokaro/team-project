package use_case.watchlist.addToWatchList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddToWatchListInteractorTest {

    @Test
    void successTest() {
        AddToWatchListInputData inputData = new AddToWatchListInputData();
        inputData.username = "testUser";
        inputData.refNumber = 123;

        AddToWatchListDataAccessInterface dataAccessObject = mock(AddToWatchListDataAccessInterface.class);

        AddToWatchListOutputBoundary successPresenter = new AddToWatchListOutputBoundary() {
            @Override
            public void presentSuccess(AddToWatchListOutputData outputData) {
                assertEquals("testUser", outputData.getUsername());
                assertEquals(123, outputData.getRefNumber());
                assertEquals("Movie added successfully", outputData.getMessage());
            }

            @Override
            public void presentError(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        AddToWatchListInputBoundaryData interactor = new AddToWatchListInteractor(
                dataAccessObject, successPresenter);
        interactor.addMovieToWatchlist(inputData);

        verify(dataAccessObject, times(1)).addMovieToWatchlist("testUser", 123);
    }

    @Test
    void failureDataAccessExceptionTest() {
        AddToWatchListInputData inputData = new AddToWatchListInputData();
        inputData.username = "testUser";
        inputData.refNumber = 456;

        AddToWatchListDataAccessInterface dataAccessObject = mock(AddToWatchListDataAccessInterface.class);

        doThrow(new RuntimeException("Database error"))
                .when(dataAccessObject).addMovieToWatchlist("testUser", 456);

        AddToWatchListOutputBoundary failurePresenter = new AddToWatchListOutputBoundary() {
            @Override
            public void presentSuccess(AddToWatchListOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentError(String error) {
                assertEquals("Database error", error);
            }
        };

        AddToWatchListInputBoundaryData interactor = new AddToWatchListInteractor(
                dataAccessObject, failurePresenter);
        interactor.addMovieToWatchlist(inputData);

        verify(dataAccessObject, times(1)).addMovieToWatchlist("testUser", 456);
    }

    @Test
    void successWithDifferentMovieIdTest() {
        AddToWatchListInputData inputData = new AddToWatchListInputData();
        inputData.username = "alice";
        inputData.refNumber = 999;

        AddToWatchListDataAccessInterface dataAccessObject = mock(AddToWatchListDataAccessInterface.class);

        AddToWatchListOutputBoundary successPresenter = new AddToWatchListOutputBoundary() {
            @Override
            public void presentSuccess(AddToWatchListOutputData outputData) {
                assertEquals("alice", outputData.getUsername());
                assertEquals(999, outputData.getRefNumber());
                assertEquals("Movie added successfully", outputData.getMessage());
            }

            @Override
            public void presentError(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        AddToWatchListInputBoundaryData interactor = new AddToWatchListInteractor(
                dataAccessObject, successPresenter);
        interactor.addMovieToWatchlist(inputData);

        verify(dataAccessObject, times(1)).addMovieToWatchlist("alice", 999);
    }

    @Test
    void failureNegativeMovieIdTest() {
        AddToWatchListInputData inputData = new AddToWatchListInputData();
        inputData.username = "testUser";
        inputData.refNumber = -1;

        AddToWatchListDataAccessInterface dataAccessObject = mock(AddToWatchListDataAccessInterface.class);

        AddToWatchListOutputBoundary successPresenter = new AddToWatchListOutputBoundary() {
            @Override
            public void presentSuccess(AddToWatchListOutputData outputData) {
                assertEquals("testUser", outputData.getUsername());
                assertEquals(-1, outputData.getRefNumber());
            }

            @Override
            public void presentError(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        AddToWatchListInputBoundaryData interactor = new AddToWatchListInteractor(
                dataAccessObject, successPresenter);
        interactor.addMovieToWatchlist(inputData);

        verify(dataAccessObject, times(1)).addMovieToWatchlist("testUser", -1);
    }
}