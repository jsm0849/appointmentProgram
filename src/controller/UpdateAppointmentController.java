/**
 * The UpdateAppointmentController class implements the code behind the update appointment page. It allows the User to update
 * the information an Appointment contains in the database.
 *
 * @author Jacob Smith
 */

package controller;

import dao.AppointmentDAO;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import model.User;
import dao.UserDAO;
import model.Contact;
import dao.ContactDAO;
import model.Customer;
import dao.CustomerDAO;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class UpdateAppointmentController implements Initializable{
    public TextField appointmentIDField, titleField, descriptionField, locationField, typeField;
    public ComboBox<Contact> contactComboBox;
    public ComboBox<Customer> customerComboBox;
    public ComboBox<String> startMonthComboBox, startDayComboBox, startYearComboBox;
    public ComboBox<String> endMonthComboBox, endDayComboBox, endYearComboBox;
    public ComboBox<LocalTime> startTimeComboBox, endTimeComboBox;
    public ComboBox<User> userComboBox;
    public Button updateButton, cancelButton;
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<User> userList = FXCollections.observableArrayList();
    private Appointment selectedAppt;

    /**
     * The initalize method populates the Appointment fields with data from the database.
     *
     * LAMBDA EXPRESSIONS: Lambda expressions were used on ObservableLists of Contacts, Users, and Customers to check against
     * the selected Appointment and populate each ComboBox with the actual object and all of its data, instead of just the small
     * piece of data about it that the Appointment held. This allows the rest of the data in those objects to be referenced later.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        AppointmentHolder appointmentHolder = AppointmentHolder.getInstance();
        selectedAppt = appointmentHolder.getAppointment();   //Getting the Appointment that was selected in the main menu.
        try{
            contactList.setAll(ContactDAO.getAllContacts());
            contactComboBox.setItems(contactList);
            contactList.forEach(contact -> {
                if(contact.getContactID() == selectedAppt.getContactID()){
                    contactComboBox.getSelectionModel().select(contact);
                }
            });

            customerList.setAll(CustomerDAO.getAllCustomers());
            customerComboBox.setItems(customerList);
            customerList.forEach(customer -> {
                if(customer.getCustomerID() == selectedAppt.getCustomerID()){
                    customerComboBox.getSelectionModel().select(customer);
                }
            });

            userList.setAll(UserDAO.getAllUsers());
            userComboBox.setItems(userList);
            userList.forEach(user -> {
                if(user.getUserID() == selectedAppt.getUserID()){
                    userComboBox.getSelectionModel().select(user);
                }
            });
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 41);
        while(start.isBefore(end)){
            startTimeComboBox.getItems().add(start);
            endTimeComboBox.getItems().add(start);
            if(start.getHour() == selectedAppt.getStartDT().getHour() && start.getMinute() == selectedAppt.getStartDT().getMinute()){
                startTimeComboBox.getSelectionModel().select(start);
            }
            if(start.getHour() == selectedAppt.getEndDT().getHour() && start.getMinute() == selectedAppt.getEndDT().getMinute()){
                endTimeComboBox.getSelectionModel().select(start);
            }
            start = start.plusMinutes(10);
        }
        startTimeComboBox.getItems().add(LocalTime.of(23, 50));
        endTimeComboBox.getItems().add(LocalTime.of(23, 50));
        for(int i = 1; i < 13; i++) {
            if (i < 10) {
                startMonthComboBox.getItems().add("0" + i);
                endMonthComboBox.getItems().add("0" + i);
            } else {
                startMonthComboBox.getItems().add(Integer.toString(i));
                endMonthComboBox.getItems().add(Integer.toString(i));
            }
        }
        if(selectedAppt.getStartDT().getMonthValue() < 10){
            startMonthComboBox.getSelectionModel().select("0" + selectedAppt.getStartDT().getMonthValue());
            endMonthComboBox.getSelectionModel().select("0" + selectedAppt.getStartDT().getMonthValue());
        }else{
            startMonthComboBox.getSelectionModel().select(selectedAppt.getStartDT().getMonthValue());
            endMonthComboBox.getSelectionModel().select(selectedAppt.getStartDT().getMonthValue());
        }
        LocalDate currentDate = LocalDate.now();
        for(int i = 2000; i < (currentDate.getYear() + 101); i++){
            startYearComboBox.getItems().add(Integer.toString(i));
            endYearComboBox.getItems().add(Integer.toString(i));
        }
        startYearComboBox.getSelectionModel().select(Integer.toString(selectedAppt.getStartDT().getYear()));
        endYearComboBox.getSelectionModel().select(Integer.toString(selectedAppt.getEndDT().getYear()));
        startMonthSelected();
        endMonthSelected();
        appointmentIDField.setText(Integer.toString(selectedAppt.getAppointmentID()));
        titleField.setText(selectedAppt.getTitle());
        descriptionField.setText(selectedAppt.getDescription());
        locationField.setText(selectedAppt.getLocation());
        typeField.setText(selectedAppt.getType());
    }

    /**
     * The startMonthSelected method updates the available number of days to choose from, based on the selected month.
     */
    public void startMonthSelected(){
        String month = startMonthComboBox.getSelectionModel().getSelectedItem();
        startDayComboBox.getItems().clear();
        switch (month){
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                for(int i = 1; i < 32; i++){
                    if(i < 10){
                        startDayComboBox.getItems().add("0" + i);
                    }else{
                        startDayComboBox.getItems().add(Integer.toString(i));
                    }
                }
                break;
            case "04":
            case "06":
            case "09":
            case "11":
                for(int i = 1; i < 31; i++){
                    if(i < 10){
                        startDayComboBox.getItems().add("0" + i);
                    }else{
                        startDayComboBox.getItems().add(Integer.toString(i));
                    }
                }
                break;
            default:
                for(int i = 1; i < 29; i++){
                    if(i < 10){
                        startDayComboBox.getItems().add("0" + i);
                    }else{
                        startDayComboBox.getItems().add(Integer.toString(i));
                    }
                }
                break;
        }
        if(selectedAppt.getEndDT().getDayOfMonth() < 10){
            startDayComboBox.getSelectionModel().select( "0" + selectedAppt.getStartDT().getDayOfMonth());
        }else{
            startDayComboBox.getSelectionModel().select(Integer.toString(selectedAppt.getStartDT().getDayOfMonth()));
        }
    }
    /**
     * The endMonthSelected method updates the number of days available for selection, based on the month selected.
     */
    public void endMonthSelected(){
        String month = endMonthComboBox.getSelectionModel().getSelectedItem();
        endDayComboBox.getItems().clear();
        switch (month){
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                for(int i = 1; i < 32; i++){
                    if(i < 10){
                        endDayComboBox.getItems().add("0" + i);
                    }else{
                        endDayComboBox.getItems().add(Integer.toString(i));
                    }
                }
                break;
            case "04":
            case "06":
            case "09":
            case "11":
                for(int i = 1; i < 31; i++){
                    if(i < 10){
                        endDayComboBox.getItems().add("0" + i);
                    }else{
                        endDayComboBox.getItems().add(Integer.toString(i));
                    }
                }
                break;
            default:
                for(int i = 1; i < 29; i++){
                    if(i < 10){
                        endDayComboBox.getItems().add("0" + i);
                    }else{
                        endDayComboBox.getItems().add(Integer.toString(i));
                    }
                }
                break;
        }
        if(selectedAppt.getEndDT().getDayOfMonth() < 10){
            endDayComboBox.getSelectionModel().select( "0" + selectedAppt.getEndDT().getDayOfMonth());
        }else{
            endDayComboBox.getSelectionModel().select(Integer.toString(selectedAppt.getEndDT().getDayOfMonth()));
        }
    }
    /**
     * The update method runs when the update button is clicked. It attempts to update the Appointment data in the database
     * and return to the main menu, if the data was appropriate and valid.
     *
     * @throws IOException
     */
    public void update() throws IOException {
        ZoneId userZone = ZoneId.systemDefault();
        LocalDateTime startDateTime, endDateTime;
        ZonedDateTime zonedStartDT, zonedEndDT;
        StringBuilder startSB = new StringBuilder("");
        StringBuilder endSB = new StringBuilder("");
        LocalTime startTime = startTimeComboBox.getSelectionModel().getSelectedItem();
        LocalTime endTime = endTimeComboBox.getSelectionModel().getSelectedItem();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Input not valid");

        //Building a date and time as a String based on all the combo box selections, then checking its validity.
        startSB.append(startMonthComboBox.getSelectionModel().getSelectedItem() + " ");
        startSB.append(startDayComboBox.getSelectionModel().getSelectedItem() + " ");
        startSB.append(startYearComboBox.getSelectionModel().getSelectedItem() + " ");
        startSB.append(startTime.toString());
        startDateTime = LocalDateTime.parse(startSB, formatter);
        endSB.append(endMonthComboBox.getSelectionModel().getSelectedItem() + " ");
        endSB.append(endDayComboBox.getSelectionModel().getSelectedItem() + " ");
        endSB.append(endYearComboBox.getSelectionModel().getSelectedItem() + " ");
        endSB.append(endTime.toString());
        endDateTime = LocalDateTime.parse(endSB, formatter);

        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getCustomerID();
        OverlapInterface overlap = (s, e) -> {
            ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
            try {
                allAppts = AppointmentDAO.getAllAppointments();
            }catch(Exception exception){
                System.out.println(exception.getMessage());
            }
            AtomicInteger o = new AtomicInteger(0);
            allAppts.forEach(appointment -> {
                if((s.toLocalDateTime().isAfter(appointment.getStartDT()) && s.toLocalDateTime().isBefore(appointment.getEndDT())) ||
                        (e.toLocalDateTime().isAfter(appointment.getStartDT()) && e.toLocalDateTime().isBefore(appointment.getEndDT())) ||
                        (s.toLocalDateTime().equals(appointment.getStartDT()) || e.toLocalDateTime().equals(appointment.getEndDT()))){
                    System.out.println(appointment.getCustomerID() + " " + customerID);
                    if(appointment.getCustomerID() == customerID) {
                        o.set(1);
                    }
                }
            });
            if(o.get() == 0){
                return false;
            }
            return true;
        };

        if(startDateTime.isAfter(endDateTime)){     //checking data validity
            error.setContentText("Start time must be before end time.");
            error.showAndWait();
            return;
        }else if(startDateTime.isBefore(LocalDateTime.now())){
            error.setContentText("Start time must be some time in the future.");
            error.showAndWait();
            return;
        }else if(startDateTime.getDayOfMonth() != endDateTime.getDayOfMonth() ||
                startDateTime.getMonth() != endDateTime.getMonth() ||
                startDateTime.getYear() != endDateTime.getYear()){
            error.setContentText("Start and end times must be on the same day.");
            error.showAndWait();
            return;
        }
        //Checking the selected times against Eastern Time business hours.
        zonedStartDT = startDateTime.atZone(userZone);
        zonedStartDT = zonedStartDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        zonedEndDT = endDateTime.atZone(userZone);
        zonedEndDT = zonedEndDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        if(zonedStartDT.getHour() < 8 || zonedStartDT.getHour() > 22 ||
                (zonedStartDT.getHour() == 22 && zonedStartDT.getMinute() > 0)){
            error.setContentText("Start time must be between the hours of 8am and 10pm EST.");
            error.showAndWait();
            return;
        }else if(zonedEndDT.getHour() > 22 || (zonedEndDT.getHour() == 22 && zonedEndDT.getMinute() > 0)){
            error.setContentText("Appointment must end before 10pm EST.");
            error.showAndWait();
            return;
        }
        zonedStartDT = zonedStartDT.withZoneSameInstant(ZoneId.systemDefault());
        zonedEndDT = zonedEndDT.withZoneSameInstant(ZoneId.systemDefault());
        if(overlap.checkForOverlap(zonedStartDT, zonedEndDT)){
            error.setContentText("Appointment cannot overlap with any other appointments.");
            error.showAndWait();
            return;
        }

        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        String updatedBy = userComboBox.getSelectionModel().getSelectedItem().getUserName();
        int userID = userComboBox.getSelectionModel().getSelectedItem().getUserID();
        int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();

        AppointmentDAO.updateAppointment(selectedAppt.getAppointmentID(), title, description, location, type,
                                        startDateTime, endDateTime, updatedBy, customerID, userID, contactID);

        Stage stage = (Stage)updateButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The cancel method runs when the cancel button is clicked. It returns to the main menu and leaves the Appointment unmodified.
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
