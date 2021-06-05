package www.scientistx.saftafood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class notification_bar extends DialogFragment {
    private FirebaseListAdapter<vp_message> adapter;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ImageButton Send;
    EditText message1;
    LinearLayout layoutt;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view= inflater.inflate(R.layout.notification_bar, container, false);
        layoutt=view.findViewById(R.id.hs);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        Send=view.findViewById(R.id.send);
        message1=view.findViewById(R.id.message1);
        listView=view.findViewById(R.id.list);
        String em=auth.getInstance().getCurrentUser().getEmail();
        if(em.equals("mksa.sazzad@gmail.com")||em.equals("jayanta15-2641@diu.edu.bd")){
            message1.setVisibility(View.VISIBLE);
            Send.setVisibility(View.VISIBLE);
        }else{
            message1.setVisibility(View.INVISIBLE);
            Send.setVisibility(View.INVISIBLE);
        }
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email="SAFTA Food";
                String message= message1.getText().toString().trim();
                database.getReference("users").push().setValue(new vp_message(email,message));
                message1.setText("");
            }
        });
        displayMessage();
        return view;
    }
    public void displayMessage(){
        adapter=new FirebaseListAdapter<vp_message>(getActivity(),vp_message.class,R.layout.vp_message_list,
                database.getReference("users")) {
            @Override
            protected void populateView(View v, vp_message model, int position) {
              TextView name,mess,date;
                name=v.findViewById(R.id.user_vp);
                mess=v.findViewById(R.id.message_vp);
                date=v.findViewById(R.id.date_vp);

                name.setText(model.getName());
                mess.setText(model.getBody());
                date.setText(DateFormat.format("dd.MM.yyyy (hh:mm)",model.getTime()));
            }
        };
        listView.setAdapter(adapter);
    }

}
