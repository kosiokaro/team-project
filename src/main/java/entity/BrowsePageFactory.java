package entity;


public class BrowsePageFactory {

    public static BrowsePage buildPage(){
       Movie[] movies = new Movie[0];
        return new BrowsePage(movies,1);

    }
}
