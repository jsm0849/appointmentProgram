/**
 * The CountryDAO class implements data access to read Countries from the database.
 *
 * @author Jacob Smith
 */

package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
    /**
     * The getCountry method returns a Country if it was found in the database.
     *
     * @param countryID the ID number of the Country.
     * @return the Country if it was found in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static Country getCountry(int countryID) throws SQLException, Exception{
        String sqlStatement = "select * FROM countries WHERE Country_ID  = " + countryID;
        Query.makeQuery(sqlStatement);
        Country countryResult;
        ResultSet result = Query.getResult();
        while(result.next()){
            String name = result.getString("Country");
            countryResult = new Country(countryID, name);
            return countryResult;
        }
        return null;
    }

    /**
     * The getAllCountries method returns an ObservableList of the Countries in the database.
     * @return an ObservableList of the Countries in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Country> getAllCountries() throws SQLException, Exception{
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String sqlStatement = "select * from countries";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int countryID = result.getInt("Country_ID");
            String name = result.getString("Country");
            Country countryResult = new Country(countryID, name);
            allCountries.add(countryResult);
        }
        return allCountries;
    }
}
