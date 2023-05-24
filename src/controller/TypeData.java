/**
 * The TypeData class is a helper class for the Reports page that stores the number of occurrences of each type of
 * appointment.
 *
 * @author Jacob Smith
 */

package controller;

public class TypeData {
    private String type;
    private int numAppointments;

    /**
     * The constructor generates a new TypeData object.
     *
     * @param type the type of Appointment.
     * @param numAppts the number of Appointments of a certain type.
     */
    public TypeData(String type, int numAppts){
        this.type = type;
        this.numAppointments = numAppts;
    }

    //Mutators
    /**
     * The setType method sets the type of the Appointment in question.
     *
     * @param type the type of Appointment.
     */
    public void setType(String type){
        this.type = type;
    }
    /**
     * The setNumAppointments method sets the number of Appointments of this type.
     *
     * @param numAppts the number of Appointments of this type.
     */
    public void setNumAppointments(int numAppts){
        this.numAppointments = numAppts;
    }

    //Accessors
    /**
     * The getType method returns the type of Appointment.
     *
     * @return the type of Appointment.
     */
    public String getType(){
        return type;
    }
    /**
     * The getNumAppointments method returns the number of Appointments of this type.
     *
     * @return the number of Appointments of this type.
     */
    public int getNumAppointments(){
        return numAppointments;
    }

    //Other Methods
    /**
     * The addAppointment method increments the number of Appointments of this type.
     */
    public void addAppointment(){
        numAppointments++;
    }
}
