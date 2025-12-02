package use_case.browse;


import entity.BrowsePage;
import entity.BrowseRequestBuilder;

public class BrowseInteractor implements BrowseInputBoundary {
    private final BrowseDataAccess browseDataAccessObject;
    private final BrowseOutputBoundary browsePresenter;


    public BrowseInteractor(BrowseDataAccess browseDataAccessObject, BrowseOutputBoundary browsePresenter) {
        this.browseDataAccessObject = browseDataAccessObject;
        this.browsePresenter = browsePresenter;
    }

    @Override
    public void execute(BrowseInputData browseInputData) {
        BrowseRequestBuilder builder = new BrowseRequestBuilder();
        if(browseInputData.title.isEmpty()){
            if(!browseInputData.year.isEmpty()){builder.setReleaseYear(browseInputData.year);}
            if (!browseInputData.pageNumber.isEmpty()){builder.setPageNumber(browseInputData.pageNumber);}

            if(browseInputData.sortAscending){builder.sortByRatingAsc();
                System.out.println("Sort by Ascending");}
            if(browseInputData.sortDescending){builder.sortByRatingDesc();}
            builder.addMinimumRating();

        } else {
            builder = new BrowseRequestBuilder(browseInputData.title);
            if(!browseInputData.year.isEmpty()){builder.setReleaseYear(browseInputData.year);}
            if (!browseInputData.pageNumber.isEmpty()){builder.setPageNumber(browseInputData.pageNumber);}

            if(browseInputData.sortAscending){builder.sortByRatingAsc();}
            if(browseInputData.sortDescending){builder.sortByRatingDesc();}
        }


        BrowsePage outputPage = this.browseDataAccessObject.getPage(builder);
        if (outputPage.getMovies().length == 0) {
            System.out.println("Error: " + outputPage.getError());
            BrowseOutputData browseOutputData = new BrowseOutputData(outputPage);
            browseOutputData.setErrorMessage(outputPage.getError());
            this.browsePresenter.prepareError(browseOutputData);
        }
        else{
            BrowseOutputData browseOutputData = new BrowseOutputData(outputPage);
            this.browsePresenter.populateView(browseOutputData);
        }




    }

    @Override
    public void selectMovie(BrowseInputData browseInputData) {
        BrowseOutputData browseOutputData =  new BrowseOutputData(browseInputData.referenceNumber);
        this.browsePresenter.prepareSelectMovieView(browseOutputData);
    }


}
