package pl.polsl.java.model;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class representing a polynomial function. It has two components: one of them
 * is an arrya list which contain the function coefficients and the other is the
 * polynomial degree. Class has methods enabling to set and get those two
 * components.
 *
 * @author Monika Kokot
 * @version 3.0
 */
public class Polynomial {

    /**
     * ArrayList of double's representing the coefficients of the polynomial
     */
    ArrayList<Double> coefficients;
    /**
     * The degree of the polynomial.
     */
    private int polynomialDegree;

    /**
     * Contructor which sets the proper degree and creates the coefficients
     * ArrayList of a proper size (which is always an incremented degree). It
     * fills this array with zeros.
     *
     * @param pDegree the polynomial degree that should be stored in this class
     * component.
     */
    public Polynomial(int pDegree) {
        polynomialDegree = pDegree;
        coefficients = new ArrayList<>(polynomialDegree + 1);
        for (int i = 0; i < polynomialDegree + 1; ++i) {
            coefficients.add(0.0);
        }
    }

    /**
     * Non-arguments constructor. It creates an empty polynomial object.
     */
    public Polynomial() {
        polynomialDegree = -1;
        coefficients = null;
    }

    /**
     * Method to convert the polynomial formula from separate components to one
     * String.
     *
     * @return the polynomial formula placed in one Strng
     */
    public String getPolynomialString() {
        String message = "";
        int i = 0;
        for (Double e : coefficients) { //forEach usage
            if (e != 0) {
                if (message.isEmpty()) {
                    message = e.toString() + "x^" + i + message;
                } else {
                    message = e.toString() + "x^" + i + " + " + message;
                }
            }
            ++i;
        }
        if (message.equals("")) {
            message = "0";
        }
        return message;
    }

    /**
     * Method used to get the polymonial degree from the outside.
     *
     * @return degree of a polymonial
     */
    public int getDegree() {
        return polynomialDegree;
    }

    /**
     * Method used to set the polymonial degree from the outside.
     *
     * @param degree of the polynomial.
     */
    public void setDegree(int degree) {
        polynomialDegree = degree;
        coefficients = new ArrayList<>(polynomialDegree + 1);
        for (int i = 0; i < polynomialDegree + 1; ++i) {
            coefficients.add(0.0);
        }
    }

    /**
     * Method used to get a specific coefficient from the coefficients array
     * from the outside.
     *
     * @param index index of the array representing polymonial coefficients
     * @return the coefficient from the given index
     */
    public double getACoefficient(int index) {
        return coefficients.get(index);
    }

    /**
     * Method used to set an exact coefficient from the outside.
     *
     * @param coefficient coefficient value we wanto to set
     * @param index index in the coefficient array of the coefficient we want to
     * set
     */
    public void setACoefficient(double coefficient, int index) {
        coefficients.set(index, coefficient);
    }
}
