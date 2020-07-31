package Controller;

import Model.QueryModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetPayeeNameServlet")
public class GetPayeeNameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CORS.config(request,response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String payeeID = request.getParameter("payee_id");
            QueryModel queryModel = new QueryModel();
            String payeeName = queryModel.getPayeeName(payeeID);
            if(payeeName.equals("")){
                response.sendError(400);
            }
            else {
                String result = "{\"payeeName\":" + "\"" + payeeName + "\"}";
                out.print(result);
                response.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
