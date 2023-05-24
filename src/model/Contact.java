/**
 * The Contact class defines what a Contact person is for the scheduling software.
 *
 * @author Jacob Smith
 */

package model;

public class Contact {
    private int contact_ID;
    private String contactName;
    private String email;

    /**
     * The constructor generates a new Contact.
     *
     * @param id the ID number of the Contact.
     * @param name the name of the Contact.
     * @param email the email address of the Contact.
     */
    public Contact(int id, String name, String email){
        this.contact_ID = id;
        this.contactName = name;
        this.email = email;
    }
    public Contact(){}

    //Mutators
    /**
     * The setContactID method sets the ID number of the Contact.
     *
     * @param id the ID number of the Contact.
     */
    public void setContactID(int id){
        this.contact_ID = id;
    }
    /**
     * The setContactName method sets the name of the Contact.
     *
     * @param name the Name of the Contact.
     */
    public void setContactName(String name){
        this.contactName = name;
    }
    /**
     * The setEmail method sets the email address of the Contact.
     *
     * @param email the email address of the Contact.
     */
    public void setEmail(String email){
        this.email = email;
    }

    //Accessors
    /**
     * The getContactID method returns the ID number of the Contact.
     *
     * @return the ID number of the Contact.
     */
    public int getContactID(){
        return contact_ID;
    }
    /**
     * The getContactName method returns the name of the Contact.
     *
     * @return the name of the Contact.
     */
    public String getContactName(){
        return contactName;
    }
    /**
     * The getEmail method returns the Contact's email address.
     *
     * @return the email address of the Contact.
     */
    public String getEmail(){
        return email;
    }

    /**
     * The overridden toString method returns the Contact's name, for ComboBox selection purposes.
     *
     * @return the name of the Contact as a String.
     */
    @Override
    public String toString(){
        return getContactName();
    }
}
