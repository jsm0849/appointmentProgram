/**
 * The Query class defines querying the database and getting a result set.
 *
 * @author Jacob Smith
 */

package dao;

import java.sql.Statement;
import java.sql.ResultSet;

public class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet results;

    /**
     * The makeQuery method makes a query of the database.
     *
     * @param q the query statement.
     */
    public static void makeQuery(String q){
        query = q;
        try{
            statement = JDBC.getConnection().createStatement();
            if(query.toLowerCase().startsWith("select"))
                results=statement.executeQuery(q);
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                statement.executeUpdate(q);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * The getResult method returns the set of results from the Query.
     *
     * @return the results of the Query.
     */
    public static ResultSet getResult(){
        return results;
    }
}
