package use_case.browse;


import org.junit.jupiter.api.Test;


public class BrowseInteractorTest {

    @Test
    public void BrowseSucessTest() {
        BrowseInputData searchQuery = new BrowseInputData("2024","","1",false,true);


    }

    @Test
    public void BrowseFailureTest() {
        BrowseInputData searchQuery = new BrowseInputData("2024","asdaasdasdadasdads","1",false,true);


    }



}
