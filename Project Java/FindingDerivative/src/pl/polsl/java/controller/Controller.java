package pl.polsl.java.controller;

import java.io.*;
import java.util.*;
import pl.polsl.java.model.*;
import pl.polsl.java.view.View;
import pl.polsl.java.ownexception.WrongNumberProgramParametersException;

/**
 * Class representing the Controller from the MVC pattern. It collects the input
 * data and passes it over to the DerativateFinding or the View.
 *
 * @author Monika Kokot
 * @version 2.0
 */
public class Controller {

    /**
     * Object represendig the Model from the MVC pattern. It is called to find
     * the derivative.
     */
    private DerativateFinding theModel;
    /**
     * Object representing the polynomial of which the derivative must be found.
     */
    private final Polynomial thePolynomial;
    /**
     * Object representing the View from the MVC pattern.
     */
    private final View theView;

    /**
     * The Controller constructor. It creates the input polynomial and the View.
     * It reads a file from the input and passes it over to the Model.
     *
     * @param args the command line arguments
     * @throws WrongNumberProgramParametersException
     * @throws FileNotFoundException
     */
    public Controller(String[] args) throws WrongNumberProgramParametersException, FileNotFoundException {
        theView = new View();
        thePolynomial = new Polynomial();
        try {
            validateParameters(args);
            Scanner inFile = new Scanner(new File(args[0]));
            thePolynomial.readInput(inFile);
        } catch (FileNotFoundException | NoSuchElementException | WrongNumberProgramParametersException e) {
            theView.showException(e);
            throw e;
        } catch (final Exception e) {
            theView.showException(e);
            throw e;
        }
    }

    /**
     * Method calling the View function to show data on the screen. It also
     * creates the DerativateFinding, so that we can find a derivative.
     */
    public void startProgram() {
        try {
            theView.showFunction(thePolynomial.getPolynomialString(), "Input function: ");
            theModel = new DerativateFinding(thePolynomial);
            Polynomial foundDerivative = theModel.findDerivative();
            theView.showFunction(foundDerivative.getPolynomialString(), "Found derivative: ");
        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NegativeArraySizeException e) {
            theView.showException(e);
        } catch (final Exception e) {
            theView.showException(e);
        }

    }

    /**
     * Method checking if the user passed proper number of the command line
     * arguments. There should be only one argument (a file with input data).
     *
     * @param args the command line arguments
     * @throws WrongNumberProgramParametersException
     */
    private void validateParameters(String args[]) throws WrongNumberProgramParametersException {
        if (args.length != 1) {
            throw new WrongNumberProgramParametersException("You have to pass one argument to the command line!");
        }
    }
}
