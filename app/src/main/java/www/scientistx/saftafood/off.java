package www.scientistx.saftafood;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class off extends Fragment {
    ImageButton Off;
    EditText From,To;
    FirebaseDatabase database;
    FirebaseAuth auth;
    public off() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_off, container, false);
        Off=v.findViewById(R.id.off);
        From=v.findViewById(R.id.from);
        To=v.findViewById(R.id.to);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from=From.getText().toString().trim();
                String to=To.getText().toString().trim();
                if(TextUtils.isEmpty(from)){
                    From.setError("Fill the body");
                    return;
                }if(TextUtils.isEmpty(to)){
                    To.setError("Fill the body");
                    return;
                }
                off(to,from);
            }
        });
        return v;
    }
    public void off(String to,String from){
        String email=auth.getInstance().getCurrentUser().getEmail();
        String all="Meal off from:"+from+" to:"+to;
        database.getReference("users").push().setValue(new vp_message(email,all));
        Toast.makeText(getContext(),"Request is received, Wait for confirmation",Toast.LENGTH_LONG).show();
    }
}
