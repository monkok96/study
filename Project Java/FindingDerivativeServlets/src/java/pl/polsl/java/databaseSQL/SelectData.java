package pl.polsl.java.databaseSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pl.polsl.java.databasemanagment.UserParameters;
import javafx.util.Pair;

/**
 * Class repsonsible for selecting data from tables in database.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class SelectData {

    /**
     * Method selecting user of specific username, invoking method which finds
     * such user in database.
     *
     * @param userParams signle record reperesenting user
     * @return true if user exists in database; false if they don't
     */
    public boolean selectDataForRegistration(UserParameters userParams) {

        String sql = "SELECT * FROM Users WHERE Username='" + userParams.getUName() + "'";
        return selectData(sql);
    }

    /**
     * Method selecting user of specific username and password, invoking method
     * which finds such user in database.
     *
     * @param userParams signle record reperesenting user
     * @return true if user exists in database; false if they don't
     */
    public boolean selectDataForLogging(UserParameters userParams) {
        String sql = "SELECT * FROM Users WHERE Username='" + userParams.getUName() + "' AND Password='" + userParams.getUPassword() + "'";
        return selectData(sql);
    }

    /**
     * Method executing an sql statement to select data from database.
     *
     * @param sql statement to execute
     * @return true if user exsts in database; false if they don't
     */
    public boolean selectData(String sql) {
        Connection con = null;
        boolean isCurrentUserExisting = false;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/derivative", "project", "project");
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                isCurrentUserExisting = true;
            }
            rs.close();
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
            return isCurrentUserExisting;
        }
    }

    /**
     * Method selecting all polynomials and its derivatives belonging to
     * specific user from database.
     *
     * @param userParams signel record representing user
     * @return list of pairs of strings: polynomial and its derivative
     */
    public ArrayList<Pair<String, String>> selectHistoryData(UserParameters userParams) {
        Connection con = null;
        ResultSet rsOut = null;
        ArrayList<Pair< String, String>> polynomialsPairs = new ArrayList<>();
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
            String sql = "SELECT d.polynomial, d.derivative FROM DerivativeHistory d, Users u WHERE u.ID_User=" + usrId.toString() + " AND d.ID_User=u.ID_User";
            rsOut = statement.executeQuery(sql);
            while (rsOut.next()) {
                Pair< String, String> tmpPair = new Pair<>(rsOut.getString("polynomial"), rsOut.getString("derivative"));
                polynomialsPairs.add(tmpPair);
            }

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
            return polynomialsPairs;
        }
    }
}
