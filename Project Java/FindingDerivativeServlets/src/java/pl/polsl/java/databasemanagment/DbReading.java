package pl.polsl.java.databasemanagment;

import java.util.ArrayList;
import javafx.util.Pair;
import pl.polsl.java.databaseSQL.SelectData;

/**
 * Class implementing interface responsile of reading data from database.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class DbReading implements DataBaseReading {

    /**
     * Method checking if user whose parameters are in "currentUser" variable is
     * existing in database that is placed in text file in "path".
     *
     * @param path the path of file in which the database is placed
     * @param currentUser single record in database describing user
     * @return true if the database cotains user and false if it does not
     */
    @Override
    public boolean doesUserExist(String path, UserParameters currentUser) {
        SelectData selectData = new SelectData();
        if (selectData.selectDataForLogging(currentUser)) {
            return true;
        }
        return false;

    }

    /**
     * Method to get and return history of counting of the specific user. Not to
     * use in txt file kind of database!
     *
     * @param userParams single record in database representing user
     * @return list of polynomials and its derivatives
     */
    @Override
    public ArrayList<Pair<String, String>> userHistory(UserParameters userParams) {
        SelectData selectData = new SelectData();
        return selectData.selectHistoryData(userParams);

    }

}
