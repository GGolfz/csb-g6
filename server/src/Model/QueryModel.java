package Model;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QueryModel {
    Connection conn;
    PreparedStatement preparedStatement;

    public QueryModel() throws Exception{
        conn = DBConnection.getMSSQLConnection();
    }
    public ResultSet statementQuery(String query){
        try {
            preparedStatement = conn.prepareStatement(query);
            return preparedStatement.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void executeQuery(String query){
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet getFavorite(String ownerID){
        String query = String.format("SELECT * FROM favorite WHERE ownerID='%s'",ownerID);
        ResultSet result = statementQuery(query);
        return result;
    }
    public String getUpdate(){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }
    public ResultSet getBranch(){
        String query = String.format("SELECT branch.id,branch.name AS name ,province.name AS province FROM branch JOIN province on province_id = province.id WHERE branch.[open] = 1;");
        return statementQuery(query);
    }
    public ResultSet getOwner(String ownerID){
        String query = String.format("SELECT customer.name AS name, customer.lastname AS lastname from customer\n" +
                "WHERE id LIKE '%s'",ownerID);
        return statementQuery(query);
    }
    public ResultSet getAccount(String ownerID){
        String query = String.format("SELECT account.id AS id ,account_type AS type,balance AS balance,payment_limit AS 'limit',currency AS currency,interest_rate AS 'interest',fixed_deposit AS 'fixed',maintenance_fees AS fee from dbo.account\n" +
                "LEFT JOIN account_type a on account.account_type_id = a.id \n" +
                "WHERE owner_id LIKE '%s' and status=1",ownerID);
        return statementQuery(query);
    }
    public ResultSet getOwnerInfo(String ownerID){
        String query = String.format("SELECT citizen_id AS citizen_id, \n" +
                "       passport_id AS passport_id,\n" +
                "       name AS name,\n" +
                "       lastname AS lastname,\n" +
                "       date_of_birth AS date_of_birth,\n" +
                "       gender AS gender,\n" +
                "       email AS email,\n" +
                "       tel AS tel,\n" +
                "       occupation AS occupation \n" +
                "from customer where id = '%s'",ownerID);
        return statementQuery(query);
    }
    public ResultSet getOwnerAddress(String ownerID){
        String query = String.format("SELECT name AS name, lastname AS lastname, address AS address from customer where id = '%s'",ownerID);
        return statementQuery(query);
    }
    public void updateCustomerInfo(String ownerID, String citizen_id, String passport_id, String name, String lastname, String date_of_birth, String gender, String email, String tel, String occupation, String address){
        String update_at = getUpdate();
        String query = String.format("UPDATE customer\n" +
                "SET citizen_id = '%s',passport_id='%s',name='%s',lastname='%s',date_of_birth='%s',gender='%s',email='%s',tel='%s',occupation='%s',address='%s',update_at='%s'\n" +
                "WHERE id = '%s'",citizen_id,passport_id,name,lastname,date_of_birth,gender,email,tel,occupation,address,update_at,ownerID);
        executeQuery(query);
    }
    public int getAccountAmount(String accout_type){
        String query = String.format("SELECT COUNT(id) AS count from account where account_type='%s' ",accout_type);
        ResultSet result = statementQuery(query);
        int count = 0;
        try {
            while (result.next()) {
                count = Integer.parseInt(result.getString("count"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
    public void changeLimit(String accountID,String limit){
        String update_at = getUpdate();
        String query= String.format("UPDATE account SET payment_limit = '%s', update_at = '%s' WHERE id = '%s'",limit,update_at,accountID);
        executeQuery(query);
    }
    public void addAccount(String accountID,String ownerID,String account_type,String name,String balance,String account_type_id,String payment_limit,String currency,String fee) {
        String update_at = getUpdate();
        String query = String.format("INSERT INTO account(id, owner_id, account_type, name, balance, account_type_id, payment_limit, currency, update_at, status, fee)\n" +
                "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s',1,'%s')", accountID, ownerID, account_type, name, balance, account_type_id, payment_limit, currency, update_at, fee);
        executeQuery(query);
    }
    public String[] closeAccount(String accountID) throws Exception {
        String query = String.format("SELECT datediff(day,(select created_at from account where id = '%s' ),getdate()) AS day",accountID);
        ResultSet res1 = statementQuery(query);
        String day="";
        while(res1.next()){
            day = res1.getString("day");
        }
        String query2 = String.format("SELECT owner_id, balance,currency,interest_rate from account\n" +
                "join account_type a on account.account_type_id = a.id\n" +
                "where account.id = '%s'",accountID);
        String balance="";
        String interest="";
        String currency="";
        String ownerID= "";
        ResultSet res2 = statementQuery(query2);
        while(res2.next()) {
            balance = res2.getString("balance");
            interest = res2.getString("interest_rate");
            currency = res2.getString("currency");
            ownerID = res2.getString("owner_id");
        }
        double money = (Double.parseDouble(balance)  * ((Double.parseDouble(day) /365) * (Double.parseDouble(interest)/100)));
        String money2 = String.format("%.2f",money);
        double allmoney = Double.parseDouble(balance) + Double.parseDouble(money2);
        deposit(ownerID,accountID,"Deposit",money2,"Interest");
        withdraw(ownerID,accountID,"Withdraw",String.format("%.2f",allmoney),"Close Account");
        String query3 = String.format("UPDATE account SET status = 0 where id = '%s'",accountID);
        executeQuery(query3);
        String[] res = {String.format("%.2f",allmoney)+" "+currency,money2};
        return res;
    }
    public String getPayeeName(String payee_id) throws SQLException {
        String query = String.format("SELECT name,lastname from customer\n" +
                "where id = (select owner_id from account where id= '%s')",payee_id);
        ResultSet result = statementQuery(query);
        String name ="";
        String lastname="";
        while(result.next()){
            name  = result.getString("name");
            lastname = result.getString("lastname");
        }
        if(name.equals("") && lastname.equals("")){
            return "";
        }
        else {
            return name + " " + lastname;
        }
    }
    public String getRemain(String accountID) throws SQLException {
        String query = String.format("SELECT sum(amount) as 'Amount' from [transaction] where account_id = '%s' and Convert(date,created_at) = Convert(date,GETDATE())",accountID);
        ResultSet result = statementQuery(query);
        result.next();
        String remain = result.getString("Amount");
        if(remain !=null){
            return remain;
        }
        else{
            return "0";
        }
    }
    public String getalreadydep(String accountID) throws SQLException {
        String query = String.format("SELECT count(ID) as 'deposit' from [transaction] where account_id ='%s' and MONTH(created_at) = MONTH(GETDATE()) and transaction_type = 'Deposit'",accountID);
        ResultSet result = statementQuery(query);
        result.next();
        String trans = result.getString("deposit");
        if(trans.equals("0")){
            return "false";
        }
        else{
            return "true";
        }
    }
    public void createTransaction(String owner_id, String account_id, String tranaction_type, String amount, String payee_id, String note, String update) throws SQLException {
        try {
            // Unusable (Waiting for the changes in database)
            String query = String.format("INSERT INTO CSC105_project.dbo.[transaction](account_id, owner_id, transaction_type, amount, payee_id, note, created_at)\n" +
                            "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                    account_id, owner_id, tranaction_type, amount, payee_id, note, update);

            preparedStatement = conn.prepareStatement(query);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return;
    }

    // Deposit Model
    public String deposit (String owner_id, String account_id,String transactionType, String amount, String note) throws Exception {
        try {
            String update = getUpdate();
            // Check if the "account id" is valid using "owner id"
            String query = String.format("SELECT account.id\n" +
                    "FROM CSC105_project.dbo.account\n" +
                    "WHERE owner_id = '%s';", owner_id);

            ResultSet result = statementQuery(query);

            ArrayList<String> ListOfAccounts = new ArrayList<>();

            while (result.next()) {
                ListOfAccounts.add(result.getString("id"));
            }

            if (ListOfAccounts.contains(account_id) && checkAccountStatus(account_id)) {
                /*
                 - Call createTransaction function.
                 - Create deposit transaction in the database.
                */
                createTransaction(owner_id ,account_id, transactionType, amount, account_id, note, update);

                // Calculate balance
                query = String.format("SELECT balance " +
                                      "FROM account\n" +
                                      "WHERE id = '%s';", account_id);
                preparedStatement = conn.prepareStatement(query);
                result = preparedStatement.executeQuery();
                result.next();

                double newAmount = Double.parseDouble(result.getString("balance")) + Double.parseDouble(amount);
                String newBalance = String.format("%.2f", newAmount);

                query = String.format("UPDATE account\n" +
                                "SET balance = '%s', update_at = '%s'\n" +
                                "WHERE id = '%s';",
                        newBalance, update, account_id);

                executeQuery(query);
                return update;
            } else if (!checkAccountStatus(account_id)) {
                Exception e = new Exception("Account is offline!");
                throw e;
            } else {
                Exception e = new Exception("Invalid account!");
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Withdraw Model
    public String withdraw (String owner_id, String account_id,String transactionType, String amount, String note) throws Exception {
        try {
            /*
             - Call createTransaction function.
             - Create withdraw transaction in the database.
             - Check if the balance is sufficient.
            */

            // Query get account balance
            String update = getUpdate();
            String query = String.format("SELECT balance\n" +
                                         "FROM CSC105_project.dbo.account\n" +
                                         "WHERE id = '%s';", account_id);

            ResultSet result = statementQuery(query);
            result.next();

            double Balance = Double.parseDouble(result.getString("balance"));
            double amountInDouble = Double.parseDouble(amount);

            // Check if balance is sufficient
            if (Balance >= amountInDouble && checkAccountStatus(account_id)) {
                // Create Transaction
                createTransaction(owner_id, account_id, transactionType, amount, account_id, note, update);

                double newAmount = Balance - amountInDouble;
                String newBalance = String.format("%.2f", newAmount);
                // Query update balance in the database
                query = String.format("UPDATE account\n" +
                                "SET balance = '%s', update_at = '%s'\n" +
                                "WHERE id = '%s';",
                        newBalance, update, account_id);

                executeQuery(query);
                return update;
            } else if (!checkAccountStatus(account_id)) {
                Exception e = new Exception("Account is offline!");
                throw e;
            } else {
                Exception e = new Exception("Insufficient balance!");
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    //Transfer Model
    public String transfer (String owner_id, String account_id, String payee_id, String amount,String transactionType, String note) throws Exception {
        try {
            // Check the owner payee id
            String query = String.format("SELECT owner_id\n" +
                    "FROM CSC105_project.dbo.account\n" +
                    "WHERE id = '%s' ", payee_id);

            ResultSet result = statementQuery(query);
            result.next();
            String ownerOfPayeeId = result.getString("owner_id");
            Exception e;

            if (checkMatchCurrency(account_id, payee_id) && (checkAccountStatus(account_id) && checkAccountStatus(payee_id))) {
                withdraw(owner_id, account_id, transactionType,amount, note);
                return deposit(ownerOfPayeeId, payee_id,transactionType, amount, note);
            } else if (!checkAccountStatus(account_id) || !checkAccountStatus(payee_id)) {
                e = new Exception("The account you are trying to transfer to is offline!");
                throw e;
            } else {
                e = new Exception("Please exchange before transfer!");
                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Check the status of the account
    public boolean checkAccountStatus (String account_id) throws Exception {
        try {
            String query = String.format("SELECT status\n" +
                    "FROM CSC105_project.dbo.account\n" +
                    "WHERE id = '%s';", account_id);

            preparedStatement = conn.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            boolean status = result.getBoolean("status");
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Check the currency of the account
    public boolean checkMatchCurrency (String account_id, String payee_id) throws Exception {
        try {
            String query = String.format("SELECT currency\n" +
                    "FROM account\n" +
                    "WHERE id = '%s' ", account_id);
            preparedStatement = conn.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            String account_currency = result.getString("currency");

            query = String.format("SELECT currency\n" +
                    "FROM account\n" +
                    "WHERE id = '%s' ", payee_id);
            preparedStatement = conn.prepareStatement(query);
            result = preparedStatement.executeQuery();
            result.next();
            String payee_currency = result.getString("currency");

            return (account_currency.equalsIgnoreCase(payee_currency) ? true : false);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateBankFinancial () {

    }

    //Getting Transaction History for each Account
    public ArrayList<Transaction> getTransactionHistory (String accountID) {
        ArrayList<Transaction> tran = new ArrayList<>();
        try {

            String query = String.format("SELECT * FROM dbo.[transaction]\n" +
                    "WHERE dbo.[transaction].account_id = '%s'\n ", accountID);
            preparedStatement = conn.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                tran.add(new Transaction(result));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tran;
    }

    public void addFavorite(String ownerID,String id,String name,String color) {
        String query = String.format("INSERT INTO favorite(ownerID,name,account_id,color) VALUES('%s','%s','%s','%s')",ownerID,name,id,color);
        executeQuery(query);
    }
    public void updateFavorite(String favid,String ownerID,String id,String name,String color) {
        String query = String.format("UPDATE favorite SET ownerID = '%s',name = '%s',account_id = '%s',color = '%s' WHERE id = '%s'",ownerID,name,id,color,favid);
        executeQuery(query);
    }
    public void deleteFavorite(String favid){
        String query = String.format("DELETE FROM favorite WHERE id = '%s'",favid);
        executeQuery(query);
    }
}
