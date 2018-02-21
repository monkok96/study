package pl.polsl.java.databasemanagment;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 * Interface used to control reading data from database.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public interface DataBaseReading {

    /**
     * Method checking if user whose parameters are in "currentUser" variable is
     * existing in database that is placed in file in "path".
     *
     * @param path the path of file in which the database is placed
     * @param currentUser single record in database describing user
     * @return true if the database cotains user and false if it does not
     */
    public boolean doesUserExist(String path, UserParameters currentUser);

    /**
     * Method to get and return history of counting of the specific user. Not to
     * use in txt file kind of database!
     *
     * @param userParams single record in database representing user
     * @return list of polynomials and its derivatives
     */
    public ArrayList<Pair<String, String>> userHistory(UserParameters userParams);
}
