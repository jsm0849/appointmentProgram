/**
 * The Customer class defines a Customer, who can have appointments scheduled
 * for them.
 *
 * @author Jacob Smith
 */

package model;

import java.time.LocalDateTime;
import dao.CountryDAO;
import dao.DivisionDAO;

public class Customer {
    private int customerID;
    private String customerName;
    private String streetAddress;
    private String postalCode;
    private String phoneNumber;
    private LocalDateTime creationDate;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;
    private int divisionID;
    private String divisionName = "nonexistent";
    private String countryName = "nonexistent";

    /**
     * The constructor generates a new Customer.
     *
     * @param id the Customer's ID number.
     * @param name the Customer's name.
     * @param address the Customer's street address.
     * @param postCode the Customer's postal code.
     * @param phone the Customer's phone number.
     * @param creationDate the date/time the Customer was created.
     * @param createdBy the username of the User who created the Customer.
     * @param lastUpdated the date/time the Customer was last updated.
     * @param lastUpdatedBy the username of the User who last updated the Customer.
     * @param division_ID the ID of the first level division where the Customer resides.
     */
    public Customer(int id, String name, String address, String postCode, String phone,
                    LocalDateTime creationDate, String createdBy, LocalDateTime lastUpdated,
                    String lastUpdatedBy, int division_ID){
        this.customerID = id;
        this.customerName = name;
        this.streetAddress = address;
        this.postalCode = postCode;
        this.phoneNumber = phone;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = division_ID;
        try{
            FirstLevelDivision division = DivisionDAO.getDivision(division_ID);
            this.divisionName = division.getDivisionName();
            Country country = CountryDAO.getCountry(division.getCountryID());
            this.countryName = country.getCountryName();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Mutators
    /**
     * The setCustomerID method sets the Customer's ID number.
     *
     * @param id the Customer's ID number.
     */
    public void setCustomerID(int id){
        this.customerID = id;
    }
    /**
     * The setCustomerName method sets the Customer's name.
     *
     * @param name the Customer's name.
     */
    public void setCustomerName(String name){
        this.customerName = name;
    }
    /**
     * The setStreetAddress method sets the Customer's home address
     *
     * @param address the Customer's home address.
     */
    public void setStreetAddress(String address){
        this.streetAddress = address;
    }
    /**
     * The setPostalCode method sets the Customer's postal code.
     *
     * @param postCode the Customer's postal code.
     */
    public void setPostalCode(String postCode){
        this.postalCode = postCode;
    }
    /**
     * The setPhoneNumber method sets the Customer's phone number.
     *
     * @param phone the Customer's phone number.
     */
    public void setPhoneNumber(String phone){
        this.phoneNumber = phone;
    }
    /**
     * The setCreationDate method sets the date/time the Customer was created.
     *
     * @param creationDate the date/time the Customer was created.
     */
    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }
    /**
     * The setCreatedBy method sets the username of the User who created the Customer.
     *
     * @param user the username of the User who created the Customer.
     */
    public void setCreatedBy(String user){
        this.createdBy = user;
    }
    /**
     * The setLastUpdated method sets the date/time the Customer was last updated.
     *
     * @param lastUpdated the date/time the Customer was last updated.
     */
    public void setLastUpdated(LocalDateTime lastUpdated){
        this.lastUpdated = lastUpdated;
    }
    /**
     * The setLastUpdatedBy method sets the username of the User who last updated the Customer.
     *
     * @param user the username of the User who last updated the Customer.
     */
    public void setLastUpdatedBy(String user){
        this.lastUpdatedBy = user;
    }
    /**
     * The setDivisionID method sets the ID of the FirstLevelDivision where the Customer resides.
     *
     * @param id the ID of the FirstLevelDivision where the Customer resides.
     */
    public void setDivisionID(int id){
        this.divisionID = id;
    }
    /**
     * The setDivisionName method sets the name of the FirstLevelDivision the Customer is based in.
     *
     * @param name the name of the Division the Customer is based in.
     */
    public void setDivisionName(String name){
        this.divisionName = name;
    }
    /**
     * The setCountryName method sets the name of the Country the Customer is based in.
     *
     * @param name the name of the Country the Customer is based in.
     */
    public void setCountryName(String name){
        this.countryName = name;
    }

    //Accessors
    /**
     * The getCustomerID method returns the Customer's ID number.
     *
     * @return the Customer's ID number.
     */
    public int getCustomerID(){
        return customerID;
    }
    /**
     * The getCustomerName method returns the Customer's name.
     *
     * @return the Customer's name.
     */
    public String getCustomerName(){
        return customerName;
    }
    /**
     * The getStreetAddress method returns the Customer's home address.
     *
     * @return the Customer's home address.
     */
    public String getStreetAddress(){
        return streetAddress;
    }
    /**
     * The getPostalCode method returns the Customer's postal code.
     *
     * @return the Customer's postal code.
     */
    public String getPostalCode(){
        return postalCode;
    }
    /**
     * The getPhoneNumber method returns the Customer's phone number.
     *
     * @return the Customer's phone number.
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }
    /**
     * The getCreationDate method returns the date/time the Customer was created.
     *
     * @return the creation date of the Customer.
     */
    public LocalDateTime getCreationDate(){
        return creationDate;
    }
    /**
     * The getCreatedBy method returns the username of the User who created the Customer.
     *
     * @return the username of the User who created the Customer.
     */
    public String getCreatedBy(){
        return createdBy;
    }
    /**
     * The getLastUpdated method returns the date/time the Customer was last updated.
     *
     * @return the date/time the Customer was last updated.
     */
    public LocalDateTime getLastUpdated(){
        return lastUpdated;
    }
    /**
     * The getLastUpdatedBy method returns the username of the User who last updated the Customer.
     *
     * @return the username of the User who last updated the Customer.
     */
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }
    /**
     * The getDivisionID method returns the ID of the FirstLevelDivision where the Customer resides.
     *
     * @return the ID of the FirstLevelDivision where the Customer resides.
     */
    public int getDivisionID(){
        return divisionID;
    }
    /**
     * The getDivisionName method returns the name of the Division the Customer is based in.
     *
     * @return the name of the Division the Customer is based in.
     */
    public String getDivisionName(){
        return divisionName;
    }
    /**
     * The getCountryName method returns the name of the Country the Customer is based in.
     *
     * @return the name of the Country the Customer is based in.
     */
    public String getCountryName(){
        return countryName;
    }

    /**
     * The overridden toString method returns the Customer's ID number, for ComboBox selection purposes.
     *
     * @return the CustomerID.
     */
    @Override
    public String toString(){
        return Integer.toString(getCustomerID());
    }
}