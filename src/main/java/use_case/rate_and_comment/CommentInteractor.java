package use_case.rate_and_comment;

import entity.Comment;

/**
 * The rate and comment Interactor.
 */
public class CommentInteractor implements CommentInputBoundary{
    private final CommentUserDataAccessInterface userDataAccessObject;
    private final CommentOutputBoundary commentPresenter;


    public CommentInteractor(CommentUserDataAccessInterface userDataAccessObject, CommentOutputBoundary commentPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.commentPresenter = commentPresenter;
    }

    @Override
    public void execute(CommentInputData commentInputData) {
        final String username = userDataAccessObject.getCurrentUser();
        final String medianame = commentInputData.getMedianame();
        final String comment = commentInputData.getComment();
        final int rate = commentInputData.getRate();

        //完成存储数据部分
        Comment newcomment = new Comment(username, rate, comment, medianame);
        userDataAccessObject.addComment(username, newcomment);

        final CommentOutputData commentOutputData = new CommentOutputData(medianame);
        commentPresenter.prepareSuccessView(commentOutputData);
    }
}
