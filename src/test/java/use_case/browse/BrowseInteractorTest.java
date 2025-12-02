package use_case.browse;


import data_access.BrowseDataAccess;
import interface_adapter.browse.BrowsePresenter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;


public class BrowseInteractorTest {

    @Test
    public void BrowseSucessTest() {
        BrowseInputData searchQuery = new BrowseInputData("","","1",false,true);
        BrowseInputData searchQuery1 = new BrowseInputData("","","",true,false);
        BrowseInputData searchQuery2 = new BrowseInputData("2024","","1",true,false);
        BrowseInputData searchQuery3 = new BrowseInputData("2024","","1",false,true);
        BrowseInputData searchQuery4 = new BrowseInputData("2025","","1",true,false);

        BrowseInputData searchQuery5 = new BrowseInputData("","","1",false,true);
        BrowseInputData searchQuery6 = new BrowseInputData("2014","Lego Movie","",true,false);
        BrowseInputData searchQuery7 = new BrowseInputData("","Lego Movie","1",true,false);
        BrowseInputData searchQuery8 = new BrowseInputData("","","1",true,false);




        BrowseDataAccess browseDataAccess = new BrowseDataAccess();
        BrowseOutputBoundary presenter = new BrowseOutputBoundary() {
            @Override
            public void populateView(BrowseOutputData browseOutputData) {
                assert browseOutputData.getMovies().size() > 0;
                assert browseOutputData.getMovies().size() <= 20;

            }

            @Override
            public void prepareError(BrowseOutputData browseOutputData) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareSelectMovieView(BrowseOutputData browseOutputData) {
                fail("Use case failure is unexpected.");
            }

        };

        BrowseInteractor browseInteractor = new BrowseInteractor(browseDataAccess,presenter);
        browseInteractor.execute(searchQuery);
        browseInteractor.execute(searchQuery1);
        browseInteractor.execute(searchQuery2);
        browseInteractor.execute(searchQuery3);
        browseInteractor.execute(searchQuery4);
        browseInteractor.execute(searchQuery5);
        browseInteractor.execute(searchQuery6);
        browseInteractor.execute(searchQuery7);
        browseInteractor.execute(searchQuery8);



    }

    @Test
    public void BrowseFailureTest() {
        BrowseInputData searchQuery = new BrowseInputData("2024","asdaasdasdadasdads","1",false,true);

        BrowseDataAccess browseDataAccess = new BrowseDataAccess();
        BrowseOutputBoundary presenter = new BrowseOutputBoundary() {
            @Override
            public void populateView(BrowseOutputData browseOutputData) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareError(BrowseOutputData browseOutputData) {
                assert browseOutputData != null;
            }

            @Override
            public void prepareSelectMovieView(BrowseOutputData browseOutputData) {
                fail("Use case failure is unexpected.");
            }

        };

        BrowseInteractor browseInteractor = new BrowseInteractor(browseDataAccess,presenter);
        browseInteractor.execute(searchQuery);

    }
    @Test
    public void BrowseSelectMovieTest(){
        BrowseInputData searchQuery = new BrowseInputData(10000);
        BrowseDataAccess browseDataAccess = new BrowseDataAccess();
        BrowseOutputBoundary presenter = new BrowseOutputBoundary() {
            @Override
            public void populateView(BrowseOutputData browseOutputData) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareError(BrowseOutputData browseOutputData) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareSelectMovieView(BrowseOutputData browseOutputData) {
                assert browseOutputData.getMovies().size()== 1;
                assert browseOutputData.getMovies().get(0).getMovieID() == 10000;
            }

        };

        BrowseInteractor browseInteractor = new BrowseInteractor(browseDataAccess,presenter);
        browseInteractor.selectMovie(searchQuery);
    }






}
