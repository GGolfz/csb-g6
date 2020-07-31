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

@WebServlet(name = "DeleteFavoriteServlet")
public class DeleteFavoriteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CORS.config(request, response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String owner_id = request.getParameter("ownerID");
            String favid = request.getParameter("favid");
            QueryModel queryModel = new QueryModel();
            queryModel.deleteFavorite(favid);
            ResultSet resultSet = queryModel.getFavorite(owner_id);
            String result = toJSON(resultSet);
            if (result.equals("")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
            out.print(result);
            response.setStatus(201);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private String toJSON(ResultSet result){
        try {
            String json="{";
            json+="\"content\":"+"[";
            while(result.next()){
                String name1 = result.getString("name");
                String accountID1 = result.getString("account_id");
                String color1 = result.getString("color");
                String id = result.getString("id");
                json+="{";
                json+="\"favid\":"+"\""+id+"\",";
                json+="\"name\":"+"\""+name1+"\",";
                json+="\"color\":"+"\""+color1+"\",";
                json+="\"id\":"+"\""+accountID1+"\",";
                json+="\"text\":"+"\""+name1.substring(0,1)+"\"";
                json+="},";
            }
            if(json.length()>1){
                json = json.substring(0,json.length()-1);
            }
            json+="]";
            json+="}";
            return json;
        }
        catch (Exception e){
            e.printStackTrace();
            return "";

        }
    }
}