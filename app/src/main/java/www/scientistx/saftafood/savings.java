package www.scientistx.saftafood;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.Date;

public class savings {
    private double hand;
    private double total;
    java.util.Date d= new Date();
    private String months =String.format("%tb",d)+" "+String.format("%tY",d);
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    public savings(){

    }
    public void setHand(double hand){
        this.hand=hand;
    }
    public void setTotal(double total){
        this.total=total;
    }
    public void setHome(){

    }

}
