package pl.polsl.java.databasemanagment;

/**
 * Interface used to control writing data to database.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public interface DataBaseWriting {

    /**
     * Method checking if user whose parameters are in "currentUser" variable is
     * existing in database that is placed in file in "path". If not, the user
     * can be placed in this file.
     *
     * @param path the path of file in which the database is placed
     * @param currentUser single record in database describing user
     * @return true if the user exists in database so they cannot be added or no
     * if they do not exist and have been succesfully added.
     */
    public boolean canWriteUser(String path, UserParameters currentUser);

    /**
     * Method to update history of counting. It write in database currenty found
     * derivative. Not to use in file kind of database!
     *
     * @param inPolynomial input polynomial
     * @param outPolynomial derivative of the polynomial
     * @param currentUser single record in database representing user
     */
    public void updateHistory(String inPolynomial, String outPolynomial, UserParameters currentUser);
}
