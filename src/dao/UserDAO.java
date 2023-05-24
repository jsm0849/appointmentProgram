/**
 * The UserDAO class implements the data access to read from the Users table in the database.
 *
 * @author Jacob Smith
 */

package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    /**
     * The getUser method searches the database for a User based on their username, and returns a User object.
     *
     * @param userName the User's username.
     * @return the User object if found.
     * @throws SQLException
     * @throws Exception
     */
    public static User getUser(String userName) throws SQLException, Exception{
        String sqlStatement = "select * FROM users WHERE User_Name  = '" + userName+ "'";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result = Query.getResult();
        while(result.next()){
            int userID = result.getInt("User_ID");
            String username = result.getString("User_Name");
            String password = result.getString("Password");
            userResult = new User(userID, username, password);
            return userResult;
        }
        return null;
    }

    /**
     * The getAllUsers method returns an array of User objects matching the Users in the database.
     *
     * @return an array of the Users in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sqlStatement = "select * from users";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int userid = result.getInt("User_ID");
            String username = result.getString("User_Name");
            String password = result.getString("Password");
            User userResult = new User(userid, username, password);
            allUsers.add(userResult);
        }
        return allUsers;
    }
}
