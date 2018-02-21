package pl.polsl.java.main;

import pl.polsl.java.controller.Controller;

/**
 * Class reading the program parameter (text file) and passing it over to the
 * Controller.
 *
 * @author Monika Kokot
 * @version 2.0
 */
public class Main {

    /**
     * Main method creating the Controller and calling its method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Controller theController = new Controller(args);
            theController.startProgram();
        } catch (Exception e) {
            System.out.println("An exception occured: " + e.toString());
        }
    }
}
