/**
 *The FirstLevelDivision class defines a first level division (state/province) for the scheduling software.
 *
 * @author Jacob Smith
 */

package model;

public class FirstLevelDivision {
    private int division_ID;
    private String divisionName;
    private int country_ID; //the ID number of the Country the Division is a part of.

    /**
     * The constructor generates a new FirstLevelDivision.
     *
     * @param division_ID the ID number of the FirstLevelDivision.
     * @param name the name of the FirstLevelDivision.
     * @param country_ID the ID number of the Country the Division is a part of.
     */
    public FirstLevelDivision(int division_ID, String name, int country_ID){
        this.division_ID = division_ID;
        this.divisionName = name;
        this.country_ID = country_ID;
    }

    //Mutators
    /**
     * The setDivisionID method sets the ID number of the FirstLevelDivision.
     *
     * @param id the ID number of the FirstLevelDivision.
     */
    public void setDivisionID(int id){
        this.division_ID = id;
    }
    /**
     * The setDivisionName method sets the name of the FirstLevelDivision.
     *
     * @param name the name of the FirstLevelDivison.
     */
    public void setDivisionName(String name){
        this.divisionName = name;
    }
    /**
     * The setCountryID method sets the ID number of the Country the Division is a part of.
     *
     * @param id the ID number of the Country the FirstLevelDivision is a part of.
     */
    public void setCountryID(int id){
        this.country_ID = id;
    }

    //Accessors
    /**
     * The getDivisionID method returns the ID number of the FirstLevelDivision.
     *
     * @return the ID number of the FirstLevelDivision.
     */
    public int getDivisionID(){
        return division_ID;
    }
    /**
     * The getDivisionName method returns the name of the FirstLevelDivision.
     *
     * @return the name of the FirstLevelDivision.
     */
    public String getDivisionName(){
        return divisionName;
    }
    /**
     * The getCountryID method returns the ID number of the Country the Division is a part of.
     *
     * @return the ID number of the Country the FirstLevelDivision is a part of.
     */
    public int getCountryID(){
        return country_ID;
    }
    @Override
    public String toString(){
        return divisionName;
    }
}
