package interface_adapter.rate_and_comment;

import use_case.rate_and_comment.CommentInputBoundary;
import use_case.rate_and_comment.CommentInputData;

/**
 * The controller for the rate and comment Use Case.
 */
public class CommentController {
    private final CommentInputBoundary commentUseCaseInteractor;

    public CommentController(CommentInputBoundary commentUseCaseInteractor) {
        this.commentUseCaseInteractor = commentUseCaseInteractor;
    }

    /**
     * Executes the rate and comment Use Case.
     * @param medianame the name of the movie that the user is rating
     * @param comment the comment of the user
     * @param rate the rate that the user give
     */
    public void execute(String medianame, String comment, int rate) {
        final CommentInputData commentInputData = new CommentInputData(rate, comment, medianame);

        commentUseCaseInteractor.execute(commentInputData);
    }
}
