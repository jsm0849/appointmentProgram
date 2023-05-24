/**
 * The CustomerDAO class implements data access to create, read, update, and delete Customers from the Customers
 * table in the database.
 *
 * @author Jacob Smith
 */

package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.time.LocalDateTime;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
    //CREATE
    /**
     * The addCustomer method inserts a new Customer into the database.
     *
     * @param name the Customer's name.
     * @param address the Customer's address.
     * @param postCode the Customer's postal code.
     * @param phoneNumber the Customer's phone number.
     * @param createdBy the username of the User who created the Customer.
     * @param divisionID the ID number of the FirstLevelDivision where the Customer is based.
     */
    public static void addCustomer(String name, String address, String postCode, String phoneNumber, String createdBy,
                                   int divisionID){
        String sqlStatement = "insert INTO Customers VALUES(NULL, '" + name + "', '" + address + "', '" + postCode + "', '" +
                                                            phoneNumber + "', NOW(), '" + createdBy + "', NOW(), '" + createdBy +
                                                            "', " + divisionID + ")";
        Query.makeQuery(sqlStatement);
    }

    //READ
    /**
     * The getCustomer method returns a Customer if it was found in the database.
     *
     * @param customerID the ID number of the Customer.
     * @return a Customer object if the Customer was found in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static Customer getCustomer(int customerID) throws SQLException, Exception{
        String sqlStatement = "select * FROM customers WHERE Customer_ID  = " + customerID;
        Query.makeQuery(sqlStatement);
        Customer customerResult;
        ResultSet result = Query.getResult();
        while(result.next()){
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postCode = result.getString("Postal_Code");
            String phoneNumber = result.getString("Phone");
            LocalDateTime creationDate = result.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = result.getString("Created_By");
            LocalDateTime lastUpdated = result.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int divisionID = result.getInt("Division_ID");
            customerResult = new Customer(customerID, name, address, postCode, phoneNumber, creationDate, createdBy,
                                            lastUpdated, lastUpdatedBy, divisionID);
            return customerResult;
        }
        return null;
    }
    /**
     * The getAllCustomers method returns an ObservableList of Customers from the database.
     * @return an ObservableList of Customers from the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception{
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = "select * from customers";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int customerID = result.getInt("Customer_ID");
            String name = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postCode = result.getString("Postal_Code");
            String phoneNumber = result.getString("Phone");
            LocalDateTime creationDate = result.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = result.getString("Created_By");
            LocalDateTime lastUpdated = result.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int divisionID = result.getInt("Division_ID");
            Customer customerResult = new Customer(customerID, name, address, postCode, phoneNumber, creationDate,
                                                    createdBy, lastUpdated, lastUpdatedBy, divisionID);
            allCustomers.add(customerResult);
        }
        return allCustomers;
    }

    //UPDATE
    /**
     * The updateCustomer method updates an existing Customer in the database.
     *
     * @param customerID the ID number of the existing Customer.
     * @param name The Customer's name.
     * @param address The Customer's address.
     * @param postCode The Customer's postal code.
     * @param phoneNumber The Customer's phone number.
     * @param updatedBy The User who is updating the Customer.
     * @param divisionID The ID number of the FirstLevelDivision the Customer is based in.
     */
    public static void updateCustomer(int customerID, String name, String address, String postCode, String phoneNumber,
                                      String updatedBy, int divisionID){
        String sqlStatement = "update Customers SET Customer_Name = '" + name + "', Address = '" + address +
                                "', Postal_Code = '" + postCode + "', Phone = '" + phoneNumber +
                                "', Last_Update = NOW(), Last_Updated_By = '" + updatedBy + "', Division_ID = " +
                                divisionID + " WHERE Customer_ID = " + customerID;
        Query.makeQuery(sqlStatement);
    }

    //DELETE
    /**
     * The deleteCustomer method deletes a Customer's Appointments from the database, then deletes the Customer as well.
     *
     * @param customerID the ID number of the Customer being deleted.
     */
    public static void deleteCustomer(int customerID){
        String sqlStatement = "delete FROM Appointments WHERE Customer_ID = " + customerID;
        Query.makeQuery(sqlStatement);
        sqlStatement = "delete FROM Customers WHERE Customer_ID = " + customerID;
        Query.makeQuery(sqlStatement);
    }
}
