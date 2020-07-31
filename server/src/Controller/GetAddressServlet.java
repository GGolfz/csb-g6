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

@WebServlet(name = "GetAddressServlet")
public class GetAddressServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CORS.config(request,response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String ownerID = request.getParameter("ownerID");
            QueryModel queryModel = new QueryModel();
            ResultSet owner = queryModel.getOwnerAddress(ownerID);
            if(owner == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
            else{
                String result = toJSON(ownerID, owner);
                if(result.equals("")){
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
                out.print(result);
                response.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
    private String toJSON(String ownerID, ResultSet owner){
        try {
            String result = "{";
            result += "\"id\":" + "\"" + ownerID + "\"" + ",";
            String ownerName = "";
            String ownerLastname = "";
            String address = "";
            String addressNum = "";
            String villageNum = "";
            String building = "";
            String soi = "";
            String road = "";
            String subDistrict = "";
            String district = "";
            String province = "";
            String zipcode = "";
            owner.next();
            ownerName = owner.getString("name");
            ownerLastname = owner.getString("lastname");
            address = owner.getString("address");
            result += "\"ownerName\":" + "\"" + ownerName + "\"" + ",";
            result += "\"ownerLastname\":" + "\"" + ownerLastname + "\"" + ",";
            String[] newAddress = address.split("//");
            addressNum = newAddress[0];
            villageNum = newAddress[1];
            building = newAddress[2];
            soi = newAddress[3];
            road = newAddress[4];
            subDistrict = newAddress[5];
            district = newAddress[6];
            province = newAddress[7];
            zipcode = newAddress[8];
            result += "\"address\":{";
            result += "\"addressNum\":" + "\"" + addressNum + "\"" + ",";
            result += "\"villageNum\":" + "\"" + villageNum + "\"" + ",";
            result += "\"building\":" + "\"" + building + "\"" + ",";
            result += "\"soi\":" + "\"" + soi + "\"" + ",";
            result += "\"road\":" + "\"" + road + "\"" + ",";
            result += "\"subDistrict\":" + "\"" + subDistrict + "\"" + ",";
            result += "\"district\":" + "\"" + district + "\"" + ",";
            result += "\"province\":" + "\"" + province + "\"" + ",";
            result += "\"zipcode\":" + "\"" + zipcode + "\"";
            result += "}}";
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
