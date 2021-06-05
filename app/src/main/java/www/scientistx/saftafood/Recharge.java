package www.scientistx.saftafood;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class Recharge extends Fragment {
ImageButton btn;
EditText Number,Amount;
FirebaseAuth fAuth;
FirebaseFirestore fStore;
FirebaseDatabase database;
public static final String ACCOUNT_SID="AC90ae8fc630af6100ba7ffb5cc4302fe8";
public static final String AUTH_TOKEN="657c9c9893138a6c2cfa90969debd016";
    public Recharge() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recharge, container, false);
        btn=v.findViewById(R.id.imageButton2);
        Number=v.findViewById(R.id.number);
        Amount=v.findViewById(R.id.amount);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        database=FirebaseDatabase.getInstance();
        final double homedue=getArguments().getDouble("homedue");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final ProgressDialog pd = new ProgressDialog(getActivity());
               // pd.setMessage("Loading...");
               // pd.show();
                String num=Number.getText().toString().trim();
                String amount=Amount.getText().toString().trim();
                double a=Double.parseDouble(amount);
                if(TextUtils.isEmpty(num)){
                    Number.setError("Fill the body");
                    return;
                }if(TextUtils.isEmpty(amount)){
                    Amount.setError("Fill the body");
                    return;
                }if(a<10){
                    Amount.setError("Valid amount");
                    return;
                }
                if((-homedue)>=a){
                    String email=fAuth.getInstance().getCurrentUser().getEmail();
                    DocumentReference df= fStore.collection("users").document(email);
                    df.update("extra",FieldValue.increment(a));
                    //Toast.makeText(getContext(),"Recharge successfull,wait for confirmation",Toast.LENGTH_LONG).show();
                    // type here for connection...............................
                    WriteData(num,amount);

                }else {
                    Toast.makeText(getContext(),"You have not enough balance in home account",Toast.LENGTH_LONG).show();
                    //pd.dismiss();
                }
            }
        });
        return v;
    }
    public void WriteData(String num,String amount){
        java.util.Date d= new Date();
        String date=String.format("%td",d)+" "+String.format("%tb",d)+" "+String.format("%tY",d);
        String email=fAuth.getInstance().getCurrentUser().getEmail();
        String all="Recharge Amount:"+amount+" to number:"+num;
        database.getReference("users").push().setValue(new vp_message(email,all));
        Toast.makeText(getContext(),"Recharge successfull,wait for confirmation",Toast.LENGTH_LONG).show();

    }

}
