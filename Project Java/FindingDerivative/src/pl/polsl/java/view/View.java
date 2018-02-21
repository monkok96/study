package pl.polsl.java.view;

/**
 * Class representing the View from the MVC pattern. All it does is showing data
 * on the screen.
 *
 * @author Monika Kokot
 * @version 2.0
 */
public class View {

    /**
     * Method showing passed polynomial function message on a screen.
     *
     * @param functionMessage the formula od the polynomial
     * @param functionTitle text to show just before showing the function.
     */
    public void showFunction(String functionMessage, String functionTitle) {
        System.out.println(functionTitle);
        System.out.println(functionMessage);
    }

    /**
     * Method shows an exception that occured on a screen.
     *
     * @param e exception to show
     */
    public void showException(Exception e) {
        System.out.println("An exception occured: " + e.toString());
    }
}
