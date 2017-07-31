/**
 * Created by Андрей on 30.07.2017.
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        request.getRequestDispatcher("link.html").include(request, response);

        String name=request.getParameter("name");
        String password=request.getParameter("password");

        if(password.equals("admin")){
            out.print("Welcome, "+name+" "+",you logged in with admin role");
            HttpSession session=request.getSession();
            session.setAttribute("name",name);
            try {
                response.wait(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/UserUI");
        }
        else{
            out.print("Welcome, "+name+" "+",you logged in with user role");
            HttpSession session=request.getSession();
            session.setAttribute("name",name);
            try {
                response.wait(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response.sendRedirect("/UserUI");
        }
        out.close();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
