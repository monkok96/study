package pl.polsl.java.model;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;

/**
 * Class containing unit tests to methods from Polynomial class.
 *
 * @author Monika Kokot
 * @version 2.0
 */
public class PolynomialTest {

    /**
     * Intance of polynomial.
     */
    Polynomial polynomialInstance;

    /**
     *
     */
    public PolynomialTest() {
    }

    /**
     *
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of getPolynomialString method, of class Polynomial.
     */
    @Test
    public void testGetPolynomialString() {
        String expResult = "";
        String result;

        /**
         * Testing a situation in which all coefficients of the polynomial are
         * equal to 0. Every coefficientfrom the derivative should be equal to 0
         * as well, so the polynomial string will be empty.
         */
        polynomialInstance = new Polynomial(2);
        expResult = "";
        result = polynomialInstance.getPolynomialString();
        assertEquals(expResult, result);

        /**
         * Testing a situation in which there is a typical polynomial.
         */
        polynomialInstance = new Polynomial(2);
        for (int i = 0; i <= 2; ++i) {
            polynomialInstance.setACoefficient(i + 1, i);
        }
        expResult = "3.0x^2 + 2.0x^1 + 1.0x^0";
        result = polynomialInstance.getPolynomialString();
        assertEquals(expResult, result);

        /**
         * Testing a situation in which the polynomial is null. There should
         * occur an exception.
         */
        try {
            polynomialInstance = new Polynomial();
            expResult = "";
            result = polynomialInstance.getPolynomialString();
            assertEquals(expResult, result);
            fail("The exception should occur while trying to get string of the null polynomial");
        } catch (NullPointerException e) {
        }

    }

    /**
     * Test of readInput method, of class Polynomial.
     * @throws java.lang.Exception
     */
    @Test
    public void testReadInput() throws Exception {

        /**
         * Testing a situation in which the input file exists.
         */
        Scanner inFile = new Scanner(new File("input_data.txt"));
        polynomialInstance = new Polynomial();
        polynomialInstance.readInput(inFile);
        int expPolynomialDegree = 3;
        ArrayList<Double> expCoefficients = new ArrayList();
        expCoefficients.add(4.0);
        expCoefficients.add(3.0);
        expCoefficients.add(1.0);
        expCoefficients.add(2.0);

        int polynomialDegree = polynomialInstance.getDegree();
        ArrayList<Double> coefficients = new ArrayList();
        for (int i = 0; i <= polynomialDegree; ++i) {
            coefficients.add(polynomialInstance.getACoefficient(i));
        }

        assertEquals(expPolynomialDegree, polynomialDegree);
        assertEquals(expCoefficients, coefficients);

        /**
         * Testing a situation in which input file is null. There should occur
         * an exception.
         */
        try {
            inFile = null;
            polynomialInstance = new Polynomial();
            polynomialInstance.readInput(inFile);
            fail("The exception should occur while trying to read from null file");
        } catch (NullPointerException e) {

        }

        /**
         * Testing a situation in which input file name is empty. There should
         * occur an exception.
         */
        try {
            inFile = new Scanner(new File(""));
            polynomialInstance = new Polynomial();
            polynomialInstance.readInput(inFile);
            fail("The exception should occur while trying to read from file of empty name");
        } catch (FileNotFoundException e) {

        }

        /**
         * Testing a situation in which input file does not exist. There should
         * occur an exception.
         */
        try {
            inFile = new Scanner(new File("not_existing"));
            polynomialInstance = new Polynomial();
            polynomialInstance.readInput(inFile);
            fail("The exception should occur while trying to read from non-existing file");
        } catch (FileNotFoundException e) {

        }

        /**
         * Testing a situation in which input file is empty. There shoudl occur
         * an exception.
         */
        try {
            inFile = new Scanner(new File("some_empty_file.txt"));
            polynomialInstance = new Polynomial();
            polynomialInstance.readInput(inFile);
            fail("The exception should occur while trying to read from an empty file");
        } catch (FileNotFoundException | NoSuchElementException e) {

        }

    }

    /**
     * Test of getACoefficient method, of class Polynomial.
     */
    @Test
    public void testGetACoefficient() {
        int index = 0;
        double expResult = 0.0;
        double result;

        index = 0;
        polynomialInstance = new Polynomial(0);
        result = polynomialInstance.getACoefficient(index);
        assertEquals(expResult, result, 0.0);

        index = 2;
        polynomialInstance = new Polynomial(2);
        result = polynomialInstance.getACoefficient(index);
        assertEquals(expResult, result, 0.0);

        index = 2;
        polynomialInstance = new Polynomial(5);
        polynomialInstance.setACoefficient(2.0, index);
        polynomialInstance.getACoefficient(index);
        result = polynomialInstance.getACoefficient(index);
        assertEquals(expResult, result, 2.0);

        try {
            index = 0;
            polynomialInstance = new Polynomial();
            polynomialInstance.getACoefficient(index);
            result = polynomialInstance.getACoefficient(index);
            assertEquals(expResult, result, 0.0);
            fail("The exception should occur while trying to set an element in the null polynomal");
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {

        }

        try {
            index = -1;
            polynomialInstance = new Polynomial(2);
            polynomialInstance.getACoefficient(index);
            result = polynomialInstance.getACoefficient(index);
            assertEquals(expResult, result, 0.0);
            fail("The exception should occur while trying to set an element of the negative index");
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        try {
            index = 5;
            polynomialInstance = new Polynomial(2);
            polynomialInstance.getACoefficient(index);
            result = polynomialInstance.getACoefficient(index);
            assertEquals(expResult, result, 0.0);
            fail("The exception should occur while trying to set an element of index higher than the polynomial degree + 1");
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Test of setACoefficient method, of class Polynomial.
     */
    @Test
    public void testSetACoefficient() {
        double coefficient = 5;
        int index = 0;
        polynomialInstance = new Polynomial(0);
        polynomialInstance.setACoefficient(coefficient, index);

        index = 2;
        polynomialInstance = new Polynomial(2);
        polynomialInstance.setACoefficient(coefficient, index);

        index = 2;
        polynomialInstance = new Polynomial(5);
        polynomialInstance.setACoefficient(coefficient, index);

        try {
            index = 0;
            polynomialInstance = new Polynomial();
            polynomialInstance.setACoefficient(coefficient, index);
            fail(" The exception should occur while trying to set an element in the null polynomal");
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {

        }

        try {
            index = -1;
            polynomialInstance = new Polynomial(2);
            polynomialInstance.setACoefficient(coefficient, index);
            fail("The exception should occur while trying to set an element of the negative index");
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        try {
            index = 5;
            polynomialInstance = new Polynomial(2);
            polynomialInstance.setACoefficient(coefficient, index);
            fail("The exception should occur while trying to set an element of index higher than the polynomial degree + 1");
        } catch (IndexOutOfBoundsException e) {

        }
    }

}
