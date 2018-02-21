package pl.polsl.java.servletsmodel;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import pl.polsl.java.databasemanagment.UserParameters;
import pl.polsl.java.model.*;
import pl.polsl.java.databasemanagment.*;

/**
 * Class representing derivaive servlet. It lets user find derivative of the
 * polynomial.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class Derivative extends HttpServlet {

    /**
     * The polynomial of which there will be found derivative.
     */
    Polynomial polynomial;
    /**
     * The polynomial representingfound derivative.
     */
    Polynomial outPolynomial;
    /**
     * The instance of class responsible of finding derivative.
     */
    DerivativeFinding derivativeFinding;

    /**
     * Method representing derivative servlet. It checks if user passed proper
     * parameters to find derivative of the poynomial - if so, it finds and
     * shows derivative, otherwise it shows proper communicats.
     *
     * @param request the http servlet request
     * @param response the http servlet response
     * @throws IOException
     * @throws ServletException
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserParameters u = (UserParameters) session.getAttribute("user");
        if (u == null) {
            RequestDispatcher myDispatch = request.getRequestDispatcher("index.html");
            myDispatch.forward(request, response);
        }
        DataBaseWriting dataBaseWrting = new DbWriting();
        response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();
        polynomial = new Polynomial();
        derivativeFinding = new DerivativeFinding();
        outPolynomial = new Polynomial();
        try {
            out.println("<html>\n<body>\n");
            String pDegree = request.getParameter("degree");
            String coefficients = request.getParameter("coefficients");
            if (pDegree.length() == 0 || coefficients.length() == 0) {
                out.println("<h1>You should give two parameters.</h1>");
            } else {
                if (Integer.parseInt(pDegree) < 0) {
                    out.println("<h1>The polynomial degree cannot be negative.</h1>");
                } else {
                    polynomial.setDegree(Integer.parseInt(pDegree));
                    setPolynomialCoefficients(coefficients);
                    outPolynomial = derivativeFinding.findDerivative(polynomial);
                    out.println("The input polynomial: " + polynomial.getPolynomialString() + "<br />");
                    out.println("The output polynomial: " + outPolynomial.getPolynomialString());
                    dataBaseWrting.updateHistory(polynomial.getPolynomialString(), outPolynomial.getPolynomialString(), u);
                }
            }
        } catch (NumberFormatException | NullPointerException e) {
            out.println("<h1>Inproper data format.</h1>");
        } finally {
            out.println("</body>\n</html>");
        }
    }

    /**
     * Method representing derivative servlet in html's "get" option. It checks
     * if user passed proper parameters to find derivative of the poynomial - if
     * so, it finds and shows derivative, otherwise it shows proper communicats.
     *
     * @param request the http servlet request
     * @param response the http servlet response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    /**
     * Method representing derivative servlet in html's "post" option. It checks
     * if user passed proper parameters to find derivative of the poynomial - if
     * so, it finds and shows derivative, otherwise it shows proper communicats.
     *
     * @param request the http servlet request
     * @param response the http servlet response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    /**
     * Method setting coefficient passed by use to the polynomial of which there
     * will be found derivative. If data is incorrect, it throws
     * NumberFormatException.
     *
     * @param coef string in which there are coefficiens to set, separated with
     * semicolons.
     * @throws NumberFormatException
     */
    private void setPolynomialCoefficients(String coef) throws NumberFormatException {
        String[] coefAr = coef.split(";");
        try {
            for (int i = 0; i < polynomial.getDegree() + 1; ++i) {
                polynomial.setACoefficient(0.0, i);
            }
            for (int i = 0; i < coefAr.length; ++i) {
                if (i > polynomial.getDegree()) {
                    break;
                }
                polynomial.setACoefficient(Double.parseDouble(coefAr[i]), polynomial.getDegree() - i);
            }
        } catch (NumberFormatException e) {
            throw e;
        }
    }
}
