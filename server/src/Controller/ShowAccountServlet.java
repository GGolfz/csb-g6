package Controller;

import Model.QueryModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet(name = "Controller.ShowAccountServlet")
public class ShowAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CORS.config(request,response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()){
            String ownerID = request.getParameter("ownerID");
            QueryModel queryModel = new QueryModel();
            ResultSet owner = queryModel.getOwner(ownerID);
            if(owner == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
            else {
                String result = toJSON(ownerID,owner);
                if(result.equals("")){
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
                out.print(result);
                response.setStatus(200);
            }
        } catch (Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
    private String toJSON(String ownerID,ResultSet owner){
        try {
            QueryModel queryModel = new QueryModel();
            String result = "{";
            result += "\"id\":" + "\"" + ownerID + "\"" + ",";
            String ownerName = "";
            String ownerLastname = "";
            owner.next();
            ownerName = owner.getString("name");
            ownerLastname = owner.getString("lastname");
            result += "\"name\":" + "\"" + ownerName + "\"" + ",";
            result += "\"lastname\":" + "\"" + ownerLastname + "\"" + ",";
            result += "\"accounts\":[";
            ResultSet accounts = queryModel.getAccount(ownerID);
            String accountID = "";
            String accountType = "";
            String balance = "";
            String currency = "";
            String paymentlimit = "";
            String interest = "";
            String fixed = "";
            String fee = "";
            String remain = "";
            while (accounts.next()) {
                accountID = accounts.getString("id");
                accountType = accounts.getString("type");
                balance = accounts.getString("balance");
                currency = accounts.getString("currency");
                fee = accounts.getString("fee");
                paymentlimit = accounts.getString("limit");
                interest = accounts.getString("interest");
                fixed = accounts.getString("fixed");
                remain = queryModel.getRemain(accountID);
                String alreadydep = queryModel.getalreadydep(accountID);
                result += "{";
                result += "\"accountID\":" + "\"" + accountID + "\"" + ",";
                result += "\"accountType\":" + "\"" + accountType + "\"" + ",";
                result += "\"balance\":" + "\"" + balance + "\"" + ",";
                result += "\"currency\":" + "\"" + currency + "\"" + ",";
                result += "\"fee\":" + "\"" + fee + "\"" + ",";
                result += "\"limit\":" + "\"" + paymentlimit + "\"" + ",";
                result += "\"interest\":" + "\"" + interest + "\"" + ",";
                result += "\"fixed\":" + "\"" + fixed + "\"" + ",";
                result += "\"alreadydep\":" + "\"" + alreadydep + "\"" + ",";
                result += "\"remain\":" + "\"" + remain + "\"" + "},";
            }
            result = result.substring(0, result.length() - 1);
            result += "]}";
            return  result;
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }
}
