package pl.polsl.java.servletsmodel;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Class representing logut servlet. It reacts properly to th fact thatuser
 * wants to log out.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class LogOut extends HttpServlet {

    /**
     * Method representing logging out servlet. It redirects user to the main
     * page in case they want to log out.
     *
     * @param request the http servlet request
     * @param response the http servlet response
     * @throws IOException
     * @throws ServletException
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            session.invalidate();
            RequestDispatcher myDispatch = request.getRequestDispatcher("index.html");
            myDispatch.forward(request, response);
        } catch (IllegalStateException e) {
            out.println("<html>\n<body>\n<h1>No session found. <h1></body>\n</html>");
        }
    }

    /**
     * Method representing logging out servlet in html's "get" option. It
     * redirects user to the main page in case they want to log out.
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
     * Method representing logging out servlet in html's "post" option. It
     * redirects user to the main page in case they want to log out.
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
