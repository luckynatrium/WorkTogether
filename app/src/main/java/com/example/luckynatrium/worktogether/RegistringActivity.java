package com.example.luckynatrium.worktogether;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistringActivity extends AppCompatActivity {

    private EditText et_email;
    private EditText et_password;
    private EditText et_name;
    private Button btn_signup;
    private Button btn_signin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registring);

        //initialization layout's stuff
        et_email =(EditText) findViewById(R.id.atext_email);
        et_password = (EditText)findViewById(R.id.atext_email);
        et_name= (EditText) findViewById(R.id.retext_name);
        btn_signup=(Button)findViewById(R.id.rbutton_sign_up);
        btn_signin=(Button)findViewById(R.id.rbutton_sign_in);

        //initialization FirebaseAuth
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        //registration
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=et_email.getText().toString();
                String password = et_password.getText().toString();
                String name=et_name.getText().toString();
                while(email=="" || password==""){
                    Toast.makeText(RegistringActivity.this,"Fill all fields, please",Toast.LENGTH_LONG).show();
                }

                SignupUser(email,password,name);


            }
        });
        //redirection to authActivity
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistringActivity.this,AuthActivity.class);
                startActivity(intent);
            }
        });
    }
    public void SignupUser(String email,String password, String name){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("registering","Sign up successful");
                Toast.makeText(RegistringActivity.this,"Registering and SignIn is successful",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("registering","Sign up failed",e);
            }
        });
        //login in new user
        /*mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(RegistringActivity.this,"Registering and SignIn is successful",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistringActivity.this,"FUCK",Toast.LENGTH_LONG).show();
                Log.d("kek","FUCK",e);
            }
        });*/



            //Toast.makeText(RegistringActivity.this,"FUCK",Toast.LENGTH_LONG).show();
            //write info about user in database
            Map<String, String> data = new HashMap<>();
            data.put(email,name);
            db.collection("users").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(RegistringActivity.this,"Registering success",Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegistringActivity.this,"FUCk",Toast.LENGTH_LONG).show();
                }
            });

            Intent intent = new Intent(RegistringActivity.this,MainActivity.class);
            startActivity(intent);


        //Переход на главную активити программы



    }
}
