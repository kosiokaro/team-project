package use_case.rate_and_comment;

/**
 * Output Data for the rate and comment Use Case.
 */
public class CommentOutputData {
    private final String medianame;


    public CommentOutputData(String medianame) {
        this.medianame = medianame;

    }

    public String getMedianame() {return medianame;}

}
