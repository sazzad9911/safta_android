package www.scientistx.saftafood;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Payement extends Fragment {
    ImageButton BTN;
    EditText Trnx,Amount;
    FirebaseAuth auth;
    FirebaseDatabase database;
    public Payement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_payement, container, false);
        BTN=v.findViewById(R.id.imageButton);
        Trnx=v.findViewById(R.id.trnx);
        Amount=v.findViewById(R.id.amount);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trnx=Trnx.getText().toString().trim();
                String amount=Amount.getText().toString().trim();
                if(TextUtils.isEmpty(trnx)){
                    Trnx.setError("Fill the body");
                    return;
                }if(TextUtils.isEmpty(amount)){
                    Amount.setError("Fill the body");
                    return;
                }
                // handle the method..........................................
                pay(trnx,amount);
            }
        });
        return v;
    }
    public void pay(String trnx,String amount){
        String email=auth.getInstance().getCurrentUser().getEmail();
        String all="Payement:"+amount+" Trnx ID:"+trnx;
        database.getReference("users").push().setValue(new vp_message(email,all));
        Toast.makeText(getContext(), "Request is accepted, Wait for confirmation", Toast.LENGTH_LONG).show();
    }

}
