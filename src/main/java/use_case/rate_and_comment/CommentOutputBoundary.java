package use_case.rate_and_comment;

/**
 * The output boundary for the rate and comment Use Case.
 */
public interface CommentOutputBoundary {
    /**
     * Prepares the success view for the rate and comment Use Case.
     * @param outputData the output data.
     */
    void prepareSuccessView(CommentOutputData outputData);
}
