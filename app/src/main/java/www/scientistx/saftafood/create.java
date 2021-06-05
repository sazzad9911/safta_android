package www.scientistx.saftafood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class create extends AppCompatActivity {
    EditText User,Name,Emails,Phone,Address,Date,Password;
    ProgressBar prog;
    FirebaseAuth fAuth;
    private ConstraintLayout Show;
    private ImageButton Closerr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        User= findViewById(R.id.user);
        Name= findViewById(R.id.name);
        Emails= findViewById(R.id.email);
        Phone= findViewById(R.id.phone);
        Address= findViewById(R.id.address);
        Date= findViewById(R.id.date);
        Password= findViewById(R.id.passwords);
        prog= findViewById(R.id.progressBar3);
        fAuth= FirebaseAuth.getInstance();
        Show=findViewById(R.id.show);
        Closerr=findViewById(R.id.closerrr);

    }
    public void signup(View view) {
        String Username= User.getText().toString().trim();
        String FullName= Name.getText().toString().trim();
        String EmailAddress= Emails.getText().toString().trim();
        String PhoneNumber= Phone.getText().toString().trim();
        String Addresses= Address.getText().toString().trim();
        String Date_of_Birth= Date.getText().toString().trim();
        String Passwords= Password.getText().toString().trim();

        if(TextUtils.isEmpty(Username)){
            User.setError("User Name is empty");
        }
        if (TextUtils.isEmpty(FullName)){
            Name.setError("Name is empty");
        }
        Show.setVisibility(View.VISIBLE);
        Closerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Show.setVisibility(View.INVISIBLE);
            }
        });
    }
}
