/**
 * The AddAppointmentController class implements the AddAppointment.fxml user interface and allows an Appointment to be
 * added to the database.
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
import model.Appointment;
import model.User;
import dao.UserDAO;
import model.Contact;
import dao.ContactDAO;
import model.Customer;
import dao.CustomerDAO;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class AddAppointmentController implements Initializable {
    public TextField titleField, descriptionField, locationField, typeField;
    public ComboBox<Contact> contactComboBox;
    public ComboBox<Customer> customerComboBox;
    public ComboBox<String> startMonthComboBox, startDayComboBox, startYearComboBox;
    public ComboBox<String> endMonthComboBox, endDayComboBox, endYearComboBox;
    public ComboBox<LocalTime> startTimeComboBox, endTimeComboBox;
    public ComboBox<User> userComboBox;
    public Button saveButton, cancelButton;
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<User> userList = FXCollections.observableArrayList();

    /**
     * The initialize method initializes all the necessary data to use the AddAppointment page.
     *
     * @param url the url.
     * @param resourceBundle the resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            contactList.setAll(ContactDAO.getAllContacts());
            contactComboBox.setItems(contactList);
            contactComboBox.getSelectionModel().selectFirst();
            customerList.setAll(CustomerDAO.getAllCustomers());
            customerComboBox.setItems(customerList);
            customerComboBox.getSelectionModel().selectFirst();
            userList.setAll(UserDAO.getAllUsers());
            userComboBox.setItems(userList);
            userComboBox.getSelectionModel().selectFirst();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 41);
        while(start.isBefore(end)){                         //Initializing the time ComboBoxes.
            startTimeComboBox.getItems().add(start);
            endTimeComboBox.getItems().add(start);
            start = start.plusMinutes(10);
        }
        startTimeComboBox.getItems().add(LocalTime.of(23, 50));
        endTimeComboBox.getItems().add(LocalTime.of(23, 50));
        startTimeComboBox.getSelectionModel().select(LocalTime.of(0, 0));
        endTimeComboBox.getSelectionModel().select(LocalTime.of(0, 0));
        for(int i = 1; i < 13; i++){                        //Initializing the month ComboBox.
            if(i < 10){
                startMonthComboBox.getItems().add("0" + i);
                endMonthComboBox.getItems().add("0" + i);
            }else{
                startMonthComboBox.getItems().add(Integer.toString(i));
                endMonthComboBox.getItems().add(Integer.toString(i));
            }
        }
        startMonthComboBox.getSelectionModel().select("01");
        endMonthComboBox.getSelectionModel().select("01");
        LocalDate currentDate = LocalDate.now();
        for(int i = currentDate.getYear(); i < (currentDate.getYear() + 101); i++){         //Initializing the year ComboBox.
            startYearComboBox.getItems().add(Integer.toString(i));
            endYearComboBox.getItems().add(Integer.toString(i));
        }
        startYearComboBox.getSelectionModel().select(Integer.toString(currentDate.getYear()));
        endYearComboBox.getSelectionModel().select(Integer.toString(currentDate.getYear()));
        startMonthSelected();
        endMonthSelected();
    }
    /**
     * The startMonthSelected method runs whenever a new selection is made in the startMonthComboBox.
     */
    public void startMonthSelected(){
        String month = startMonthComboBox.getSelectionModel().getSelectedItem();
        startDayComboBox.getItems().clear();                    //Clearing out the day ComboBox to refill with correct number of days.
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
        startDayComboBox.getSelectionModel().select("01");
    }
    /**
     * The endMonthSelected method runs whenever a new selection is made in the endMonthComboBox.
     */
    public void endMonthSelected(){
        String month = endMonthComboBox.getSelectionModel().getSelectedItem();
        endDayComboBox.getItems().clear();          //Clearing the day ComboBox to refill with correct number of days.
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
        endDayComboBox.getSelectionModel().select("01");
    }
    /**
     * The save method runs whenever the Save button is clicked. It attempts to add a new Appointment to the database, if
     * the user inputs are valid.
     *
     * LAMBDA EXPRESSIONS: The OverlapInterface interface is implemented here, which checks for any overlapping Appointments with
     * the provided start and end times.
     *
     * @throws IOException
     */
    public void save() throws IOException {
        ZoneId userZone = ZoneId.systemDefault();
        LocalDateTime startDateTime, endDateTime;
        ZonedDateTime zonedStartDT, zonedEndDT;
        StringBuilder startSB = new StringBuilder(""); //The start string builder and end string builder are used to build
        StringBuilder endSB = new StringBuilder("");   //a String which is parsed into a LocalDateTime to be passed to the database.
        LocalTime startTime = startTimeComboBox.getSelectionModel().getSelectedItem();
        LocalTime endTime = endTimeComboBox.getSelectionModel().getSelectedItem();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm");
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Input not valid");

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

        startSB.append(startMonthComboBox.getSelectionModel().getSelectedItem() + " ");         //Building the start and end datetimes.
        startSB.append(startDayComboBox.getSelectionModel().getSelectedItem() + " ");
        startSB.append(startYearComboBox.getSelectionModel().getSelectedItem() + " ");
        startSB.append(startTime.toString());
        startDateTime = LocalDateTime.parse(startSB, formatter);
        endSB.append(endMonthComboBox.getSelectionModel().getSelectedItem() + " ");
        endSB.append(endDayComboBox.getSelectionModel().getSelectedItem() + " ");
        endSB.append(endYearComboBox.getSelectionModel().getSelectedItem() + " ");
        endSB.append(endTime.toString());
        endDateTime = LocalDateTime.parse(endSB, formatter);

        if(startDateTime.isAfter(endDateTime)){             //Data validity checks.
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
        String createdBy = userComboBox.getSelectionModel().getSelectedItem().getUserName();
        int userID = userComboBox.getSelectionModel().getSelectedItem().getUserID();
        int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();

        AppointmentDAO.addAppointment(title, description, location, type, startDateTime, endDateTime, createdBy, customerID, userID, contactID);

        Stage stage = (Stage)saveButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();                   //Returning to the main menu after a successful save.
    }
    /**
     * The cancel method cancels the new Appointment and returns to the main menu.
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
