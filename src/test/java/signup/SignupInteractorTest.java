package signup;

import org.junit.jupiter.api.Test;
import use_case.signup.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignupInteractorTest {

    @Test
    void successTest() {
        SignupInputData inputData = new SignupInputData("Maria", "password", "password");

        // 使用 Mock 对象替代真实的 DAO
        SignupUserDataAccessInterface userRepository = mock(SignupUserDataAccessInterface.class);

        // 设置 Mock 行为：Maria 不存在
        when(userRepository.existsByName("Maria")).thenReturn(false);

        // 创建测试用的 Presenter
        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // 检查输出数据是否正确
                assertEquals("Maria", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter);
        interactor.execute(inputData);

        // 验证 createUser 方法被调用了一次
        verify(userRepository, times(1)).createUser(
                eq("Maria"),
                eq("password"),
                anyInt()
        );
    }

    @Test
    void failurePasswordMismatchTest() {
        SignupInputData inputData = new SignupInputData("Calli", "password", "wrong");

        // 使用 Mock 对象
        SignupUserDataAccessInterface userRepository = mock(SignupUserDataAccessInterface.class);

        // 创建测试用的 Presenter
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Passwords don't match.", error);
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);

        // 验证没有调用 createUser（因为密码不匹配）
        verify(userRepository, never()).createUser(anyString(), anyString(), anyInt());
    }

    @Test
    void failureUserExistsTest() {
        SignupInputData inputData = new SignupInputData("Paul", "password", "password");

        // 使用 Mock 对象
        SignupUserDataAccessInterface userRepository = mock(SignupUserDataAccessInterface.class);

        // 设置 Mock 行为：Paul 已存在
        when(userRepository.existsByName("Paul")).thenReturn(true);

        // 创建测试用的 Presenter
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("User already exists.", error);
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);

        // 验证没有调用 createUser（因为用户已存在）
        verify(userRepository, never()).createUser(anyString(), anyString(), anyInt());
    }
}