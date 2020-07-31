package Model;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {
    String Date, currency, amount, type,account_id,owner_id,payee_id,note;
    public Transaction(ResultSet result) {
        try {
            Date = result.getString("created_at");
            this.currency = result.getString("currency");
            this.amount = result.getString("amount");
            this.type = result.getString("transaction_type");
            this.account_id=result.getString("account_id");
            this.owner_id=result.getString("owner_id");
            this.payee_id=result.getString("payee_id");
            this.note=result.getString("note");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDate() {
        return Date;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }
    public String getAccount_id(){
        return account_id;
    }
    public String getOwner_id(){
        return owner_id;
    }
    public String getPayee_id(){ return payee_id;}
    public String getNote(){return note;}

}
