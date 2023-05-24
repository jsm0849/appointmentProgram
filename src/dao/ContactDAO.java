/**
 * The ContactDAO class implements data access to read from the Contacts table in the database.
 *
 * @author Jacob Smith
 */

package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {
    /**
     * The getContact method returns a Contact if it was found in the database.
     *
     * @param contactID the ID number of the Contact.
     * @return the Contact if it was found in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static Contact getContact(int contactID) throws SQLException, Exception{
        String sqlStatement = "select * FROM contacts WHERE Contact_ID  = " + contactID;
        Query.makeQuery(sqlStatement);
        Contact contactResult;
        ResultSet result = Query.getResult();
        while(result.next()){
            String name = result.getString("Contact_Name");
            String email = result.getString("Email");
            contactResult = new Contact(contactID, name, email);
            return contactResult;
        }
        return null;
    }

    /**
     * The getAllContacts method returns an ObservableList of Contacts from the database.
     *
     * @return an ObservableList of the Contacts in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException, Exception{
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sqlStatement = "select * from contacts";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int contactID = result.getInt("Contact_ID");
            String name = result.getString("Contact_Name");
            String email = result.getString("Email");
            Contact contactResult = new Contact(contactID, name, email);
            allContacts.add(contactResult);
        }
        return allContacts;
    }
}
