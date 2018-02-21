package pl.polsl.java.servletsmain;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Class representing signup servlet. It reacts proparly to the fact that the
 * user want to sign up.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class SignUp extends HttpServlet {

    /**
     * Method representing signup servlet. It redirects user to servlet
     * responsible of signing up to database.
     *
     * @param request the http servlet request
     * @param response the http servlet response
     * @throws IOException
     * @throws ServletException
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher myDispatch = request.getRequestDispatcher("indexSignUp.html");
        myDispatch.forward(request, response);

    }

    /**
     * Method representing signup servlet in html's "get" option. It redirects
     * user to servlet responsible of signing up to database.
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
     * Method representing signup servlet in html's "post" option. It redirects
     * user to servlet responsible of signing up to database.
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
}
