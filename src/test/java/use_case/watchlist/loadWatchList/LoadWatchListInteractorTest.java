package use_case.watchlist.loadWatchList;

import entity.Movie;
import org.junit.jupiter.api.Test;
import use_case.watchlist.addToWatchList.AddToWatchListDataAccessInterface;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoadWatchListInteractorTest {

    @Test
    void successTest() {
        LoadWatchListInputData inputData = new LoadWatchListInputData("testUser");

        // Mock the data access objects
        AddToWatchListDataAccessInterface userRepository = mock(AddToWatchListDataAccessInterface.class);
        LoadWatchListDataAccessInterface movieRepository = mock(LoadWatchListDataAccessInterface.class);

        // Set up mock behavior: return movie IDs
        ArrayList<Integer> movieIds = new ArrayList<>(Arrays.asList(1, 2, 3));
        when(userRepository.getWatchlist("testUser")).thenReturn(movieIds);

        // Set up mock behavior: return movie objects
        Movie movie1 = mock(Movie.class);
        Movie movie2 = mock(Movie.class);
        Movie movie3 = mock(Movie.class);
        when(movieRepository.getMovieById(1)).thenReturn(movie1);
        when(movieRepository.getMovieById(2)).thenReturn(movie2);
        when(movieRepository.getMovieById(3)).thenReturn(movie3);

        LoadWatchListOutputBoundaryData successPresenter = new LoadWatchListOutputBoundaryData() {
            @Override
            public void presentWatchlist(LoadWatchListOutputData outputData) {
                // Check that output data is correct
                assertEquals(3, outputData.getMovies().size());
                assertEquals(movie1, outputData.getMovies().get(0));
                assertEquals(movie2, outputData.getMovies().get(1));
                assertEquals(movie3, outputData.getMovies().get(2));
            }

            @Override
            public void presentError(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoadWatchListInputBoundaryData interactor = new LoadWatchListInteractor(
                userRepository, movieRepository, successPresenter);
        interactor.loadWatchlist(inputData);

        // Verify all movie IDs were requested
        verify(movieRepository, times(1)).getMovieById(1);
        verify(movieRepository, times(1)).getMovieById(2);
        verify(movieRepository, times(1)).getMovieById(3);
    }

    @Test
    void successEmptyWatchlistTest() {
        LoadWatchListInputData inputData = new LoadWatchListInputData("testUser");

        AddToWatchListDataAccessInterface userRepository = mock(AddToWatchListDataAccessInterface.class);
        LoadWatchListDataAccessInterface movieRepository = mock(LoadWatchListDataAccessInterface.class);

        when(userRepository.getWatchlist("testUser")).thenReturn(new ArrayList<>());

        LoadWatchListOutputBoundaryData successPresenter = new LoadWatchListOutputBoundaryData() {
            @Override
            public void presentWatchlist(LoadWatchListOutputData outputData) {
                assertTrue(outputData.getMovies().isEmpty());
            }

            @Override
            public void presentError(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoadWatchListInputBoundaryData interactor = new LoadWatchListInteractor(
                userRepository, movieRepository, successPresenter);
        interactor.loadWatchlist(inputData);

        verify(movieRepository, never()).getMovieById(anyInt());
    }

    @Test
    void successNullWatchlistTest() {
        LoadWatchListInputData inputData = new LoadWatchListInputData("testUser");

        AddToWatchListDataAccessInterface userRepository = mock(AddToWatchListDataAccessInterface.class);
        LoadWatchListDataAccessInterface movieRepository = mock(LoadWatchListDataAccessInterface.class);

        when(userRepository.getWatchlist("testUser")).thenReturn(null);

        LoadWatchListOutputBoundaryData successPresenter = new LoadWatchListOutputBoundaryData() {
            @Override
            public void presentWatchlist(LoadWatchListOutputData outputData) {
                assertTrue(outputData.getMovies().isEmpty());
            }

            @Override
            public void presentError(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoadWatchListInputBoundaryData interactor = new LoadWatchListInteractor(
                userRepository, movieRepository, successPresenter);
        interactor.loadWatchlist(inputData);

        verify(movieRepository, never()).getMovieById(anyInt());
    }

    @Test
    void successWithSomeNullMoviesTest() {
        LoadWatchListInputData inputData = new LoadWatchListInputData("testUser");

        AddToWatchListDataAccessInterface userRepository = mock(AddToWatchListDataAccessInterface.class);
        LoadWatchListDataAccessInterface movieRepository = mock(LoadWatchListDataAccessInterface.class);
        ArrayList<Integer> movieIds = new ArrayList<>(Arrays.asList(1, 2, 3));
        when(userRepository.getWatchlist("testUser")).thenReturn(movieIds);

        Movie movie1 = mock(Movie.class);
        Movie movie3 = mock(Movie.class);
        when(movieRepository.getMovieById(1)).thenReturn(movie1);
        when(movieRepository.getMovieById(2)).thenReturn(null);
        when(movieRepository.getMovieById(3)).thenReturn(movie3);

        LoadWatchListOutputBoundaryData successPresenter = new LoadWatchListOutputBoundaryData() {
            @Override
            public void presentWatchlist(LoadWatchListOutputData outputData) {
                assertEquals(2, outputData.getMovies().size());
                assertEquals(movie1, outputData.getMovies().get(0));
                assertEquals(movie3, outputData.getMovies().get(1));
            }

            @Override
            public void presentError(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoadWatchListInputBoundaryData interactor = new LoadWatchListInteractor(
                userRepository, movieRepository, successPresenter);
        interactor.loadWatchlist(inputData);

        verify(movieRepository, times(1)).getMovieById(1);
        verify(movieRepository, times(1)).getMovieById(2);
        verify(movieRepository, times(1)).getMovieById(3);
    }

    @Test
    void failureUserDataAccessExceptionTest() {
        LoadWatchListInputData inputData = new LoadWatchListInputData("testUser");

        AddToWatchListDataAccessInterface userRepository = mock(AddToWatchListDataAccessInterface.class);
        LoadWatchListDataAccessInterface movieRepository = mock(LoadWatchListDataAccessInterface.class);

        when(userRepository.getWatchlist("testUser"))
                .thenThrow(new RuntimeException("Database connection failed"));

        LoadWatchListOutputBoundaryData failurePresenter = new LoadWatchListOutputBoundaryData() {
            @Override
            public void presentWatchlist(LoadWatchListOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentError(String error) {
                assertEquals("Database connection failed", error);
            }
        };

        LoadWatchListInputBoundaryData interactor = new LoadWatchListInteractor(
                userRepository, movieRepository, failurePresenter);
        interactor.loadWatchlist(inputData);

        verify(movieRepository, never()).getMovieById(anyInt());
    }

    @Test
    void failureMovieDataAccessExceptionTest() {
        LoadWatchListInputData inputData = new LoadWatchListInputData("testUser");

        AddToWatchListDataAccessInterface userRepository = mock(AddToWatchListDataAccessInterface.class);
        LoadWatchListDataAccessInterface movieRepository = mock(LoadWatchListDataAccessInterface.class);

        ArrayList<Integer> movieIds = new ArrayList<>(Arrays.asList(1));
        when(userRepository.getWatchlist("testUser")).thenReturn(movieIds);

        when(movieRepository.getMovieById(1))
                .thenThrow(new RuntimeException("Movie API failed"));

        LoadWatchListOutputBoundaryData failurePresenter = new LoadWatchListOutputBoundaryData() {
            @Override
            public void presentWatchlist(LoadWatchListOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentError(String error) {
                assertEquals("Movie API failed", error);
            }
        };

        LoadWatchListInputBoundaryData interactor = new LoadWatchListInteractor(
                userRepository, movieRepository, failurePresenter);
        interactor.loadWatchlist(inputData);
    }

    @Test
    void successMaintainsOrderTest() {
        LoadWatchListInputData inputData = new LoadWatchListInputData("testUser");

        AddToWatchListDataAccessInterface userRepository = mock(AddToWatchListDataAccessInterface.class);
        LoadWatchListDataAccessInterface movieRepository = mock(LoadWatchListDataAccessInterface.class);

        ArrayList<Integer> movieIds = new ArrayList<>(Arrays.asList(5, 2, 8, 1));
        when(userRepository.getWatchlist("testUser")).thenReturn(movieIds);

        Movie movie5 = mock(Movie.class);
        Movie movie2 = mock(Movie.class);
        Movie movie8 = mock(Movie.class);
        Movie movie1 = mock(Movie.class);
        when(movieRepository.getMovieById(5)).thenReturn(movie5);
        when(movieRepository.getMovieById(2)).thenReturn(movie2);
        when(movieRepository.getMovieById(8)).thenReturn(movie8);
        when(movieRepository.getMovieById(1)).thenReturn(movie1);

        LoadWatchListOutputBoundaryData successPresenter = new LoadWatchListOutputBoundaryData() {
            @Override
            public void presentWatchlist(LoadWatchListOutputData outputData) {
                assertEquals(4, outputData.getMovies().size());
                assertEquals(movie5, outputData.getMovies().get(0));
                assertEquals(movie2, outputData.getMovies().get(1));
                assertEquals(movie8, outputData.getMovies().get(2));
                assertEquals(movie1, outputData.getMovies().get(3));
            }

            @Override
            public void presentError(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoadWatchListInputBoundaryData interactor = new LoadWatchListInteractor(
                userRepository, movieRepository, successPresenter);
        interactor.loadWatchlist(inputData);
    }
}