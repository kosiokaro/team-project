package use_case.rate_and_comment;

/**
 * The Input Data for the rate and comment Use Case.
 */
public class CommentInputData {
    private final int rate;
    private final String comment;
    private final String medianame;

    public CommentInputData(int rate, String comment, String medianame) {
        this.rate = rate;
        this.comment = comment;
        this.medianame = medianame;
    }

    public int getRate() {return rate;}
    public String getComment() {return comment;}
    public String getMedianame() {return medianame;}
}
