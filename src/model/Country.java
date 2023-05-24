/**
 * The Country class defines a Country for the scheduling software.
 *
 * @author Jacob Smith
 */

package model;

public class Country {
    private int country_ID;
    private String countryName;

    /**
     * The constructor generates a new Country object.
     *
     * @param id the Country's ID number.
     * @param name the Country's name.
     */
    public Country(int id, String name){
        this.country_ID = id;
        this.countryName = name;
    }

    //Mutators
    /**
     * The setCountryID method sets the ID number of the Country.
     *
     * @param id the ID number of the Country.
     */
    public void setCountryID(int id){
        this.country_ID = id;
    }
    /**
     * The setCountryName method sets the name of the Country.
     *
     * @param name the name of the Country.
     */
    public void setCountryName(String name){
        this.countryName = name;
    }

    //Accessors
    /**
     * The getCountryID method returns the ID number of the Country.
     *
     * @return the ID number of the Country.
     */
    public int getCountryID(){
        return country_ID;
    }
    /**
     * The getCountryName method returns the name of the Country.
     *
     * @return the name of the Country.
     */
    public String getCountryName(){
        return countryName;
    }
    @Override
    public String toString(){
        return countryName;
    }
}
