package use_case.browse;


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

        if(browseInputData.isQuery){
            if(browseInputData.title.isEmpty()){
                if(!browseInputData.year.isEmpty()){builder.setReleaseYear(browseInputData.year);}
                else if (!browseInputData.pageNumber.isEmpty()){builder.setPageNumber(browseInputData.pageNumber);}
                else if(browseInputData.sortAscending){builder.sortByRatingAsc();}
                else if(browseInputData.sortDescending){builder.sortByRatingDesc();}

            } else {
                builder = new BrowseRequestBuilder(browseInputData.title);
                if(!browseInputData.year.isEmpty()){builder.setReleaseYear(browseInputData.year);}
                else if (!browseInputData.pageNumber.isEmpty()){builder.setPageNumber(browseInputData.pageNumber);}
                else if(browseInputData.sortAscending){builder.sortByRatingAsc();}
                else if(browseInputData.sortDescending){builder.sortByRatingDesc();}
            }


        }

    }

    @Override
    public void updatePage() {

    }

    @Override
    public void selectMovie() {

    }

    @Override
    public void addToFavorite() {

    }

    @Override
    public void addToWatchlist() {

    }
}
