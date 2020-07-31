package Controller;

import Model.QueryModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ChangeLimitServlet")
public class ChangeLimitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CORS.config(request,response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String accountID = request.getParameter("accountID");
            String limit = request.getParameter("limit");
            QueryModel queryModel = new QueryModel();
            queryModel.changeLimit(accountID,limit);
            response.setStatus(201);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
