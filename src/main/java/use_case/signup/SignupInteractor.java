package use_case.signup;

import entity.User;
import java.util.Random;
import java.util.UUID;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    //private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists.");
        }
        else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else if ("".equals(signupInputData.getPassword())) {
            userPresenter.prepareFailView("New password cannot be empty");
        }
        else if ("".equals(signupInputData.getUsername())) {
            userPresenter.prepareFailView("Username cannot be empty");
        }
        else {
            String username = signupInputData.getUsername();
            String password = signupInputData.getPassword();
            //随机创建一个accountID
            int accountID = generateUniqueAccountID(userDataAccessObject);
            userDataAccessObject.createUser(username, password, accountID);

            final SignupOutputData signupOutputData = new SignupOutputData(username);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }

    public int generateUniqueAccountID(SignupUserDataAccessInterface signupUserDataAccessInterface) {
        Random random = new Random();
        int accountID;

        do {
            // 生成 100000 到 999999999 之间的随机数（6-9位）
            accountID = 100000 + random.nextInt(999900000);
        } while (signupUserDataAccessInterface.existsByAccountID(accountID));

        return accountID;
    }
}
