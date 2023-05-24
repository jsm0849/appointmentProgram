/**
 * The ModifyCustomerController class implements the code behind the modify customer page, allowing the User to modify an
 * existing Customer.
 *
 * @author Jacob Smith
 */

package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import dao.CustomerDAO;
import model.Country;
import dao.CountryDAO;
import model.Customer;
import model.FirstLevelDivision;
import dao.DivisionDAO;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable{
    public TextField customerIDField, nameField, addressField, postalCodeField, phoneNumberField;
    public ComboBox<Country> countryComboBox;
    public ComboBox<FirstLevelDivision> divisionComboBox;
    public Button saveButton, cancelButton;
    public ObservableList<Country> countryList = FXCollections.observableArrayList();
    public ObservableList<FirstLevelDivision> divisionList = FXCollections.observableArrayList();

    /**
     * The initialize method populates all the Customer fields with data from the database on the existing Customer.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        CustomerHolder customerHolder = CustomerHolder.getInstance();
        Customer selectedCustomer = customerHolder.getCustomer();
        try{
            countryList.setAll(CountryDAO.getAllCountries());
            countryComboBox.setItems(countryList);
            countryList.forEach(country -> {            //Selecting the appropriate Country based on data pulled from the database.
                if(country.getCountryName().equals(selectedCustomer.getCountryName())){
                    countryComboBox.getSelectionModel().select(country);
                }
            });
            divisionList.setAll(DivisionDAO.getAllDivisions());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        countrySelected();
        divisionList.forEach(firstLevelDivision -> {                    //Selecting the appropriate FirstLevelDivision for the Customer.
            if(firstLevelDivision.getDivisionID() == selectedCustomer.getDivisionID()){
                divisionComboBox.getSelectionModel().select(firstLevelDivision);
            }
        });
        customerIDField.setText(Integer.toString(selectedCustomer.getCustomerID()));
        nameField.setText(selectedCustomer.getCustomerName());
        addressField.setText(selectedCustomer.getStreetAddress());
        postalCodeField.setText(selectedCustomer.getPostalCode());
        phoneNumberField.setText(selectedCustomer.getPhoneNumber());
    }
    /**
     * The countrySelected method runs when a new selection is made in the country combo box. It updates the available
     * FirstLevelDivisions.
     */
    public void countrySelected(){
        Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        divisionComboBox.getItems().clear();
        divisionList.forEach(firstLevelDivision -> {        //Choosing which divisions match the selected country.
            if(firstLevelDivision.getCountryID() == selectedCountry.getCountryID()){
                divisionComboBox.getItems().add(firstLevelDivision);
            }
        });
        divisionComboBox.getSelectionModel().selectFirst();
    }
    /**
     * The save method runs when the User clicks the save button. It attempts to update the Customer in the database, then return
     * to the main menu, if the inputted data is appropriate and valid.
     *
     * @throws IOException
     */
    public void save() throws IOException {
        int customerID = Integer.valueOf(customerIDField.getText());
        String name = nameField.getText();
        FirstLevelDivision division = divisionComboBox.getSelectionModel().getSelectedItem();
        int divisionID = division.getDivisionID();
        String address = addressField.getText();
        String postCode = postalCodeField.getText();
        String phoneNumber = phoneNumberField.getText();
        UserHolder userHolder = UserHolder.getInstance();
        User user = userHolder.getUser();
        String updatedBy = user.getUserName();
        CustomerDAO.updateCustomer(customerID, name, address, postCode, phoneNumber, updatedBy, divisionID);

        Stage stage = (Stage)saveButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The cancel method runs when the cancel button is clicked. It returns to the main menu leaving the Customer unmodified.
     *
     * @throws IOException
     */
    public void cancel() throws IOException {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
