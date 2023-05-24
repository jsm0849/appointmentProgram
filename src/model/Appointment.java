/**
 * The Appointment class defines an Appointment.
 *
 * @author Jacob Smith
 */

package model;

import java.time.LocalDateTime;
import dao.ContactDAO;

public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDT;
    private LocalDateTime endDT;
    private LocalDateTime creationDate;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;

    /**
     * The constructor generates a new Appointment.
     *
     * @param id the ID number of the Appointment.
     * @param title the title of the Appointment.
     * @param description the description of the Appointment.
     * @param location the location of the Appointment.
     * @param type the type of Appointment.
     * @param startDT the starting date/time of the Appointment.
     * @param endDT the ending date/time of the Appointment.
     * @param creationDate the date/time the Appointment was created.
     * @param createdBy the username of the User who created the Appointment.
     * @param lastUpdated the date/time the Appointment was last updated.
     * @param lastUpdatedBy the username of the User who last updated the Appointment.
     * @param customer_ID the ID of the Customer associated w/ the Appointment.
     * @param user_ID the ID of the User associated w/ the Appointment.
     * @param contact_ID the ID of the Contact associated w/ the Appointment.
     */
    public Appointment(int id, String title, String description, String location, String type,
                       LocalDateTime startDT, LocalDateTime endDT, LocalDateTime creationDate,
                       String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy,
                       int customer_ID, int user_ID, int contact_ID){
        this.appointmentID = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDT = startDT;
        this.endDT = endDT;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customer_ID;
        this.userID = user_ID;
        this.contactID = contact_ID;
        try{
            Contact contactPerson = ContactDAO.getContact(contact_ID);
            this.contactName = contactPerson.getContactName();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Appointment(){}
    //Mutators
    /**
     * The setAppointmentID method sets the Appointment's ID number.
     *
     * @param id the Appointment's ID number.
     */
    public void setAppointmentID(int id){
        this.appointmentID = id;
    }
    /**
     * The setTitle method sets the title of the Appointment.
     *
     * @param title the title of the Appointment.
     */
    public void setTitle(String title){
        this.title = title;
    }
    /**
     * The setDescription method sets the Appointment's description.
     *
     * @param desc the description of the Appointment.
     */
    public void setDescription(String desc){
        this.description = desc;
    }
    /**
     * The setLocation method sets the Appointment's location.
     *
     * @param loc the location of the Appointment.
     */
    public void setLocation(String loc){
        this.location = loc;
    }
    /**
     * The setType method sets the Appointment's type.
     *
     * @param type the type of Appointment.
     */
    public void setType(String type){
        this.type = type;
    }
    /**
     * The setStartDT method sets the start time/date of the Appointment.
     *
     * @param start the start date/time of the Appointment.
     */
    public void setStartDT(LocalDateTime start){
        this.startDT = start;
    }
    /**
     * The setEndDT method sets the end date/time of the Appointment.
     *
     * @param end the end date/time of the Appointment.
     */
    public void setEndDT(LocalDateTime end){
        this.endDT = end;
    }
    /**
     * The setCreationDate method sets the date/time the Appointment was created.
     *
     * @param creationDate the date/time the Appointment was created.
     */
    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }
    /**
     * The setCreatedBy method sets the username of the User who created the Appointment.
     *
     * @param user the username of the User who created the Appointment.
     */
    public void setCreatedBy(String user){
        this.createdBy = user;
    }
    /**
     * The setLastUpdated method sets the date/time the Appointment was last updated.
     *
     * @param lastUpdated the date/time the Appointment was last updated.
     */
    public void setLastUpdated(LocalDateTime lastUpdated){
        this.lastUpdated = lastUpdated;
    }
    /**
     * The setLastUpdatedBy method sets the username of the User who last updated the Appointment.
     *
     * @param user the username of the User who last updated the Appointment.
     */
    public void setLastUpdatedBy(String user){
        this.lastUpdatedBy = user;
    }
    /**
     * The setCustomerID method sets the ID of the Customer associated with the Appointment.
     *
     * @param id the ID of the Customer associated with the Appointment.
     */
    public void setCustomerID(int id){
        this.customerID = id;
    }
    /**
     * The setUserID method sets the ID of the User associated with the Appointment.
     *
     * @param id the ID of the User associated with the Appointment.
     */
    public void setUserID(int id){
        this.userID = id;
    }
    /**
     * The setContactID method sets the ID of the Contact associated with the Appointment.
     *
     * @param id the ID of the Contact associated with the Appointment.
     */
    public void setContactID(int id){
        this.contactID = id;
    }
    /**
     * The setContactName method sets the name of the Contact person.
     *
     * @param contactName the name of the Contact person for the Appointment.
     */
    public void setContactName(String contactName){
        this.contactName = contactName;
    }

    //Accessors
    /**
     * The getAppointmentID method returns the Appointment's ID number.
     *
     * @return the Appointment's ID number.
     */
    public int getAppointmentID(){
        return appointmentID;
    }
    /**
     * The getTitle method returns the title of the Appointment.
     *
     * @return the title of the Appointment.
     */
    public String getTitle(){
        return title;
    }
    /**
     * The getDescription method returns the Appointment's description.
     *
     * @return the description of the Appointment.
     */
    public String getDescription(){
        return description;
    }
    /**
     * The getLocation method returns the Appointment's location.
     *
     * @return the location of the Appointment.
     */
    public String getLocation(){
        return location;
    }
    /**
     * The getType method returns the Appointment's type.
     *
     * @return the type of Appointment.
     */
    public String getType(){
        return type;
    }
    /**
     * The getStartDT method returns the start time/date of the Appointment.
     *
     * @return the start date/time of the Appointment.
     */
    public LocalDateTime getStartDT(){
        return startDT;
    }
    /**
     * The getEndDT method returns the end date/time of the Appointment.
     *
     * @return the end date/time of the Appointment.
     */
    public LocalDateTime getEndDT(){
        return endDT;
    }
    /**
     * The getCreationDate method returns the date/time the Appointment was created.
     *
     * @return the creation date of the Appointment.
     */
    public LocalDateTime getCreationDate(){
        return creationDate;
    }
    /**
     * The getCreatedBy method returns the username of the User who created the Appointment.
     *
     * @return the username of the User who created the Appointment.
     */
    public String getCreatedBy(){
        return createdBy;
    }
    /**
     * The getLastUpdated method returns the date/time the Appointment was last updated.
     *
     * @return the date/time the Appointment was last updated.
     */
    public LocalDateTime getLastUpdated(){
        return lastUpdated;
    }
    /**
     * The getLastUpdatedBy method returns the username of the User who last updated the Appointment.
     *
     * @return the username of the User who last updated the Appointment.
     */
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }
    /**
     * The getCustomerID method returns the ID of the Customer associated with the Appointment.
     *
     * @return the ID of the Customer associated with the Appointment.
     */
    public int getCustomerID(){
        return customerID;
    }
    /**
     * The getUserID method returns the ID of the User associated with the Appointment.
     *
     * @return the ID of the User associated with the Appointment.
     */
    public int getUserID(){
        return userID;
    }
    /**
     * The getContactID method returns the ID of the Contact associated with the Appointment.
     *
     * @return the ID of the Contact associated with the Appointment.
     */
    public int getContactID(){
        return contactID;
    }
    /**
     * The getContactName method returns the Contact person's name.
     *
     * @return the name of the Contact person for the Appointment.
     */
    public String getContactName(){
        return contactName;
    }
}
