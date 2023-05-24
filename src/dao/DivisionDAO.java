/**
 * The DivisionDAO class implements the data access to read from the First_Level_Divisions
 * table in the database.
 *
 * @author Jacob Smith
 */

package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {
    /**
     * The getDivision method returns a first level division if it was found in the database.
     *
     * @param divisionID the ID number of the FirstLevelDivision.
     * @return the FirstLevelDivision if it was found to be in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static FirstLevelDivision getDivision(int divisionID) throws SQLException, Exception{
        String sqlStatement = "select * FROM first_level_divisions WHERE Division_ID = " + divisionID;
        Query.makeQuery(sqlStatement);
        FirstLevelDivision divisionResult;
        ResultSet result = Query.getResult();
        while(result.next()){
            String name = result.getString("Division");
            int countryID = result.getInt("Country_ID");
            divisionResult = new FirstLevelDivision(divisionID, name, countryID);
            return divisionResult;
        }
        return null;
    }

    /**
     * The getAllDivisions method returns an ObservableList of all FirstLevelDivisions in the database.
     *
     * @return an ObservableList of all FirstLevelDivisions in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions() throws SQLException, Exception{
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        String sqlStatement = "select * from First_Level_Divisions";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();
        while(result.next()){
            int divisionID = result.getInt("Division_ID");
            String name = result.getString("Division");
            int countryID = result.getInt("Country_ID");
            FirstLevelDivision divisionResult = new FirstLevelDivision(divisionID, name, countryID);
            allDivisions.add(divisionResult);
        }
        return allDivisions;
    }
}
