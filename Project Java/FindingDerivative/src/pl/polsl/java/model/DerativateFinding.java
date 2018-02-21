package pl.polsl.java.model;

/**
 * Class representing the Model from the MVC pattern. It stores an input
 * polynomial of which we want to find derivative.
 *
 * @author Monika Kokot
 * @version 2.0
 */
public class DerativateFinding {

    /**
     * Polynomial from the input.
     */
    private final Polynomial inPolynomial;

    /**
     * Constructor, which sets the polynomial component to the one that is
     * retrieved from the input data.
     *
     * @param aPolynomial function of which we want to find derivative
     */
    public DerativateFinding(Polynomial aPolynomial) {
        inPolynomial = aPolynomial;
    }

    /**
     * Definitions of lambda responsible for adding two integer numbers.
     */
    mathOperation<Integer, Integer, Integer> addition = (a, b) -> a + b;

    /**
     * Definiction of lambda responsible for multiplying two double numbers.
     */
    mathOperation<Double, Integer, Double> multiplication = (a, b) -> a * b;

    /**
     * Method used to find the derivative of the function get from the iput. It
     * uses one formula which can be used to almost all polymonial functions.
     * The only exception is when the debree of input polynomial is 0 - the
     * degree of output one will be 0 as well and the only coefficient will
     * always equal to 0.
     *
     * @return polynomial function representing the found derivative
     */
    public Polynomial findDerivative() {
        Polynomial outPolynomial;
        if (inPolynomial.getDegree() == 0) {
            outPolynomial = new Polynomial(inPolynomial.getDegree());
            outPolynomial.setACoefficient(0, 0);
        } else {
            outPolynomial = new Polynomial(inPolynomial.getDegree() - 1);
            for (int i = 0; i < outPolynomial.getDegree() + 1; ++i) {
                int newIndex = addition.operation(i, 1);
                double newCoefficient = multiplication.operation(inPolynomial.getACoefficient(newIndex), newIndex);
                outPolynomial.setACoefficient(newCoefficient, i);
            }
        }
        return outPolynomial;
    }

    /**
     * Functional interface needed to create lambda. It creates function which
     * gets two arguments of types A and B and return object of type R.
     *
     * @param <A> type of first argument
     * @param <B> type of second argument
     * @param <R> type of returned object
     */
    @FunctionalInterface
    interface mathOperation<A, B, R> {

        public R operation(A a, B b);
    }
}
