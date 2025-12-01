package LoginTest;

import data_access.FileUserDataAccessObject;


import org.junit.Test;

import use_case.login.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

public class LoginInteractorTest {

    @Test
    public void successTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginUserDataAccessInterface userRepository = new FileUserDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        userRepository.createUser("Paul", "password", 101088990);


        // This creates a successPresenter that tests whether the test case is as we expect.
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", user.getUsername());
                assertEquals("Paul", userRepository.getCurrentUser());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }


    @Test
    public void failurePasswordMismatchTest() {
        LoginInputData inputData = new LoginInputData("Paul", "wrong");
        LoginUserDataAccessInterface userRepository = new FileUserDataAccessObject();

        // For this failure test, we need to add Paul to the data access repository before we log in, and
        // the passwords should not match.
        userRepository.createUser("Paul", "password", 101088990);

        // This creates a presenter that tests whether the test case is as we expect.
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"Paul\".", error);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    public void failureUserDoesNotExistTest() {
        LoginInputData inputData = new LoginInputData("Linda", "password");
        LoginUserDataAccessInterface userRepository = new FileUserDataAccessObject();

        // Add Paul to the repo so that when we check later they already exist

        // This creates a presenter that tests whether the test case is as we expect.
        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Linda: Account does not exist.", error);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }
}