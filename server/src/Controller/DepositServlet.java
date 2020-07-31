package Controller;

import Model.QueryModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DepositServlet")
@MultipartConfig

public class DepositServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CORS.config(request,response);
        response.setContentType("application/json");
        PrintWriter p = response.getWriter();
        try {
        QueryModel q = new QueryModel();

        String owner_id = request.getParameter("owner_id");
        String account_id = request.getParameter("account_id");
        String amount = request.getParameter("amount");
        String note = request.getParameter("note");

            String update = q.deposit(owner_id,account_id,"Deposit", amount, note);
            response.setStatus(201);
            p.println(String.format("{\"success\": \"true\", \"update\": \""+update+"\"}"));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(400);
            p.print(String.format("{\"error\": \"%s\"}", e.getMessage()));
        }
    }

    /*
    response.setStatus();
    Code 201 = Create
    Code 200 = Ok

    Code 400 = Bad request
    Code 401 = Unauthorized
    Code 404 = Not found
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

