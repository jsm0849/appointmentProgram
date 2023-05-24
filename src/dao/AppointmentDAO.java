/**
 * The AppointmentDAO class implements data access to create, read, update, and delete Appointments in the database.
 *
 * @author Jacob Smith
 */

package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import java.time.LocalDateTime;
import java.sql.Timestamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

public class AppointmentDAO {
    //CREATE
    /**
     * The addAppointment method adds an Appointment to the database.
     *
     * @param title the title of the Appointment.
     * @param description the description of the Appointment.
     * @param location the location of the Appointment.
     * @param type the type of the Appointment.
     * @param startTime the start time of the Appointment.
     * @param endTime the end time of the Appointment.
     * @param createdBy the username of the User who is creating the Appointment.
     * @param customerID the ID number of the customer whose Appointment it is.
     * @param userID the User associated with the Appointment.
     * @param contactID the Contact person associated with the Appointment.
     */
    public static void addAppointment(String title, String description, String location, String type, LocalDateTime startTime,
                                      LocalDateTime endTime, String createdBy, int customerID, int userID, int contactID){
        ZoneId userZone = ZoneId.systemDefault();
        startTime = startTime.atZone(userZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        endTime = endTime.atZone(userZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        String sqlStatement = "insert INTO Appointments VALUES(NULL, '" + title + "', '" + description + "', '" + location +
                                "', '" + type + "', '" + Timestamp.valueOf(startTime) + "', '" + Timestamp.valueOf(endTime) +
                                "', NOW(), '" + createdBy + "', NOW(), '" + createdBy + "', " + customerID + ", " + userID +
                                ", " + contactID + ")";
        Query.makeQuery(sqlStatement);
    }

    //READ

    /**
     * The getAppointment method returns an Appointment object if it was found in the database.
     *
     * @param appointmentID the ID number of the Appointment being searched for in the database.
     * @return an Appointment object if the Appointment was found in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static Appointment getAppointment(int appointmentID) throws SQLException, Exception{
        ZoneId userZone = ZoneId.systemDefault();
        String sqlStatement = "select * FROM appointments WHERE Appointment_ID  = " + appointmentID;
        Query.makeQuery(sqlStatement);
        Appointment appointmentResult;
        ResultSet result = Query.getResult();
        while(result.next()){
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            LocalDateTime startTime = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = result.getTimestamp("End").toLocalDateTime();
            LocalDateTime creationDate = result.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = result.getString("Created_By");
            LocalDateTime lastUpdated = result.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int customerID = result.getInt("Customer_ID");
            int userID = result.getInt("User_ID");
            int contactID = result.getInt("Contact_ID");
            appointmentResult = new Appointment(appointmentID, title, description, location, type, startTime, endTime,
                                                creationDate, createdBy, lastUpdated, lastUpdatedBy, customerID, userID,
                                                contactID);
            return appointmentResult;
        }
        return null;
    }

    /**
     * The getAllAppointments method returns an ObservableList of all Appointments in the database and sorts them
     * in ascending order by start time.
     *
     * @return an ObservableList of all Appointments in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception{
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        ZoneId userZone = ZoneId.systemDefault();
        String sqlStatement = "select * from appointments order by Start asc";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int appointmentID = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            LocalDateTime startTime = result.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = result.getTimestamp("End").toLocalDateTime();
            LocalDateTime creationDate = result.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = result.getString("Created_By");
            LocalDateTime lastUpdated = result.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int customerID = result.getInt("Customer_ID");
            int userID = result.getInt("User_ID");
            int contactID = result.getInt("Contact_ID");
            Appointment appointmentResult = new Appointment(appointmentID, title, description, location, type, startTime, endTime,
                    creationDate, createdBy, lastUpdated, lastUpdatedBy, customerID, userID,
                    contactID);
            allAppointments.add(appointmentResult);
        }
        return allAppointments;
    }

    //UPDATE

    /**
     * The updateAppointment method updates the updatable data in the database.
     * @param appointmentID the ID number of the Appointment being updated.
     * @param title the title of the Appointment.
     * @param description the description of the Appointment.
     * @param location the location of the Appointment.
     * @param type the type of the Appointment.
     * @param startTime the start time of the Appointment.
     * @param endTime the end time of the Appointment.
     * @param updatedBy the username of the User who is updating the Appointment.
     * @param customerID the ID number of the customer whose Appointment it is.
     * @param userID the User associated with the Appointment.
     * @param contactID the Contact person associated with the Appointment.
     */
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type,
                                         LocalDateTime startTime, LocalDateTime endTime, String updatedBy, int customerID,
                                         int userID, int contactID){
        ZoneId userZone = ZoneId.systemDefault();
        startTime = startTime.atZone(userZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        endTime = endTime.atZone(userZone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        String sqlStatement = "update Appointments SET Title = '" + title + "', Description = '" + description + "', Location = '" +
                                location + "', Type = '" + type + "', Start = '" + Timestamp.valueOf(startTime) + "', End = '" +
                                Timestamp.valueOf(endTime) + "', Last_Update = NOW(), Last_Updated_By = '" + updatedBy +
                                "', Customer_ID = " + customerID + ", User_ID = " + userID + ", Contact_ID = " + contactID +
                                " WHERE Appointment_ID = " + appointmentID;
        Query.makeQuery(sqlStatement);
    }

    //DELETE

    /**
     * The deleteAppointment method deletes the Appointment from the database.
     *
     * @param appointmentID the ID number of the Appointment being deleted.
     */
    public static void deleteAppointment(int appointmentID){
        String sqlStatement = "delete FROM Appointments WHERE Appointment_ID = " + appointmentID;
        Query.makeQuery(sqlStatement);
    }
}
