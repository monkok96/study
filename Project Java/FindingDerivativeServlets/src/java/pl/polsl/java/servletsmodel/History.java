package pl.polsl.java.servletsmodel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.java.databasemanagment.*;

/**
 * Class representig history of found derivatives belonging to specific user.
 *
 * @author Monika Kokot
 * @version 5.0
 */
public class History extends HttpServlet {

    /**
     * Method responsible for showing the history of derivatives. The data is
     * remembered in ArrayList of Pairs of two Strings - polynomial and its
     * derivative.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserParameters u = (UserParameters) session.getAttribute("user");
        if (u == null) {
            RequestDispatcher myDispatch = request.getRequestDispatcher("index.html");
            myDispatch.forward(request, response);
        }
        DataBaseReading dataBaseReading = new DbReading();
        response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();
        out.println("<html>\n<body>\n");

        ArrayList<Pair<String, String>> histList = dataBaseReading.userHistory(u);
        showResultSet(histList, out);

        out.println("</body>\n</html>");
    }

    /**
     * Method responsible for showing history in "get" html's option.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Method responsible for showing history in "post" html's option.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Method printing polynomial and its derivative on output.
     *
     * @param list list of pairs of strings representing polynomial and its
     * derivatie.
     * @param out PrintWriter to print data in browser.
     */
    private void showResultSet(ArrayList<Pair<String, String>> list, PrintWriter out) {
        out.print("-----------------------------------<br />");
        for (int i = 0; i < list.size(); ++i) {
            out.print("Polynomial: " + list.get(i).getKey() + "<br/>");

            out.print("Derivative: " + list.get(i).getValue() + "<br/>");
            out.print("-----------------------------------<br />");
        }
    }
}
