package pl.polsl.java.servletsregistration;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import pl.polsl.java.databasemanagment.*;

/**
 * Class representing registration Servlet. It checks if new user can be added,
 * showing prope communicats.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class Registration extends HttpServlet {

    /**
     * Method realizing registration servlet. It checks if parameters of user do
     * add to database are proper - if so it adds user to the database,
     * otherwise it shows communicats why it cannot be done.
     *
     * @param request the http servler request
     * @param response the http servlet response
     * @throws IOException
     * @throws ServletException
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DataBaseWriting dbWriting = new DbWriting();
        UserParameters userParams = new UserParameters();
        response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();
        try {
            userParams.setUName(request.getParameter("username"));
            userParams.setUPassword(request.getParameter("password"));
            String myDbUrl = getServletConfig().getInitParameter("DB_URL");
            out.println("<html>\n<body>\n");
            if (request.getParameter("username") == null || request.getParameter("password") == null) {
                RequestDispatcher myDispatch = request.getRequestDispatcher("index.html");
                myDispatch.forward(request, response);
            }
            if (userParams.getUName().length() == 0 || userParams.getUPassword().length() == 0) {
                out.println("<h1>You should give two parameters.</h1>");
            } else {
                if (dbWriting.canWriteUser(myDbUrl, userParams)) {
                    out.println("<h1>Succesfully added new user!</h1>");
                    response.setHeader("Refresh", "1; URL=index.html");
                } else {
                    out.println("<h1>Such user already exists.</h1>\n");
                }
            }
        } catch (FileNotFoundException | NullPointerException e) {
            response.sendError(response.SC_BAD_REQUEST, "Cannot find database.");
        } catch (NoSuchElementException e) {
            response.sendError(response.SC_BAD_REQUEST, "Ups...something went wrong. Probably inproper database content.");
        } catch (IOException e) {
            response.sendError(response.SC_BAD_REQUEST, "Cannot open database.");
        } finally {
            out.println("</body>\n</html>");
        }
    }

    /**
     * Method realizing registration servlet in html's "get" option. It checks
     * if parameters of user do add to database are proper - if so it adds user
     * to the database, otherwise it shows communicats why it cannot be done.
     *
     * @param request the http servler request
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
     * Method realizing registration servlet in html's "post" option. It checks
     * if parameters of user do add to database are proper - if so it adds user
     * to the database, otherwise it shows communicats why it cannot be done.
     *
     * @param request the http servler request
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
