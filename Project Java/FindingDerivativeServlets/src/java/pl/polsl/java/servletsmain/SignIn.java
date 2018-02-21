package pl.polsl.java.servletsmain;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import pl.polsl.java.databasemanagment.*;

/**
 * Class representing signin servlet. It allows user to log in.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class SignIn extends HttpServlet {

    /**
     * Method realizing signing in servlet. It checks in database if user data
     * passed by them re proper (if user of such parameters exists in database).
     * If so, it redirects user to main page ater logging it otherwise it shows
     * proper communicat.
     *
     * @param request the http servlet request
     * @param response the hhtp servlet resonse
     * @throws IOException
     * @throws ServletException
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserParameters userParams;
        response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();
        DataBaseReading dataBaseReading = new DbReading();
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("user") != null) {
                session.setAttribute("user", null);
            }
            session.setMaxInactiveInterval(Integer.MAX_VALUE);
            if (request.getParameter("username") != null && request.getParameter("password") != null) {
                userParams = new UserParameters();
                userParams.setUName(request.getParameter("username"));
                userParams.setUPassword(request.getParameter("password"));
                session.setAttribute("user", userParams);
                String myDbUrl = getServletConfig().getInitParameter("DB_URL");
                out.println("<html>\n<body>\n");
                if (userParams.getUName().length() == 0 || userParams.getUPassword().length() == 0) {
                    out.println("<h1>You should give two parameters.</h1>");
                    session.invalidate();
                } else {
                    if (dataBaseReading.doesUserExist(myDbUrl, userParams)) {
                        out.println("<h1>Hello " + userParams.getUName() + "!!!</h1>\n");
                        RequestDispatcher myDispatch = request.getRequestDispatcher("indexDerivative.html");
                        myDispatch.forward(request, response);
                    } else {
                        session.invalidate();
                        out.println("<h1>Inproper username or password.</h1>\n");
                    }
                }
            } else {
                RequestDispatcher myDispatch = request.getRequestDispatcher("index.html");
                myDispatch.forward(request, response);
            }

        } catch (FileNotFoundException | NullPointerException e) {
            response.sendError(response.SC_BAD_REQUEST, "Cannot find database.");
        } catch (NoSuchElementException e) {
            response.sendError(response.SC_BAD_REQUEST, "Ups...something went wrong. Probably inproper database content.");
        } finally {
            out.println("</body>\n</html>");
        }
    }

    /**
     * Method realizing signing in servlet in html's "get" option. It checks in
     * database if user data passed by them re proper (if user of such
     * parameters exists in database). If so, it redirects user to main page
     * ater logging it otherwise it shows proper communicat.
     *
     * @param request the http servlet request
     * @param response the hhtp servlet resonse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    /**
     * Method realizing signing in servlet in html's "post" option. It checks in
     * database if user data passed by them re proper (if user of such
     * parameters exists in database). If so, it redirects user to main page
     * ater logging it otherwise it shows proper communicat.
     *
     * @param request the http servlet request
     * @param response the hhtp servlet resonse
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }
}
