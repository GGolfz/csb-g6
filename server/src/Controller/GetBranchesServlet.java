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

@WebServlet(name = "GetBranchesServlet")
public class GetBranchesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CORS.config(request,response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()){
            QueryModel queryModel = new QueryModel();
            ResultSet branch = queryModel.getBranch();
            if(branch == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
            else {
                String result = toJSON(branch);
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
    private String toJSON(ResultSet branch){
        try {
            String result = "{\"branch\":[";
            String id = "";
            String name = "";
            String province ="";
            while (branch.next()) {
                id = branch.getString("id");
                name = branch.getString("name");
                province = branch.getString("province");
                result += "{";
                result += "\"id\":" + "\"" + id + "\"" + ",";
                result += "\"name\":" + "\"" + name + "\""+",";
                result += "\"province\":" + "\"" + province + "\"";
                result += "},";
            }
            result = result.substring(0, result.length() - 1);
            result += "]}";
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
