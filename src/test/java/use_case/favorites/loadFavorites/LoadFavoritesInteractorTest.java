package use_case.favorites.loadFavorites;

import entity.Movie;
import org.junit.jupiter.api.Test;
import use_case.favorites.addToFavorites.AddToFavoritesDataAccessInterface;

import java.util.ArrayList;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoadFavoritesInteractorTest {
    private Movie makeMovie(int id, String title) {
        String[] genres = new String[] {"Drama"};
        String overview = title + " overview";
        double rating = 7.5;
        String releaseDate = "2020-01-01";
        int runtime = 100;
        String posterURL = title.toLowerCase().replace(" ", "_") + ".jpg";
        String language = "en";
        return new Movie(title, id, genres, overview, rating, releaseDate, runtime, posterURL, language);
    }

    @Test
    void successTest() {
        LoadFavoritesInputData inputData = new LoadFavoritesInputData("userXYZ");

        AddToFavoritesDataAccessInterface userRepo = mock(AddToFavoritesDataAccessInterface.class);
        LoadFavoritesDataAccessInterface movieRepo = mock(LoadFavoritesDataAccessInterface.class);

        ArrayList<Integer> favIds = new ArrayList<>();
        favIds.add(5);
        favIds.add(8);

        when(userRepo.getFavorites("userXYZ")).thenReturn(favIds);
        when(movieRepo.getMovieById(5)).thenReturn(makeMovie(5, "Movie A"));
        when(movieRepo.getMovieById(8)).thenReturn(makeMovie(8, "Movie B"));

        LoadFavoritesOutputBoundary presenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(LoadFavoritesOutputData outputData) {
                assertEquals(2, outputData.getMovies().size());
                assertEquals("Movie A", outputData.getMovies().get(0).getTitle());
                assertEquals("Movie B", outputData.getMovies().get(1).getTitle());
            }

            @Override
            public void presentError(String errorMessage) {
                fail("Unexpected error: " + errorMessage);
            }
        };

        LoadFavoritesInputBoundary interactor =
                new LoadFavoritesInteractor(userRepo, movieRepo, presenter);

        interactor.loadFavorites(inputData);

        verify(userRepo, times(1)).getFavorites("userXYZ");
        verify(movieRepo, times(1)).getMovieById(5);
        verify(movieRepo, times(1)).getMovieById(8);
    }

    @Test
    void ignoreMissingMoviesTest() {
        LoadFavoritesInputData inputData = new LoadFavoritesInputData("userXYZ");

        AddToFavoritesDataAccessInterface userRepo = mock(AddToFavoritesDataAccessInterface.class);
        LoadFavoritesDataAccessInterface movieRepo = mock(LoadFavoritesDataAccessInterface.class);

        ArrayList<Integer> favIds = new ArrayList<>();
        favIds.add(10);

        when(userRepo.getFavorites("userXYZ")).thenReturn(favIds);
        when(movieRepo.getMovieById(10)).thenReturn(null); // simulate movie not found

        LoadFavoritesOutputBoundary presenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(LoadFavoritesOutputData outputData) {
                assertTrue(outputData.getMovies().isEmpty(),
                        "No movies should be returned if ID lookup fails");
            }

            @Override
            public void presentError(String errorMessage) {
                fail("Unexpected error: " + errorMessage);
            }
        };

        LoadFavoritesInputBoundary interactor =
                new LoadFavoritesInteractor(userRepo, movieRepo, presenter);

        interactor.loadFavorites(inputData);

        verify(userRepo, times(1)).getFavorites("userXYZ");
        verify(movieRepo, times(1)).getMovieById(10);
    }

    @Test
    void failureRepositoryThrowsExceptionTest() {
        LoadFavoritesInputData inputData = new LoadFavoritesInputData("userXYZ");

        AddToFavoritesDataAccessInterface userRepo = mock(AddToFavoritesDataAccessInterface.class);
        LoadFavoritesDataAccessInterface movieRepo = mock(LoadFavoritesDataAccessInterface.class);

        when(userRepo.getFavorites("userXYZ"))
                .thenThrow(new RuntimeException("DB failure"));

        LoadFavoritesOutputBoundary presenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(LoadFavoritesOutputData outputData) {
                fail("Unexpected success");
            }

            @Override
            public void presentError(String errorMessage) {
                assertEquals("DB failure", errorMessage);
            }
        };

        LoadFavoritesInputBoundary interactor =
                new LoadFavoritesInteractor(userRepo, movieRepo, presenter);

        interactor.loadFavorites(inputData);

        verify(userRepo, times(1)).getFavorites("userXYZ");
        verify(movieRepo, never()).getMovieById(anyInt());
    }
}
