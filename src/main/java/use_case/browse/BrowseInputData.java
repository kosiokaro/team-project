package use_case.browse;



public class BrowseInputData {

    public String year;
    public String title;
    public String pageNumber;
    public boolean sortAscending;
    public boolean sortDescending;
    public int referenceNumber;
    public boolean isQuery = false;
    public boolean isReferenceLookUp = false;


    public BrowseInputData(String year, String title, String pageNumber, boolean sortAscending, boolean sortDescending) {

        this.year = year;
        this.title = title;
        this.pageNumber = pageNumber;
        this.sortAscending = false;
        this.sortDescending = false;
        this.isQuery = true;
        this.isReferenceLookUp = false;
    }

    public BrowseInputData(int referenceNumber1) {
        this.referenceNumber = referenceNumber1;
        this.isQuery = false;
        this.isReferenceLookUp = true;
    }




}
