/**
 * The AddCustomerController class implements the AddCustomer.fxml user interface, and allows a new Customer to be saved
 * to the database.
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
import model.FirstLevelDivision;
import dao.DivisionDAO;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable{
    public TextField nameField, addressField, postalCodeField, phoneNumberField;
    public ComboBox<Country> countryComboBox;
    public ComboBox<FirstLevelDivision> divisionComboBox;
    public Button saveButton, cancelButton;
    public ObservableList<Country> countryList = FXCollections.observableArrayList();
    public ObservableList<FirstLevelDivision> divisionList = FXCollections.observableArrayList();

    /**
     * The initialize method initializes all the data needed for the add customer page.
     *
     * @param url the url.
     * @param resourceBundle the resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            countryList.setAll(CountryDAO.getAllCountries());
            countryComboBox.setItems(countryList);
            countryComboBox.getSelectionModel().selectFirst();
            divisionList.setAll(DivisionDAO.getAllDivisions());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        countrySelected();
    }

    /**
     * The countrySelected method runs whenever a new selection is made in the countryComboBox. It updates the available
     * list of FirstLevelDivisions to those only in the selected Country.
     */
    public void countrySelected(){
        Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        divisionComboBox.getItems().clear();
        divisionList.forEach(firstLevelDivision -> {
            if(firstLevelDivision.getCountryID() == selectedCountry.getCountryID()){
                divisionComboBox.getItems().add(firstLevelDivision);
            }
        });
        divisionComboBox.getSelectionModel().selectFirst();
    }

    /**
     * The save method runs whenever the save button is clicked. It attempts to save a new Customer to the database, and
     * returns to the main menu.
     *
     * @throws IOException
     */
    public void save() throws IOException {
        String name = nameField.getText();
        FirstLevelDivision division = divisionComboBox.getSelectionModel().getSelectedItem();
        int divisionID = division.getDivisionID();
        String address = addressField.getText();
        String postCode = postalCodeField.getText();
        String phoneNumber = phoneNumberField.getText();
        UserHolder userHolder = UserHolder.getInstance();
        User user = userHolder.getUser();
        String createdBy = user.getUserName();
        CustomerDAO.addCustomer(name, address, postCode, phoneNumber, createdBy, divisionID);

        Stage stage = (Stage)saveButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The cancel method runs when the cancel button is clicked. It cancels the new Customer and returns to the main menu.
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
