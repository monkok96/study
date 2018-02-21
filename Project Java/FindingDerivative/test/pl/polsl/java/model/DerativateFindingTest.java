package pl.polsl.java.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class containing unit tests to methods from DerivativeFinding class.
 *
 * @author Monika Kokot
 * @version 2.0
 */
public class DerativateFindingTest {

    /**
     * Intance of DerivativeFinding which will be used to do tests.
     */
    DerativateFinding derivativeInstance;

    /**
     *
     */
    public DerativateFindingTest() {
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
     * Test of findDerivative method, of class DerativateFinding.
     */
    @Test
    public void testFindDerivative() {

        Polynomial expResult;
        Polynomial result;
        Polynomial inPol;

        /**
         * Testing a situation in which the input polynomial's degree is equal
         * to 0. The output one should have same degree and the only coefficient
         * should be equal to 0.
         */
        inPol = new Polynomial(0);
        inPol.setACoefficient(2, 0);
        derivativeInstance = new DerativateFinding(inPol);
        expResult = new Polynomial(0);
        result = derivativeInstance.findDerivative();
        assertEquals(expResult.getDegree(), result.getDegree());
        assertEquals(expResult.getACoefficient(0), result.getACoefficient(0), 0.0);

        /**
         * Testing a situation in which there should be found a typical
         * derivative to some typical polynomial.
         */
        inPol = new Polynomial(2);
        for (int i = 0; i <= 2; ++i) {
            inPol.setACoefficient(i + 1, i);
        }
        derivativeInstance = new DerativateFinding(inPol);
        expResult = new Polynomial(1);
        expResult.setACoefficient(2, 0);
        expResult.setACoefficient(6, 1);
        result = derivativeInstance.findDerivative();
        assertEquals(expResult.getDegree(), result.getDegree());
        for (int i = 0; i < result.getDegree() + 1; ++i) {
            assertEquals(expResult.getACoefficient(i), result.getACoefficient(i), 0.0);
        }

        /**
         * Testing a situation in which the derivativeInstance is null. There
         * should occur an exception.
         */
        try {
            derivativeInstance = null;
            expResult = null;
            result = derivativeInstance.findDerivative();
            assertEquals(expResult, result);
            fail("An exception should occur while trying to invoke method to the null object");
        } catch (NullPointerException e) {

        }

        /**
         * Testing a situation in which the input polynomial is null. There
         * should occur an exception.
         */
        try {
            inPol = null;
            derivativeInstance = new DerativateFinding(inPol);
            expResult = null;
            result = derivativeInstance.findDerivative();
            assertEquals(expResult, result);
            fail("An exception should occur while trying to find derivative to null polynomial");
        } catch (NullPointerException e) {

        }
    }

}
