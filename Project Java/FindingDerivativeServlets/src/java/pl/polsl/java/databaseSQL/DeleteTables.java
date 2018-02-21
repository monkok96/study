package pl.polsl.java.databaseSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class responsible for deleting tables in SQL database.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class DeleteTables {

    /**
     * Method responsible for deleting two tables - DerivativeHistory and Users.
     */
    public void deleteTables() {
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/derivative", "project", "project");
            Statement statement = con.createStatement();
            statement.executeUpdate("DROP TABLE DERIVATIVEHISTORY");
            statement.executeUpdate("DROP TABLE USERS");
            System.out.println("Data removed.");
        } catch (SQLException sqle) {
            System.err.println("SQL exception: " + sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println("ClassNotFound exception: " + cnfe.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqle) {
                System.err.println("SQL exception: " + sqle.getMessage());
            }
        }
    }

    /**
     * Main method of the class. It invokes method responsible of deleting
     * tables.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        DeleteTables deleteTables = new DeleteTables();
        deleteTables.deleteTables();
    }
}
