package pl.polsl.java.databasemanagment;

/**
 * Class storing user parameters. Each object of this type will represent single
 * user.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class UserParameters {

    /**
     * Username of the user.
     */
    private String uName;
    /**
     * Password of th user.
     */
    private String UPassword;

    /**
     * Constructor setting passed parameters to the user.
     *
     * @param uName of the user.
     * @param UPassword of the user.
     */
    public UserParameters(String uName, String UPassword) {
        this.uName = uName;
        this.UPassword = UPassword;
    }

    /**
     * Non-argument constructor.
     */
    public UserParameters() {
    }

    /**
     * Method setting username to the one that is passed.
     *
     * @param uName to set to current user.
     */
    public void setUName(String uName) {
        this.uName = uName;
    }

    /**
     * Method setting password to the one that is passed.
     *
     * @param uPassword to set to current user.
     */
    public void setUPassword(String uPassword) {
        this.UPassword = uPassword;
    }

    /**
     * Method to get username of current user.
     *
     * @return username
     */
    public String getUName() {
        return this.uName;
    }

    /**
     * Method to get password of current user.
     *
     * @return password
     */
    public String getUPassword() {
        return this.UPassword;
    }

}
