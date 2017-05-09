package mjsma5.budgey;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import mjsma5.budgey.Login;

/**
 * Created by Matts on 27/04/2017.
 */

public class Transaction {
    private String id;             // Unique transaction ID
    private Double amount;       // Transaction's Cost
    private String category;    // Transaction's user chosen category
    private Calendar date;          // Date transaction entered, default current day
    private String note;        // Extra user entered details
    private String method;      // Payment used (eg, Paypal, Visa ...)
    private boolean taxable;    // Boolean if transaction flagged for taxable
    private boolean type;       // true: positive, false: negative

    public Transaction(String nID, Double nAmount, String nCategory, Calendar nDate, String nNote,
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

    // Methods to set
    public void setId(String item) { id = item; };
    public void setAmount(Double item) { amount = item; }
    public void setCategory(String item) { category = item; }
    public void setDate(Calendar item) { date = item; }
    public void setNote(String item) { note = item; }
    public void setMethod(String item) { method = item; }
    void switchType(boolean t) {
        type = t;
    }
    void switchTaxable() {
        taxable = !taxable;
    }
    public void setType(Boolean item) { type = item; }

    // Method to update databsase
    public void updateDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference userRef = database.getReference("users/").child(user.getUid());
        String key = userRef.child("transactions").push().getKey();
        HashMap<String, Object> result = new HashMap<>();
        result.put("amount", amount);
        result.put("category", category);
        result.put("date", date);
        result.put("note", note);
        result.put("method", method);
        result.put("taxable", taxable);
        result.put("type", type);
        userRef = userRef.getRef().child("/" + key);
        userRef.updateChildren(result);
    }

    public String getMethod() {
        return method;
    }
}

/* access
    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
    Transaction transaction = new Transaction("a", 12.12, "b", currentDateTimeString, "c", "d", true, true);
    transaction.updateDatabase();
 */
