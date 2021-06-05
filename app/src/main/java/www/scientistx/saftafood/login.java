package www.scientistx.saftafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    EditText Email,Pass;
    ProgressBar process;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email= findViewById(R.id.email);
        Pass= findViewById(R.id.password);
        process= findViewById(R.id.progressBar);
    }
    public void loginn(View view) {

        String email= Email.getText().toString().trim();
        String password= Pass.getText().toString().trim();
        mAuth= FirebaseAuth.getInstance();
        if(TextUtils.isEmpty(email)){
            Email.setError("Enter Email");
            return;
        }
        if(TextUtils.isEmpty(password)){
            Pass.setError("Enter password");
            return;
        }

        process.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String Email=mAuth.getInstance().getCurrentUser().getEmail();
                            // Sign in success, update UI with the signed-in user's information
                            if(Email.equals("mksa.sazzad@gmail.com")||Email.equals("jayanta15-2641@diu.edu.bd")){
                                Toast.makeText(login.this,"Logged In Successfull",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),admin.class));
                                finish();
                            }else{
                            Toast.makeText(login.this,"Logged In Successfull",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(login.this, "Wrong Email and password.",Toast.LENGTH_SHORT).show();
                            process.setVisibility(View.INVISIBLE);
                        }

                        // ...
                    }
                });

    }
    public void forget(View view) {
        startActivity(new Intent(getApplicationContext(),forget.class));
    }
    public void create(View view) {
        startActivity(new Intent(getApplicationContext(),create.class));
    }
}
