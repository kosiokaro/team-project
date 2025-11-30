package commentTest;

import use_case.rate_and_comment.*;

import entity.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class CommentInteractorTest {

    private CommentUserDataAccessInterface mockDAO;
    private CommentOutputBoundary mockPresenter;
    private CommentInteractor interactor;

    @BeforeEach
    void setUp() {
        mockDAO = mock(CommentUserDataAccessInterface.class);
        mockPresenter = mock(CommentOutputBoundary.class);

        interactor = new CommentInteractor(mockDAO, mockPresenter);
    }

    @Test
    void testExecute_AddsCommentAndCallsPresenter() {
        // Given
        CommentInputData inputData = new CommentInputData(
                5,
                "Great movie!",
                "Inception"
        );

        when(mockDAO.getCurrentUser()).thenReturn("alice");

        // When
        interactor.execute(inputData);

        // 验证 DAO 是否收到 addComment

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(mockDAO).addComment(eq("alice"), commentCaptor.capture());

        Comment saved = commentCaptor.getValue();
        assert saved.getUsername().equals("alice");
        assert saved.getMedianame().equals("Inception");
        assert saved.getComment().equals("Great movie!");
        assert saved.getRate() == 5;

        // 验证 presenter 是否被调用

        ArgumentCaptor<CommentOutputData> outputCaptor = ArgumentCaptor.forClass(CommentOutputData.class);
        verify(mockPresenter).prepareSuccessView(outputCaptor.capture());

        CommentOutputData output = outputCaptor.getValue();
        assert output.getMedianame().equals("Inception");
    }
}

