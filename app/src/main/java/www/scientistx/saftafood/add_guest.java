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

import java.util.Date;

public class add_guest extends DialogFragment implements AdapterView.OnItemSelectedListener {
    EditText Number;
    Button Add;
    Spinner Spinner;
    FirebaseFirestore fStore;
    public String month,date;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view= inflater.inflate(R.layout.add_guest, container, false);
        Number=view.findViewById(R.id.number);
        Add=view.findViewById(R.id.addextra);
        Spinner=view.findViewById(R.id.spinner);
        fStore=FirebaseFirestore.getInstance();
        String [] values =
                {"None","Jayanta Roy","Amanullah Akon","Sazzad Hossain","Suin Chowdhury","Nahid Hasan","Sujon Islam","Hridoy Alam","Saiful Islam"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,values);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // attaching data adapter to spinner
        Spinner.setAdapter(dataAdapter);
        Spinner.setOnItemSelectedListener(this);
        java.util.Date d= new Date();
        month=String.format("%tb",d)+" "+String.format("%tY",d);
        date=String.format("%te",d);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String item = parent.getItemAtPosition(position).toString();
        //String number=Number.getText().toString().trim();
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=Integer.parseInt(Number.getText().toString().trim());
                if(number>2){
                    Number.setError("Less then 3");
                    return;
                }
                if(number<1){
                    Number.setError("Empty");
                    return;
                }
                if(item.equals("Jayanta Roy")){
                    fStore.collection("users").document("jayanta15-2641@diu.edu.bd")
                            .update("totalmeal", FieldValue.increment(number));
                    fStore.collection("meal").document(month).collection("totalmeal").document(month)
                            .update(date,FieldValue.increment(1));
                    Toast.makeText(getActivity(),"Add Guest Successfull",Toast.LENGTH_LONG).show();
                }else if(item.equals("Sazzad Hossain")){
                    fStore.collection("users").document("mksa.sazzad@gmail.com")
                            .update("totalmeal", FieldValue.increment(number));
                    fStore.collection("meal").document(month).collection("totalmeal").document(month)
                            .update(date,FieldValue.increment(1));
                    Toast.makeText(getActivity(),"Add Guest Successfull",Toast.LENGTH_LONG).show();
                }else if(item.equals("Amanullah Akon")){
                    fStore.collection("users").document("amanullah15-2447@diu.edu.bd")
                            .update("totalmeal", FieldValue.increment(number));
                    fStore.collection("meal").document(month).collection("totalmeal").document(month)
                            .update(date,FieldValue.increment(1));
                    Toast.makeText(getActivity(),"Add Guest Successfull",Toast.LENGTH_LONG).show();
                }else if(item.equals("Suin Chowdhury")){
                    fStore.collection("users").document("rowshan47-318@diu.edu.bd")
                            .update("totalmeal", FieldValue.increment(number));
                    fStore.collection("meal").document(month).collection("totalmeal").document(month)
                            .update(date,FieldValue.increment(1));
                    Toast.makeText(getActivity(),"Add Guest Successfull",Toast.LENGTH_LONG).show();
                }else if(item.equals("Nahid Hasan")){
                    fStore.collection("users").document("nahid10-434@diu.edu.bd")
                            .update("totalmeal", FieldValue.increment(number));
                    fStore.collection("meal").document(month).collection("totalmeal").document(month)
                            .update(date,FieldValue.increment(1));
                    Toast.makeText(getActivity(),"Add Guest Successfull",Toast.LENGTH_LONG).show();
                }else if(item.equals("Sujon Islam")){
                    fStore.collection("users").document("sujon15-2813@diu.edu.bd")
                            .update("totalmeal", FieldValue.increment(number));
                    fStore.collection("meal").document(month).collection("totalmeal").document(month)
                            .update(date,FieldValue.increment(1));
                    Toast.makeText(getActivity(),"Add Guest Successfull",Toast.LENGTH_LONG).show();
                }else if(item.equals("Hridoy Alam")){
                    fStore.collection("users").document("hridoy15-3723@diu.edu.bd")
                            .update("totalmeal", FieldValue.increment(number));
                    fStore.collection("meal").document(month).collection("totalmeal").document(month)
                            .update(date,FieldValue.increment(1));
                    Toast.makeText(getActivity(),"Add Guest Successfull",Toast.LENGTH_LONG).show();
                }else if(item.equals("Saiful Islam")){
                    fStore.collection("users").document("saiful15-2678@diu.edu.bd")
                            .update("totalmeal", FieldValue.increment(number));
                    fStore.collection("meal").document(month).collection("totalmeal").document(month)
                            .update(date,FieldValue.increment(1));
                    Toast.makeText(getActivity(),"Add Guest Successfull",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(),"not found",Toast.LENGTH_LONG).show();
                }
                Number.setText("");
                Spinner.setSelection(0);
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
