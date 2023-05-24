/**
 * The UserHolder class is a helper class which holds a specific instance of itself, to be referenced by multiple pages.
 *
 * @author Jacob Smith
 */

package controller;

import model.User;

public final class UserHolder {
    private User loggedInUser;
    private final static UserHolder INSTANCE = new UserHolder();

    /**
     * The constructor is private so that other classes cannot create a new instance of UserHolder.
     */
    private UserHolder() {}
    /**
     * The getInstance method returns the instance of UserHolder.
     *
     * @return the instance of UserHolder.
     */
    public static UserHolder getInstance() {
        return INSTANCE;
    }
    /**
     * The setUser method sets the particular User the instance of the class will hold.
     *
     * @param user the User the instance of the class will hold.
     */
    public void setUser(User user) {
        this.loggedInUser = user;
    }
    /**
     * The getUser method returns the specific User that was stored in the instance of the class.
     *
     * @return the User who was stored in the instance of the class.
     */
    public User getUser() {
        return this.loggedInUser;
    }
}
