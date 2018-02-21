package pl.polsl.java.databaseSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pl.polsl.java.databasemanagment.UserParameters;

/**
 * Class respnsible for inserting data into SQL database.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class InsertData {

    /**
     * Method inserting data to tables in database. It inserts new users, which
     * just created an account.
     *
     * @param userParams single record in database representing user
     */
    public void insertUser(UserParameters userParams) {
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/derivative", "project", "project");
            Statement statement = con.createStatement();
            String sql = "INSERT INTO Users(Username, Password) VALUES ('" + userParams.getUName() + "', '" + userParams.getUPassword() + "')";
            statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            System.out.println("Data inserted");
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
     * Method inserting data to table representing derivative history. It
     * inserts derivative that specific user just found.
     *
     * @param polynomial the input polynomial
     * @param derivative dervative of the polynomial
     * @param userParams single record representing user
     */
    public void insertHistory(String polynomial, String derivative, UserParameters userParams) {
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/derivative", "project", "project");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID_User FROM Users WHERE Username='" + userParams.getUName() + "'");
            Integer usrId = 0;
            if (rs.next()) {
                usrId = rs.getInt(1);
            }
            rs.close();
            String sql = "INSERT INTO DerivativeHistory(ID_User, Polynomial, Derivative) VALUES (" + usrId.toString() + ", '" + polynomial + "', '" + derivative + "')";
            statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            System.out.println("Data inserted");
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
}
