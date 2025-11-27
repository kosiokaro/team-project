package entity;

public class Comment {
    private String username;
    private int rate;
    private String comment;
    private String medianame;

    public Comment(String username, int rate, String comment, String medianame) {
        this.username = username;
        this.medianame = medianame;
        this.rate = rate;
        this.comment = comment;
    }

    public String getUsername() {return username;}

    public int getRate() {return rate;}

    public String getComment() {return comment;}

    public String getMedianame() {return medianame;}
}
