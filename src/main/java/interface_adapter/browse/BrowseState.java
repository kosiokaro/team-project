package interface_adapter.browse;

import java.util.List;

public class BrowseState {
    private int currentPageNumber;
    private List<String> titles;
    private List<String> images;
    private List<Integer> referenceNumbers;
    private List<Integer> runTimes;
    private List<int[]> genreIDS;


    public BrowseState() {
        this.currentPageNumber = 1;
    }

    public int getCurrentPageNumber() {return currentPageNumber;}
    public void incrementPage(int newPageNumber) {this.currentPageNumber = newPageNumber;}

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getImages() {
        return images;
    }
    public List<Integer> getReferenceNumbers() {
        return referenceNumbers;
    }
    public List<Integer> getRunTimes() {
        return runTimes;
    }
    public List<int[]> getGenreIDS() {
        return genreIDS;
    }


    public void addtitles(List<String> title) {
        this.titles.addAll(title);
    }

    public void addimages(List<String> images) {
        this.images.addAll(images);
    }

    public void addreferenceNumbers(List<Integer> referenceNumbers) {
        this.referenceNumbers.addAll(referenceNumbers);
    }

    public void addrunTimes(List<Integer> runTimes) {
        this.runTimes.addAll(runTimes);
    }

    public void addGenreIDs(List<int[]> genreIDS) {
        this.genreIDS.addAll(genreIDS);
    }


}
