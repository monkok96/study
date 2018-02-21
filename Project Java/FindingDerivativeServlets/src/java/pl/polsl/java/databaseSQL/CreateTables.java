package pl.polsl.java.databaseSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class responsible for creating tables in SQL database.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class CreateTables {

    /**
     * Method responsible for creating two tables one representing Users and
     * second representing of derivative finding history. They ae in relation
     * 1:n.
     */
    public void createTables() {
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/derivative", "project", "project");
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE Users (id_user INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, username VARCHAR(50), password VARCHAR(50) )");
            statement.executeUpdate("CREATE TABLE DerivativeHistory (id_der INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, id_user INTEGER, polynomial VARCHAR(255), derivative VARCHAR(255), FOREIGN KEY (id_user) REFERENCES Users(id_user) )");

            System.out.println("Table created");
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
     * Main method of the class. It invokes method which create tables.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        CreateTables createTables = new CreateTables();
        createTables.createTables();
    }
}
