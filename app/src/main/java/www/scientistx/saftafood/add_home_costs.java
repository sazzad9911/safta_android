package www.scientistx.saftafood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class add_home_costs extends DialogFragment {
    FirebaseFirestore fstore;
    Button btn;
    EditText txt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.add_home_costs,container,false);
        fstore=FirebaseFirestore.getInstance();
        txt=view.findViewById(R.id.editText112);
        btn=view.findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double number=Double.parseDouble(txt.getText().toString().trim());
                fstore.collection("savings").document("1")
                        .update("expenses", FieldValue.increment(number))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(),"Costs added Successfull",Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });
        return view;
    }
}
