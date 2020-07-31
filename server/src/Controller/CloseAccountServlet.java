package Controller;

import Model.QueryModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CloseAccountServlet")
public class CloseAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CORS.config(request,response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String accountID = request.getParameter("accountID");
            QueryModel queryModel = new QueryModel();
            String[] output = queryModel.closeAccount(accountID);
            String result = toJSON(accountID,output);
            out.print(result);
            response.setStatus(201);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    private String toJSON(String accountID,String[] output){
        String balance = output[0] ;
        String interest = output[1] ;
        String result = "{";
        result+= "\"accountID\":"+"\""+accountID+"\",";
        result+= "\"balance\":"+"\""+balance+"\",";
        result+= "\"interest\":"+"\""+interest+"\"";
        result+="}";
        return result;
    }
}
