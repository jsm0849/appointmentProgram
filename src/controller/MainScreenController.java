/**
 * The MainScreenController class implements the code behind the mainMenu.fxml file. It displays the lists of Customers
 * and Appointments found in the database, and allows manipulation of each, which will be updated in the database.
 *
 * @author Jacob Smith
 */

package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.util.Optional;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.io.*;
import java.util.Scanner;
import dao.AppointmentDAO;
import dao.CustomerDAO;
import javafx.stage.Stage;
import model.Customer;
import model.Appointment;

public class MainScreenController implements Initializable {
    private ZonedDateTime loginTime;
    public TableView appointmentView;
    public TableColumn apptIDColumn, apptTitleColumn, apptDescriptionColumn, apptLocationColumn, apptContactColumn;
    public TableColumn apptTypeColumn, apptStartDTColumn, apptEndDTColumn, apptCustomerIDColumn, apptUserIDColumn;
    public TableView customerView;
    public TableColumn customerIDColumn, customerNameColumn, customerCountryColumn, customerFirstLevelDivisionColumn;
    public TableColumn customerAddressColumn, customerPostalCodeColumn, customerPhoneNumberColumn;
    public Label upcomingLabel;
    public ToggleGroup apptViewGroup;
    public RadioButton weekRadioButton, monthRadioButton, allRadioButton;
    public Button reportsButton, mainExitButton;
    public Button customerAddButton, customerModifyButton, customerDeleteButton;
    public Button appointmentAddButton, appointmentUpdateButton, appointmentDeleteButton;

    /**
     * The initialize method reads the Customer and Appointment data from the database and populates the appropriate tables.
     *
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        String filename = "login_attempts.txt";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm:ss z");
        File loginFile = new File(filename);
        String loginTimeString;
        Scanner fileScanner;
        try {                                           //Ultimately reading the last login time in the login records file.
            fileScanner = new Scanner(loginFile);
            while(fileScanner.hasNext()){
                loginTimeString = fileScanner.nextLine();
                loginTime = ZonedDateTime.parse(loginTimeString, formatter);
                fileScanner.nextLine();
            }
            fileScanner.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        updateCustomerView();
        updateAppointmentView();
    }
    /**
     * The updateCustomerView method runs whenever the customer table needs to be updated (if an add, update, or delete was
     * performed).
     */
    public void updateCustomerView(){
        try {
            customerView.setItems(CustomerDAO.getAllCustomers());
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("countryName"));
            customerFirstLevelDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
            customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("streetAddress"));
            customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * The updateAppointmentView method runs whenever the appointment table needs to be updated (when an add, update, or
     * delete is performed, or when the User needs to view appointments for the current month or week).
     */
    public void updateAppointmentView(){
        RadioButton selectedButton = (RadioButton)apptViewGroup.getSelectedToggle();
        String selectedView = selectedButton.getText();
        try{
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            ObservableList<Appointment> appointmentDisplayList = FXCollections.observableArrayList();
            appointmentList.setAll(AppointmentDAO.getAllAppointments());
            switch(selectedView){               //Checking which appointments should be displayed based on which button the User clicked.
                case "Current Week":
                    appointmentList.forEach(appointment -> {
                        checkLoginTime(appointment.getStartDT(), appointment.getAppointmentID());
                        if (appointment.getStartDT().isAfter(loginTime.toLocalDateTime()) &&
                                appointment.getStartDT().isBefore(loginTime.toLocalDateTime().plusDays(6))) {
                            appointmentDisplayList.add(appointment);
                        }else if(appointment.getStartDT().getDayOfYear() == loginTime.getDayOfYear() &&
                                appointment.getStartDT().getMonthValue() == loginTime.getMonthValue()){
                            appointmentDisplayList.add(appointment);
                        }
                    });
                    break;
                case "Current Month":
                    appointmentList.forEach(appointment -> {
                        checkLoginTime(appointment.getStartDT(), appointment.getAppointmentID());
                        if (appointment.getStartDT().getMonthValue() == loginTime.toLocalDateTime().getMonthValue()) {
                            appointmentDisplayList.add(appointment);
                        }
                    });
                    break;
                case "All":
                    appointmentList.forEach(appointment -> {
                        checkLoginTime(appointment.getStartDT(), appointment.getAppointmentID());
                        appointmentDisplayList.add(appointment);
                    });
                    break;
            }
            appointmentView.setItems(appointmentDisplayList);
            apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            apptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptStartDTColumn.setCellValueFactory(new PropertyValueFactory<>("startDT"));
            apptEndDTColumn.setCellValueFactory(new PropertyValueFactory<>("endDT"));
            apptCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            apptUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * The checkLoginTime method checks a submitted appointment time against the time the User logged in, to help determine if
     * the appointment is in the next 15 minutes.
     *
     * @param appointmentTime The date and time of the Appointment.
     * @param appointmentID The ID number of the Appointment.
     */
    public void checkLoginTime(LocalDateTime appointmentTime, int appointmentID){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if(loginTime.toLocalDateTime().isBefore(appointmentTime) && loginTime.toLocalDateTime().isAfter(appointmentTime.minusMinutes(15))){
            upcomingLabel.setText("Alert: There is an upcoming appointment at " + appointmentTime.format(formatter) +
                                    ", The appointment ID is " + appointmentID);        //Changing the display if necessary for an
                                                                                        //upcoming appointment.
        }
    }
    /**
     * The exit method runs when the exit button is clicked, checking with the User again before closing the program.
     */
    public void exit() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setHeaderText("Closing Application!");
        exitAlert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> option = exitAlert.showAndWait();
        if(option.isPresent() && option.get() == ButtonType.OK){
            Platform.exit();
        }
    }
    /**
     * The deleteSelectedCustomer method deletes the selected Customer after double-checking with the User.
     */
    public void deleteSelectedCustomer() {
        Customer selectedCustomer = (Customer)customerView.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setHeaderText("Customer removed.");
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Input not valid");
        if(selectedCustomer == null){
            error.setContentText("No customer selected!");
            error.showAndWait();
        }else{
            Alert removal = new Alert(Alert.AlertType.CONFIRMATION);
            removal.setContentText("Are you sure you want to delete this customer? Associated appointments will be removed as well.");
            Optional<ButtonType> option = removal.showAndWait();
            if(option.isPresent() && option.get() == ButtonType.OK){
                CustomerDAO.deleteCustomer(selectedCustomer.getCustomerID());       //The CustomerDAO method deletes relevant
                updateCustomerView();                                               //Appointments as well.
                updateAppointmentView();
                confirmation.showAndWait();
            }
        }
    }
    /**
     * The switchToModifyCustomer method changes the menu to the modify customer menu for the selected customer.
     * @throws IOException
     */
    public void switchToModifyCustomer() throws IOException {
        Customer selectedCustomer = (Customer)customerView.getSelectionModel().getSelectedItem();
        CustomerHolder customerHolder = CustomerHolder.getInstance();
        customerHolder.setCustomer(selectedCustomer);
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Input not valid");
        if(selectedCustomer == null){
            error.setContentText("No customer selected!");
            error.showAndWait();
        }else{
            Stage stage = (Stage)customerAddButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/modifyCustomer.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * The switchToAddCustomer method changes the menu to the add customer menu.
     * @throws IOException
     */
    public void switchToAddCustomer() throws IOException {
        Stage stage = (Stage)customerAddButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/addCustomer.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The showWeekAppts method updates the appointment table to show the appointments in the next 7 days.
     */
    public void showWeekAppts(){
        updateAppointmentView();
    }
    /**
     * The showMonthAppts method updates the appointment table to show the appointments this month.
     */
    public void showMonthAppts(){
        updateAppointmentView();
    }
    /**
     * The showAllAppts method updates the appointment table to show all appointments in the database.
     */
    public void showAllAppts(){
        updateAppointmentView();
    }
    /**
     * The deleteSelectedAppointment method deletes the selected appointment from the database.
     */
    public void deleteSelectedAppointment() {
        Appointment selectedAppointment = (Appointment)appointmentView.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setHeaderText("Appointment ID#" + selectedAppointment.getAppointmentID() + " was deleted. It was a " +
                                    selectedAppointment.getType() + " appointment.");
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Input not valid");
        if(selectedAppointment == null){
            error.setContentText("No appointment selected!");
            error.showAndWait();
        }else{
            Alert removal = new Alert(Alert.AlertType.CONFIRMATION);
            removal.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> option = removal.showAndWait();
            if(option.isPresent() && option.get() == ButtonType.OK){
                AppointmentDAO.deleteAppointment(selectedAppointment.getAppointmentID());
                updateAppointmentView();
                confirmation.showAndWait();
            }
        }
    }
    /**
     * The switchToUpdateAppointment method changes the menu to the update appointment menu for the selected appointment.
     *
     * @throws IOException
     */
    public void switchToUpdateAppointment() throws IOException {
        Appointment selectedAppointment = (Appointment)appointmentView.getSelectionModel().getSelectedItem();
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Input not valid");
        if(selectedAppointment == null){
            error.setContentText("No appointment selected!");
            error.showAndWait();
        }else{
            AppointmentHolder appointmentHolder = AppointmentHolder.getInstance();
            appointmentHolder.setAppointment(selectedAppointment);
            Stage stage = (Stage)appointmentUpdateButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/updateAppointment.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * The switchToAddAppointments method changes the menu to the add appointment menu.
     *
     * @throws IOException
     */
    public void switchToAddAppointment() throws IOException {
        Stage stage = (Stage)appointmentAddButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/addAppointment.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The switchToReports method changes the menu to the reports menu.
     *
     * @throws IOException
     */
    public void switchToReports() throws IOException {
            Stage stage = (Stage)reportsButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/reports.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
}
