package Controller;

import Model.QueryModel;
import Model.Transaction;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class GetTransactionHistoryServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        CORS.config(request,response);
        response.setContentType("application/json");
        try(PrintWriter out = response.getWriter()) {
            String accountID = request.getParameter("accountID");

            QueryModel q = new QueryModel();
            ArrayList<Transaction> transactions = q.getTransactionHistory(accountID);

            Gson gson = new Gson();
            String jsonData = "";

            if(!transactions.isEmpty())
                jsonData = gson.toJson(transactions);

            out.print(jsonData);
            response.setStatus(200);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
