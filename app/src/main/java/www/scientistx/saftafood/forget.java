package www.scientistx.saftafood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget extends AppCompatActivity {
    EditText email;
    TextView text;
    ProgressBar progress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        email= findViewById(R.id.text1);
        text= findViewById(R.id.textView5);
        progress= findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();

    }
    public void send(View view){
        String Email= email.getText().toString().trim();
        if(TextUtils.isEmpty(Email)){
            email.setError("Enter vaild Email");
        }else{
            progress.setVisibility(View.VISIBLE);

            mAuth.sendPasswordResetEmail(Email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                text.setText("***Please Check your email");
                                progress.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

        }

    }
}
