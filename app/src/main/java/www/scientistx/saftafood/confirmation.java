package www.scientistx.saftafood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class confirmation extends DialogFragment {
    Button Button;
    FirebaseAuth fAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.confirmation,container,false);
        Button=view.findViewById(R.id.button6);
        fAuth= FirebaseAuth.getInstance();
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin ad=(admin)getActivity();
                String Email=fAuth.getInstance().getCurrentUser().getEmail();
                if(Email.equals("jayanta15-2641@diu.edu.bd")){
                    ad.ResetMeal();
                }else if(Email.equals("mksa.sazzad@gmail.com")){
                    ad.ResetHome();
                }else {
                    Toast.makeText(getActivity(),"You don't have permission",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

}
