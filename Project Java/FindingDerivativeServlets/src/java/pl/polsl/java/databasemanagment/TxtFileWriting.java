package pl.polsl.java.databasemanagment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class implementing interface responsible of writing data to database.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class TxtFileWriting implements DataBaseWriting {

    /**
     * Method checking if user whose parameters are in "currentUser" variable is
     * existing in database that is placed in txt file in "path". If not, the
     * user can be placed in this file.
     *
     * @param path the path of txt file in which the database is placed
     * @param currentUser single record in database describing user
     * @return true if the user exists in database so they cannot be added or no
     * if they do not exist and have been succesfully added.
     */
    @Override
    public boolean canWriteUser(String path, UserParameters currentUser) {
        try {
            File dbFile = new File(path);
            Scanner scanner = new Scanner(dbFile);
            String scannerUser;
            while (scanner.hasNext()) {
                scannerUser = scanner.next();
                scanner.next();
                if (scannerUser.equals(currentUser.getUName())) {
                    return false;
                }
            }
            try (FileWriter fw = new FileWriter(dbFile, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter outPr = new PrintWriter(bw)) {
                outPr.println(currentUser.getUName() + " " + currentUser.getUPassword());
                outPr.close();

            } catch (IOException e) {
                System.out.println("An error occured while writing to the file.");
            }
        } catch (FileNotFoundException | NoSuchElementException e) {
            System.out.println("An error occured while opening the file.");
        } finally {
            return true;
        }
    }

    /**
     * Method to update history of counting. It write in database currenty found
     * derivative. Not to use in file kind of database!
     *
     * @param inPolynomial input polynomial
     * @param outPolynomial derivative of the polynomial
     * @param currentUser single record in database representing user
     * @deprecated
     */
    @Deprecated
    @Override
    public void updateHistory(String inPolynomial, String outPolynomial, UserParameters currentUser) {

    }
}
