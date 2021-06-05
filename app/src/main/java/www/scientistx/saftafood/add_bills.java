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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class add_bills extends Fragment implements AdapterView.OnItemSelectedListener {
    Button AddCredit,AddFine,AddExtra,Save,AddCost;
    EditText Home,Wifi,Elec,Cooking,Gass;
    Spinner spinner;
    TextView HandCash,Expend;
    FirebaseFirestore fStore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.add_bills,container,false);
        AddCredit=view.findViewById(R.id.addcreditss);
        AddFine=view.findViewById(R.id.addfine);
        AddExtra=view.findViewById(R.id.addextra);
        AddCost=view.findViewById(R.id.addcostsss);
        Save=view.findViewById(R.id.save);
        Home=view.findViewById(R.id.home);
        Wifi=view.findViewById(R.id.wifi);
        Elec=view.findViewById(R.id.elec);
        Cooking=view.findViewById(R.id.cooking);
        Gass=view.findViewById(R.id.gass);
        spinner=view.findViewById(R.id.holder);
        HandCash=view.findViewById(R.id.homehandcash);
        Expend=view.findViewById(R.id.spend);
        fStore=FirebaseFirestore.getInstance();
        String [] values =
                {"None","Jayanta Roy","Amanullah Akon","Sazzad Hossain","Suin Chowdhury","Nahid Hasan","Sujon Islam","Hridoy Alam","Saiful Islam"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,values);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
        AddCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_home_credit homeCredit=new add_home_credit();
                homeCredit.show(getFragmentManager(),"home credit");
            }
        });
        AddExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_extra extra=new add_extra();
                extra.show(getFragmentManager(),"add-extra");
            }
        });
        AddFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_home_fine fine=new add_home_fine();
                fine.show(getFragmentManager(),"home fine");
            }
        });
        AddCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_home_costs homeCosts=new add_home_costs();
                homeCosts.show(getFragmentManager(),"home costs");
            }
        });
        Data();
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String item = parent.getItemAtPosition(position).toString();
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String home=Home.getText().toString().trim();
                String wifi=Wifi.getText().toString().trim();
                String cooking=Cooking.getText().toString().trim();
                String gass=Gass.getText().toString().trim();
                String elec=Elec.getText().toString().trim();

                double homes=Double.parseDouble(home);
                double wifis=Double.parseDouble(wifi);
                double cook=Double.parseDouble(cooking);
                double gas=Double.parseDouble(gass);
                double ele=Double.parseDouble(elec);
                if(home.isEmpty()){
                    Home.setError("fill");
                    return;
                }if(wifi.isEmpty()){
                    Wifi.setError("fill");
                    return;
                }if(cooking.isEmpty()){
                    Cooking.setError("fill");
                    return;
                }
                if(item.equals("Jayanta Roy")){
                    fStore.collection("users").document("jayanta15-2641@diu.edu.bd")
                            .update("home",homes,"wifi",wifis,"cooking",cook,"gass",gas
                            ,"elec",ele);
                }else if(item.equals("Sazzad Hossain")){
                    fStore.collection("users").document("mksa.sazzad@gmail.com")
                            .update("home",homes,"wifi",wifis,"cooking",cook,"gass",gas
                                    ,"elec",ele);
                }else if(item.equals("Amanullah Akon")){
                    fStore.collection("users").document("amanullah15-2447@diu.edu.bd")
                            .update("home",homes,"wifi",wifis,"cooking",cook,"gass",gas
                                    ,"elec",ele);
                }else if(item.equals("Suin Chowdhury")){
                    fStore.collection("users").document("rowshan47-318@diu.edu.bd")
                            .update("home",homes,"wifi",wifis,"cooking",cook,"gass",gas
                                    ,"elec",ele);
                }else if(item.equals("Nahid Hasan")){
                    fStore.collection("users").document("nahid10-434@diu.edu.bd")
                            .update("home",homes,"wifi",wifis,"cooking",cook,"gass",gas
                                    ,"elec",ele);
                }else if(item.equals("Sujon Islam")){
                    fStore.collection("users").document("sujon15-2813@diu.edu.bd")
                            .update("home",homes,"wifi",wifis,"cooking",cook,"gass",gas
                                    ,"elec",ele);
                }else if(item.equals("Hridoy Alam")){
                    fStore.collection("users").document("hridoy15-3723@diu.edu.bd")
                            .update("home",homes,"wifi",wifis,"cooking",cook,"gass",gas
                                    ,"elec",ele);
                }else if(item.equals("Saiful Islam")){
                    fStore.collection("users").document("saiful15-2678@diu.edu.bd")
                            .update("home",homes,"wifi",wifis,"cooking",cook,"gass",gas
                                    ,"elec",ele);
                }else {
                    Toast.makeText(getActivity(),"not found",Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getActivity(),"Successfully Added all bills",Toast.LENGTH_LONG).show();
                Home.setText("");
                Wifi.setText("");
                Gass.setText("");
                Elec.setText("");
                Cooking.setText("");
                spinner.setSelection(0);

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getActivity(),"select any",Toast.LENGTH_LONG).show();
    }
    public void Data(){
        fStore.collection("users")
                .whereEqualTo("switch", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        double total=0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                total=total+document.getDouble("homecredit");
                            }
                            Data1(total);
                        }
                    }
                });
    }
    public void Data1(final double x){
        fStore.collection("savings").document("1")
        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {

                if (snapshot != null && snapshot.exists()) {
                    double hand=x-snapshot.getDouble("expenses");
                    Expend.setText(String.format("%.2f",snapshot.getDouble("expenses")));
                    HandCash.setText(String.format("%.2f",hand));

                }
            }
        });
    }
}
