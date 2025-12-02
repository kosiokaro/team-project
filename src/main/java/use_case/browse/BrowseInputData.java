package use_case.browse;



public class BrowseInputData {

    public String year;
    public String title;
    public String pageNumber;
    public boolean sortAscending;
    public boolean sortDescending;
    public int referenceNumber;

    public BrowseInputData(String year, String title, String pageNumber, boolean sortAscending, boolean sortDescending) {

        this.year = year;
        this.title = title;
        this.pageNumber = pageNumber;
        this.sortAscending = sortAscending;
        this.sortDescending = sortDescending;
    }

    public BrowseInputData(int referenceNumber1) {
        this.referenceNumber = referenceNumber1;
    }





}
