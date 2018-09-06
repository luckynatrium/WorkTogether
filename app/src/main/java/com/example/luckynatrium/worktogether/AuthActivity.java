package com.example.luckynatrium.worktogether;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthActivity extends AppCompatActivity {


    private EditText aet_email;
    private EditText aet_password;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Button abtn_signup;
    private Button abtn_signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        aet_email=findViewById(R.id.atext_email);
        aet_password=findViewById(R.id.atext_password);
        abtn_signin=findViewById(R.id.abutton_sign_in);
        abtn_signup=findViewById(R.id.abutton_sign_up);

        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        abtn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthActivity.this,RegistringActivity.class);
                startActivity(intent);
            }
        });

        abtn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = aet_email.getText().toString();
                String password = aet_password.getText().toString();
                SigninUser(email,password);
            }
        });

    }
    public void SigninUser(String email,String password){

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("sign_in", "Sign in successful");
                String user_id = mAuth.getCurrentUser().getUid();
                if (user_id != null) {
                    db.collection("users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if (!document.exists())
                                Log.d("sign_in", "User is not in database", new Exception());
                        }
                    });
                }
                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(intent);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AuthActivity.this,"Invalid email or password",Toast.LENGTH_LONG).show();
                aet_email.setText("");
                aet_password.setText("");
            }
        });
    }
}
