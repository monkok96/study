package pl.polsl.java.view;

/**
 * Class representing the View from the MVC pattern. All it does is showing data
 * on the screen.
 *
 * @author Monika Kokot
 * @version 3.0
 */
public class View {

    /**
     * Method shows an exception that occured on a screen.
     *
     * @param e exception to show
     */
    public void showException(Exception e) {
        System.out.println("An exception occured: " + e.toString());
    }

    /**
     * Methos used to show command received from the server.
     *
     * @param cmd command to show on the screen
     */
    public void showCommand(String cmd) {
        System.out.println(cmd);
    }
}
