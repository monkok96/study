package pl.polsl.java.databasemanagment;

import pl.polsl.java.databaseSQL.InsertData;
import pl.polsl.java.databaseSQL.SelectData;

/**
 * Class implementing interface responsible of writing data to database.
 *
 * @author Monka Kokot
 * @version 5.0
 */
public class DbWriting implements DataBaseWriting {

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

        SelectData selectData = new SelectData();
        if (selectData.selectDataForRegistration(currentUser) == true) {
            return false;
        }
        InsertData insertData = new InsertData();
        insertData.insertUser(currentUser);

        return true;
    }

    /**
     * Method to update history of counting. It write in database currenty found
     * derivative. Not to use in file kind of database!
     *
     * @param inPolynomial input polynomial
     * @param outPolynomial derivative of the polynomial
     * @param currentUser single record in database representing user
     */
    @Override
    public void updateHistory(String inPolynomial, String outPolynomial, UserParameters currentUser) {
        InsertData insertData = new InsertData();
        insertData.insertHistory(inPolynomial, outPolynomial, currentUser);
    }
}
