/**
 * The User class defines a User who is able to access the scheduling software.
 *
 * @author Jacob Smith
 */

package model;

public class User {
    private int userID;
    private String userName;
    private String userPassword;

    /**
     * The constructor generates a new User.
     *
     * @param user_ID the User's ID number.
     * @param userName the User's username.
     * @param userPassword the User's password.
     */
    public User(int user_ID, String userName, String userPassword){
        this.userID = user_ID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    //Mutators
    /**
     * The setUserID method sets the User's ID number.
     *
     * @param id the User's ID number.
     */
    public void setUserID(int id){
        this.userID = id;
    }
    /**
     * The setUserName method sets the User's username.
     *
     * @param userName the User's username.
     */
    public void setUserName(String userName){
        this.userName = userName;
    }
    /**
     * The setUserPassword method sets the User's password.
     *
     * @param password the User's password.
     */
    public void setUserPassword(String password){
        this.userPassword = password;
    }

    //Accessors
    /**
     * The getUserID method returns the User's ID number.
     *
     * @return the ID number of the User
     */
    public int getUserID(){
        return userID;
    }
    /**
     * The getUserName method returns the User's username.
     *
     * @return the User's username.
     */
    public String getUserName(){
        return userName;
    }
    /**
     * The getUserPassword method returns the User's password.
     *
     * @return the User's password.
     */
    public String getUserPassword(){
        return userPassword;
    }
    @Override
    public String toString(){
        return Integer.toString(userID);
    }
}
