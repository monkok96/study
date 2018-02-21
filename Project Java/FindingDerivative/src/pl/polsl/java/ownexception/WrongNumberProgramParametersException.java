package pl.polsl.java.ownexception;

/**
 * Class representing own exception which shall occur when someone want to
 * insert inproper number of program parameters.
 *
 * @author Monika Kokot
 * @version 2.0
 */
public class WrongNumberProgramParametersException extends Exception {

    /**
     * The exception constructor. It is needed so when the exception is cought,
     * the proper message can be shown.
     *
     * @param message the message to show when the exception occurs
     */
    public WrongNumberProgramParametersException(String message) {
        super(message);
    }
}
