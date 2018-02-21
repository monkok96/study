package pl.polsl.java.databasemanagment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.util.Pair;

/**
 * Class implementing interface responsible of reading data from database.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class TxtFileReading implements DataBaseReading {

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

        File inFile;
        try {
            inFile = new File(path);
            Scanner scanner = new Scanner(inFile);
            String scannerUser, scannerPassword;
            while (scanner.hasNext()) {
                scannerUser = scanner.next();
                scannerPassword = scanner.next();
                if (scannerUser.equals(currentUser.getUName()) && scannerPassword.equals(currentUser.getUPassword())) {
                    return true;
                }
            }
        } catch (FileNotFoundException | NoSuchElementException e) {
            System.out.println("An error occured while opening txt file database.");
        } finally {
            return false;
        }
    }

    /**
     * Method to get and return history of counting of the specific user. Not to
     * use in txt file kind of database!
     *
     * @param userParams single record in database representing user
     * @return list of polynomials and its derivatives
     * @deprecated
     */
    @Deprecated
    @Override
    public ArrayList<Pair<String, String>> userHistory(UserParameters userParams) {
        return null;
    }

}
