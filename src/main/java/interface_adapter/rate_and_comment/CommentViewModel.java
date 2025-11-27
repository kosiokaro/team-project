package interface_adapter.rate_and_comment;

import interface_adapter.ViewModel;

public class CommentViewModel extends ViewModel<CommentState>{
    public CommentViewModel() {
        super("comment");
        setState(new CommentState());
    }
}
