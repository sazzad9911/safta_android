package www.scientistx.saftafood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class add_home_credit extends DialogFragment implements AdapterView.OnItemSelectedListener {
    FirebaseFirestore firestore;
    Button btn;
    EditText txt;
    Spinner sp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.add_home_credit,container,false);
        firestore=FirebaseFirestore.getInstance();
        btn=view.findViewById(R.id.button5);
        txt=view.findViewById(R.id.editText);
        sp=view.findViewById(R.id.spinner3);
        String [] values =
                {"None","Jayanta Roy","Amanullah Akon","Sazzad Hossain","Suin Chowdhury","Nahid Hasan","Sujon Islam","Hridoy Alam","Saiful Islam"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,values);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // attaching data adapter to spinner
        sp.setAdapter(dataAdapter);
        sp.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String item = parent.getItemAtPosition(position).toString();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input=txt.getText().toString().trim();
                double number=Double.parseDouble(input);
                if(input.isEmpty()){
                    txt.setError("fill");
                    return;
                }
                if(item.equals("Jayanta Roy")){
                    firestore.collection("users").document("jayanta15-2641@diu.edu.bd")
                            .update("homecredit", FieldValue.increment(number));
                }else if(item.equals("Sazzad Hossain")){
                    firestore.collection("users").document("mksa.sazzad@gmail.com")
                            .update("homecredit", FieldValue.increment(number));
                }else if(item.equals("Amanullah Akon")){
                    firestore.collection("users").document("amanullah15-2447@diu.edu.bd")
                            .update("homecredit", FieldValue.increment(number));
                }else if(item.equals("Suin Chowdhury")){
                    firestore.collection("users").document("rowshan47-318@diu.edu.bd")
                            .update("homecredit", FieldValue.increment(number));
                }else if(item.equals("Nahid Hasan")){
                    firestore.collection("users").document("nahid10-434@diu.edu.bd")
                            .update("homecredit", FieldValue.increment(number));
                }else if(item.equals("Sujon Islam")){
                    firestore.collection("users").document("sujon15-2813@diu.edu.bd")
                            .update("homecredit", FieldValue.increment(number));
                }else if(item.equals("Hridoy Alam")){
                    firestore.collection("users").document("hridoy15-3723@diu.edu.bd")
                            .update("homecredit", FieldValue.increment(number));
                }else if(item.equals("Saiful Islam")){
                    firestore.collection("users").document("saiful15-2678@diu.edu.bd")
                            .update("homecredit", FieldValue.increment(number));
                }else {
                    Toast.makeText(getActivity(),"not found",Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getActivity(),"Successfully Added Home Credit",Toast.LENGTH_LONG).show();
                txt.setText("");
                sp.setSelection(0);
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
