/**
 * The AppointmentHolder class is a helper class which holds a particular instance of itself. The selected Appointment
 * was selected on the main menu and will be accessed by the update appointment menu.
 *
 * @author Jacob Smith
 */

package controller;

import model.Appointment;

public final class AppointmentHolder {
    private Appointment selectedAppointment;
    private final static AppointmentHolder INSTANCE = new AppointmentHolder();

    /**
     * The constructor is private so other classes can't create more instances of it.
     */
    private AppointmentHolder() {}
    /**
     * The getInstance method returns the instance that all the menus can reference.
     *
     * @return the instance of the AppointmentHolder.
     */
    public static AppointmentHolder getInstance() {
        return INSTANCE;
    }
    /**
     * The setAppointment method sets the Appointment for the instance of AppointmentHolder.
     *
     * @param appt
     */
    public void setAppointment(Appointment appt) {
        this.selectedAppointment = appt;
    }
    /**
     * The getAppointment method returns the Appointment held by the instance of AppointmentHolder.
     *
     * @return
     */
    public Appointment getAppointment() {
        return this.selectedAppointment;
    }
}
