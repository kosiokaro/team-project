package use_case.rate_and_comment;

/**
 * Input Boundary for actions which are related to rate and comment.
 */
public interface CommentInputBoundary {
    /**
     * Executes the rate and comment use case.
     * @param commentInputData the input data
     */
    void execute(CommentInputData commentInputData);
}
