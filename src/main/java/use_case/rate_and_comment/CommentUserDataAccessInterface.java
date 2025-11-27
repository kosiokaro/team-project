package use_case.rate_and_comment;

import entity.Comment;

/**
 * DAO interface for the rate and comment Use Case.
 */
public interface CommentUserDataAccessInterface {
    /**
     * add the comment into the users file
     * @param username the new current username
     */
    public void addComment(String username, Comment comment);

    /**
     * get the username of the current user
     * @return username
     */
      String getCurrentUser();
}
