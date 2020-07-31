package Controller;

import Model.QueryModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "OpenAccountServlet")
public class OpenAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CORS.config(request,response);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String id = request.getParameter("id");
            String citizenID = request.getParameter("citizenID");
            String passportID = request.getParameter("passportID");
            String ownerName = request.getParameter("ownerName");
            String ownerLastname = request.getParameter("ownerLastname");
            String birthdate = request.getParameter("birthdate");
            String email = request.getParameter("email");
            String occupation = request.getParameter("occupation");
            String tel = request.getParameter("tel");
            String gender = request.getParameter("gender");
            String addressNum = request.getParameter("addressNum");
            String villageNum = request.getParameter("villageNum");
            String soi = request.getParameter("soi");
            String road = request.getParameter("road");
            String district = request.getParameter("district");
            String subDistrict = request.getParameter("subDistrict");
            String building = request.getParameter("building");
            String province = request.getParameter("province");
            String zipcode = request.getParameter("zipcode");
            String accountType = request.getParameter("type");
            String branch = request.getParameter("branch");
            String currency = request.getParameter("currency");
            String fixed = request.getParameter("fixed");
            String month = request.getParameter("month");
            String address = addressNum+"//"+villageNum+"//"+building+"//"+soi+"//"+road+"//"+subDistrict+"//"+district+"//"+province+"//"+zipcode;
            QueryModel queryModel = new QueryModel();
            queryModel.updateCustomerInfo(id,citizenID,passportID,ownerName,ownerLastname,birthdate,gender,email,tel,occupation,address);
            int amount = queryModel.getAccountAmount(accountType)+1;
            int type = 0;
            String fee = "0";
            switch (accountType){
                case "Current":
                    type=1;
                    break;
                case "Saving":
                    type=2;
                    break;
                case "Foreign":
                    switch(currency){
                        case "EUR":
                            fee = "1";
                            break;
                        case "GBP":
                            fee = "1";
                            break;
                        case "USD":
                            fee = "0.8";
                            break;
                        case "THB":
                            fee = "0";
                            break;
                        default:
                            fee = "0.5";
                            break;
                    }
                    type=18;
                    break;
                case "Fixed Deposit":
                    switch (fixed){
                        case "2000":
                            switch (month) {
                                case "3":
                                    type=9;
                                    break;
                                case "6":
                                    type=10;
                                    break;
                                case "12":
                                    type=11;
                                    break;
                            }
                            break;
                        case "4000":
                            switch (month) {
                                case "3":
                                    type=12;
                                    break;
                                case "6":
                                    type=13;
                                    break;
                                case "12":
                                    type=14;
                                    break;
                            }
                            break;
                        case "6000":
                            switch (month) {
                                case "3":
                                    type=15;
                                    break;
                                case "6":
                                    type=16;
                                    break;
                                case "12":
                                    type=17;
                                    break;
                            }
                            break;
                    }
            }
            String accountID = String.format("%02d%03d%04d",Integer.parseInt(branch),type,amount);
            int sum = 0;
            for(int i = 0 ;i<accountID.length();i++){
                sum+=(10-i)*Integer.parseInt(accountID.substring(i,i+1));
            }
            int checkdigit = (11-sum%11)%10;
            accountID += ""+checkdigit;
            double bal = 0;
            String balance = "" +bal;
            double limit = 1000000;
            String paymentlimit = ""+limit;
            String type_id = ""+type;
            currency = currency==null?"THB":currency;
            String accountName = ownerName + " " + ownerLastname;
            try{
                queryModel.addAccount(accountID,id,accountType,accountName,balance,type_id,paymentlimit,currency,fee);
            }
            catch (Exception e){
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
            String result = "{\"accountID\":"+"\""+accountID+"\",\"accountType\":"+"\""+accountType+"\"}";
            out.print(result);
            response.setStatus(201);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
