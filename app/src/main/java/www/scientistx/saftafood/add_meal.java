package www.scientistx.saftafood;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class add_meal extends Fragment {
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
Button Save,AddGuest,AddCredit;
CheckBox Sazzad,Aman,Jay,Sagor,Suin,Hridoy,Sujon,Ayon;
TextView Date,MealRate,HandCash;
EditText Cost;
ProgressBar Pro;
public String month,date;
public int i;
    public add_meal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_meal, container, false);
        Save=view.findViewById(R.id.ok);
        firebaseFirestore=FirebaseFirestore.getInstance();
        Pro=view.findViewById(R.id.progressBar4);
        MealRate=view.findViewById(R.id.mealrate111);
        AddGuest=view.findViewById(R.id.addguest);
        AddCredit=view.findViewById(R.id.addcredit);
        HandCash=view.findViewById(R.id.handcash);
        //..........................................................
        Sazzad=view.findViewById(R.id.sazzad);
        Aman=view.findViewById(R.id.aman);
        Jay=view.findViewById(R.id.jay);
        Sagor=view.findViewById(R.id.sagor);
        Hridoy=view.findViewById(R.id.hridoy);
        Suin=view.findViewById(R.id.suin);
        Ayon=view.findViewById(R.id.ayon);
        Sujon=view.findViewById(R.id.sujon);
        //.........................................................
        Date=view.findViewById(R.id.date);
        Cost=view.findViewById(R.id.cost);
        java.util.Date d= new Date();
        month=String.format("%tb",d)+" "+String.format("%tY",d);
        date=String.format("%te",d);
        Date.setText("Today: "+date+" "+month);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMeal();
            }
        });
        AddGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_guest addGuest=new add_guest();
                addGuest.show(getFragmentManager(),"add guest");
            }
        });
        AddCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_credit ad=new add_credit();
                ad.show(getFragmentManager(),"add credit");
            }
        });
        double mealrate=getArguments().getDouble("mealrate");
        calculate();
        String meal=String.format("%.2f",mealrate);
        String handcash=String.format("%.2f",getArguments().getDouble("handcash"));
        MealRate.setText(meal);
        HandCash.setText(handcash);
        return view;
    }
    public void AddMeal(){
        Pro.setVisibility(View.VISIBLE);
        firebaseFirestore=FirebaseFirestore.getInstance();
        String cos=Cost.getText().toString().trim();
        if(cos.isEmpty()){
            Cost.setError("Empty");
            return;
        }
        int costs=Integer.parseInt(Cost.getText().toString().trim());
        int sazzad=0,aman=0,jay=0,sujon=0,ayon=0,suin=0,sagor=0,hridoy=0;
        if(Sazzad.isChecked()){
            sazzad=1;
            firebaseFirestore.collection("users").document("mksa.sazzad@gmail.com")
                    .update("totalmeal", FieldValue.increment(1));
        }if(Jay.isChecked()){
            jay=1;
            firebaseFirestore.collection("users").document("jayanta15-2641@diu.edu.bd")
                    .update("totalmeal", FieldValue.increment(1));
        }if(Sujon.isChecked()){
            sujon=1;
            firebaseFirestore.collection("users").document("sujon15-2813@diu.edu.bd")
                    .update("totalmeal", FieldValue.increment(1));
        }if(Ayon.isChecked()){
            ayon=1;
            firebaseFirestore.collection("users").document("nahid10-434@diu.edu.bd")
                    .update("totalmeal", FieldValue.increment(1));
        }if(Suin.isChecked()){
            suin=1;
            firebaseFirestore.collection("users").document("rowshan47-318@diu.edu.bd")
                    .update("totalmeal", FieldValue.increment(1));
        }if(Sagor.isChecked()){
            sagor=1;
            firebaseFirestore.collection("users").document("saiful15-2678@diu.edu.bd")
                    .update("totalmeal", FieldValue.increment(1));
        }if(Hridoy.isChecked()){
            hridoy=1;
            firebaseFirestore.collection("users").document("hridoy15-3723@diu.edu.bd")
                    .update("totalmeal", FieldValue.increment(1));
        }if(Aman.isChecked()){
            aman=1;
            firebaseFirestore.collection("users").document("amanullah15-2447@diu.edu.bd")
                    .update("totalmeal", FieldValue.increment(1));
        }
        final int total=sazzad+jay+sujon+ayon+suin+sagor+hridoy+aman;
        DocumentReference db=firebaseFirestore.collection("meal").document(month);
        db.update(date,costs).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DocumentReference df=firebaseFirestore.collection("meal").document(month)
                        .collection("totalmeal").document(month);
                df.update(date,total).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(),"Meal Update Successfully",Toast.LENGTH_LONG).show();
                        Pro.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Connection Error",Toast.LENGTH_LONG).show();
                Pro.setVisibility(View.INVISIBLE);
            }
        });
        Sazzad.setChecked(false);
        Aman.setChecked(false);
        Jay.setChecked(false);
        Sagor.setChecked(false);
        Suin.setChecked(false);
        Ayon.setChecked(false);
        Sujon.setChecked(false);
        Hridoy.setChecked(false);
        Cost.setText("");

    }
    public void calculate(){
        firebaseFirestore.collection("meal").document(month)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                            i=0;
                            double total=0;
                            while (i<=31){
                                if(snapshot.getDouble(String.valueOf(i))!=null){
                                    total=total+snapshot.getDouble(String.valueOf(i));
                                }
                                i++;
                            }
                            set(total);
                    }
                });
    }
    public void set(final double x){
        firebaseFirestore.collection("users")
                .whereEqualTo("switch", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            double total = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               total = total +document.getDouble("mealcredit");
                            }
                            String hand=String.format("%.2f",(total-x));
                            HandCash.setText(hand);
                        } else {
                            Toast.makeText(getActivity(),"Not found",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
