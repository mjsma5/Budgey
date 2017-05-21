package mjsma5.budgey;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

/**
 * Created by Matts on 27/04/2017.
 */

public class Transaction {
    private String id;             // Unique transaction ID
    private String amount;       // Transaction's Cost
    private String category;    // Transaction's user chosen category
    private String date;          // Date transaction entered, default current day
    private String note;        // Extra user entered details
    private String method;      // Payment used (eg, Paypal, Visa ...)
    private boolean taxable;    // Boolean if transaction flagged for taxable
    private boolean type;       // true: positive, false: negative

    public Transaction(){}

    DatabaseReference transRef = GoogleSignInActivity.transRef;


    public Transaction(String nAmount, String nCategory, String nDate, String nNote,
                       String nMethod, Boolean nTaxable, Boolean nType) {
        amount = nAmount;
        category = nCategory;
        date = nDate;
        note = nNote;
        method = nMethod;
        taxable = nTaxable;
        type = nType;
    }
    // methods to get
    public String getID() { return id; }
    public String getCategory() {return category;}
    public String getAmount() {return amount;}
    public Boolean gType() {return type; }

    // Methods to set
    public void setId(String item) { id = item; };
    public void setAmount(String item) { amount = item; }
    public void setCategory(String item) { category = item; }
    public void setDate(String item) { date = item; }
    public void setNote(String item) { note = item; }
    public void setMethod(String item) { method = item; }
    public void setTaxable(String item)  { taxable = item.equals("true"); }
    public void setType(String item)  { type = item.equals("true"); }

    void switchType(boolean t) {
        type = t;
    }
    void switchTaxable() {
        taxable = !taxable;
    }
    public void setType(Boolean item) { type = item; }

    // Method to update database
    public void updateDatabase() {
        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userRef = database.getReference("users/" + user.getUid() + "/transactions");
        */
        DatabaseReference currTrans;
        String key = transRef.push().getKey();
        HashMap<String, Object> result = new HashMap<>();
        result.put("amount", amount);
        result.put("category", category);
        result.put("date", date);
        result.put("note", note);
        result.put("method", method);
        result.put("taxable", taxable);
        result.put("type", type);
        currTrans = transRef.getRef().child("/" + key);
        currTrans.updateChildren(result);
    }

    public String getMethod() {
        return method;
    }
}

