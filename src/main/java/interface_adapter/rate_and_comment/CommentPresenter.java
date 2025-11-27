package interface_adapter.rate_and_comment;

import interface_adapter.RandC_success_submit.RandCSuccessState;
import interface_adapter.RandC_success_submit.RandCSuccessViewModel;
import interface_adapter.ViewManagerModel;
import use_case.rate_and_comment.CommentOutputBoundary;
import use_case.rate_and_comment.CommentOutputData;

/**
 * The Presenter for the rate and comment Use Case.
 */
public class CommentPresenter implements CommentOutputBoundary {

    private final CommentViewModel commentViewModel;
    private final RandCSuccessViewModel randCSuccessViewModel;
    private final ViewManagerModel viewManagerModel;


    public CommentPresenter(CommentViewModel commentViewModel, RandCSuccessViewModel randCSuccessViewModel, ViewManagerModel viewManagerModel) {
        this.commentViewModel = commentViewModel;
        this.randCSuccessViewModel = randCSuccessViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(CommentOutputData outputData) {
        // On success, update the RandCViewModel's state
        final RandCSuccessState randCSuccessState = randCSuccessViewModel.getState();
        randCSuccessState.setMedianame(outputData.getMedianame());
        //System.out.println(randCSuccessViewModel.getState().getMedianame());
        this.randCSuccessViewModel.firePropertyChange();

        // and clear everything from the CommentViewModel's state
        commentViewModel.setState(new CommentState());
        commentViewModel.firePropertyChange();

        //switch to the RandCSuccessSubmit view
        this.viewManagerModel.setState(randCSuccessViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }
}
