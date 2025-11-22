package entity;

public class BrowseRequestBuilder {

    private final StringBuilder request;


    public BrowseRequestBuilder(){
        this.request = new StringBuilder();
        this.request.append("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US");
        this.setPageNumber("1");
    }

    public BrowseRequestBuilder(String query){
        this.request = new StringBuilder();
        this.request.append("https://api.themoviedb.org/3/search/movie?query=").append(query).append("&include_adult=false&language=en-US");
        this.setPageNumber("1");
    }

    public String getRequest() {
        return request.toString();
    }

    public void setPageNumber(String pageNumber) {
        this.request.append("&page=").append(pageNumber);
    }

    public void  setReleaseYear(String year){
        request.append("&primary_release_year=").append(year);
    }

    public void  sortByRatingAsc(){
        request.append("&sort_by=vote_average.asc");
    }

    public void  sortByRatingDesc(){
        request.append("&sort_by=vote_average.desc");
    }

}
