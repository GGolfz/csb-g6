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

@WebServlet(name = "GetOwnerServlet")
public class GetOwnerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CORS.config(request,response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String ownerID = request.getParameter("ownerID");
            QueryModel queryModel = new QueryModel();
            ResultSet owner = queryModel.getOwnerInfo(ownerID);
            if(owner == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
            else{
                String result = toJSON(ownerID,owner);
                out.print(result);
                response.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
    private String toJSON(String ownerID,ResultSet owner){
        try {
            String citizenID = "";
            String passportID = "";
            String ownerName = "";
            String ownerLastname = "";
            String birthdate = "";
            String gender = "";
            String email = "";
            String tel = "";
            String occupation = "";
            String result = "{";
            result += "\"id\":" + "\"" + ownerID + "\"" + ",";
            owner.next();
            citizenID = owner.getString("citizen_id");
            passportID = owner.getString("passport_id");
            ownerName = owner.getString("name");
            ownerLastname = owner.getString("lastname");
            birthdate = owner.getString("date_of_birth");
            gender = owner.getString("gender");
            email = owner.getString("email");
            tel = owner.getString("tel");
            occupation = owner.getString("occupation");
            citizenID = citizenID != null ? citizenID : "";
            passportID = passportID != null ? passportID : "";
            ownerName = ownerName != null ? ownerName : "";
            ownerLastname = ownerLastname != null ? ownerLastname : "";
            birthdate = birthdate != null ? birthdate : "";
            gender = gender != null ? gender : "";
            email = email != null ? email : "";
            tel = tel != null ? tel : "";
            occupation = occupation != null ? occupation : "";
            result += "\"citizenID\":" + "\"" + citizenID + "\"" + ",";
            result += "\"passportID\":" + "\"" + passportID + "\"" + ",";
            result += "\"ownerName\":" + "\"" + ownerName + "\"" + ",";
            result += "\"ownerLastname\":" + "\"" + ownerLastname + "\"" + ",";
            result += "\"birthdate\":" + "\"" + birthdate + "\"" + ",";
            result += "\"gender\":" + "\"" + gender + "\"" + ",";
            result += "\"email\":" + "\"" + email + "\"" + ",";
            result += "\"tel\":" + "\"" + tel + "\"" + ",";
            result += "\"occupation\":" + "\"" + occupation + "\"";
            result += "}";
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
