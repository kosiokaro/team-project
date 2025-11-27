package use_case.signup;

import entity.User;

/**
 * DAO interface for the Signup Use Case.
 */
public interface SignupUserDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Saves the user.
     * @param username the of the user
     * @param password the password of the user
     * @param accountID the accountID of the user
     */
    void createUser(String username, String password, int accountID);

    /**
     * Check if the accountID already exists
     * @param accountID the randomly generated accountID of the new user
     */
    boolean existsByAccountID(int accountID);
}
