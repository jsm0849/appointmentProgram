/**
 * The ReportsController class implements the code behind the reports.fxml file. It displays three different reports: a schedule
 * of Appointments for each Contact in the organization, a list of types of Appointments and their frequency for each month, and
 * a master schedule of Appointments for each location in the organization.
 *
 * @author Jacob Smith
 */

package controller;

import dao.AppointmentDAO;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import model.Contact;
import dao.ContactDAO;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReportsController implements Initializable{

    public TableView contactView, contactV;  //contactV is the TableView which only holds the unique Contacts.
    public TableColumn contactViewContactColumn, contactViewTitleColumn, contactViewLocationColumn, contactViewDescriptionColumn;
    public TableColumn contactViewTypeColumn, contactViewStartDTColumn, contactViewEndDTColumn, contactViewCustomerIDColumn;
    public TableColumn contactViewApptIDColumn;

    public TableView typeView;
    public TableColumn typeViewTypeColumn, typeViewNumApptsColumn;
    public ComboBox<String> monthComboBox;
    public ComboBox<String> yearComboBox;

    public TableView locationView, locationV; //locationV is the TableView which only holds the unique locations.
    public TableColumn locViewLocationColumn, locViewApptIDColumn, locViewTitleColumn, locViewDescriptionColumn, locViewTypeColumn;
    public TableColumn locViewStartDTColumn, locViewEndDTColumn, locViewCustomerIDColumn, locViewContactColumn;

    public Button mainMenuButton;

    private ObservableList<Appointment> typeAppts = FXCollections.observableArrayList(); //The list of Appointments of a specific month in time.
    private ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    private ObservableList<Contact> contactList = FXCollections.observableArrayList();
    private ObservableList<Appointment> locationList = FXCollections.observableArrayList();

    /**
     * The initialize method populates the tables with all the appropriate data, pulled from the database.
     *
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            allAppts = AppointmentDAO.getAllAppointments();
            contactList = ContactDAO.getAllContacts();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        contactViewApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        contactViewTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactViewDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactViewLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactViewContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactViewTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactViewStartDTColumn.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        contactViewEndDTColumn.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        contactViewCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        locViewApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        locViewTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        locViewDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locViewLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locViewContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        locViewTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        locViewStartDTColumn.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        locViewEndDTColumn.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        locViewCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        contactList.forEach(contact -> {
            contactV.getItems().add(contact);
            contactView.getItems().add(new Appointment()); //Adding blank rows between sections of Appointments for clarity.
            allAppts.forEach(appointment -> {
                if(appointment.getContactID() == contact.getContactID()){
                    contactView.getItems().add(appointment);
                    contactV.getItems().add(new Contact()); //Adding blank rows between unique Contacts for clarity.
                }
            });
        });

        AtomicBoolean isNewLocation = new AtomicBoolean(false);
        //The first lambda expression loops through all the Appointments, finding each unique location and building a list.
        //The second lambda expression loops through all the newly found unique locations, grouping and displaying the Appointments
        //appropriately in chronological order.
        allAppts.forEach(appointment -> {
            isNewLocation.set(true);
            locationList.forEach(location -> {
                if(appointment.getLocation().equals(location)){
                    isNewLocation.set(false);
                }
            });
            if(isNewLocation.get()){
                locationList.add(appointment);
            }
        });
        locationList.forEach(location -> {
            locationV.getItems().add(location);
            locationView.getItems().add(new Appointment());
                allAppts.forEach(appointment -> {
                    if(appointment.getLocation().equals(location.getLocation())){
                        locationView.getItems().add(appointment);
                        locationV.getItems().add(new Appointment());
                    }
                });
        });

        monthComboBox.getItems().add("January");
        monthComboBox.getItems().add("February");
        monthComboBox.getItems().add("March");
        monthComboBox.getItems().add("April");
        monthComboBox.getItems().add("May");
        monthComboBox.getItems().add("June");
        monthComboBox.getItems().add("July");
        monthComboBox.getItems().add("August");
        monthComboBox.getItems().add("September");
        monthComboBox.getItems().add("October");
        monthComboBox.getItems().add("November");
        monthComboBox.getItems().add("December");
        LocalDateTime today = LocalDateTime.now();
        for(int i = 2000; i < (today.getYear() + 101); i++){
            yearComboBox.getItems().add(Integer.toString(i));
        }
        monthComboBox.getSelectionModel().selectFirst();
        yearComboBox.getSelectionModel().select(Integer.toString(today.getYear()));
        monthSelected();
    }
    /**
     * The monthSelected method runs whenever a new selection is made in the month or year combo boxes. It updates the displayed
     * list of types of Appointments and the frequency of those Appointments for that specific month.
     * Lambda Expressions: Lambda expressions were used to loop through each Appointment to get a list of unique types, then loops
     * back through the list of unique types and sorts the Appointments for the current month by type.
     */
    public void monthSelected(){
        String monthSelected = monthComboBox.getSelectionModel().getSelectedItem();
        int yearSelected = Integer.valueOf(yearComboBox.getSelectionModel().getSelectedItem());
        ObservableList<TypeData> types = FXCollections.observableArrayList();
        typeAppts.clear();
        allAppts.forEach(appointment -> {
            if(appointment.getStartDT().getMonth().toString().toLowerCase().equals(monthSelected.toLowerCase()) &&
                    appointment.getStartDT().getYear() == yearSelected){
                typeAppts.add(appointment);
            }
        });
        AtomicBoolean isNewType = new AtomicBoolean(false);
        typeAppts.forEach(appointment -> {
            isNewType.set(true);
            types.forEach(type -> {
                if(appointment.getType().equals(type.getType())){
                    isNewType.set(false);
                }
            });
            if(isNewType.get()){
                types.add(new TypeData(appointment.getType(), 0));
            }
        });
        types.forEach(type -> {
            typeAppts.forEach(appointment -> {
                if(appointment.getType().equals(type.getType())){
                    type.addAppointment();
                }
            });
        });
        typeView.setItems(types);
        typeViewTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeViewNumApptsColumn.setCellValueFactory(new PropertyValueFactory<>("numAppointments"));
    }
    /**
     * The yearSelected method runs whenever a new selection is made in the year combo box.
     */
    public void yearSelected(){
        monthSelected();
    }
    /**
     * The mainMenu method returns the User to the main menu of the application.
     * @throws IOException
     */
    public void mainMenu() throws IOException {
        Stage stage = (Stage)mainMenuButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
