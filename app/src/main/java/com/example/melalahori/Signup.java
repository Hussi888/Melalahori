package com.example.melalahori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    private TextInputLayout emailId,password,username;
    private Button signup_bt;
    private TextView already_account;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseFirestore mfirestore= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email_signup);
        password = findViewById(R.id.password_signup);
        username = findViewById(R.id.name_signup);
        signup_bt = findViewById(R.id.signup_bt);
        already_account = findViewById(R.id.already_account);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseuser = firebaseAuth.getCurrentUser();
                if (mFirebaseuser != null) {
                    Toast.makeText(Signup.this, "You are Logged In!", Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(Signup.this, home.class);
                    startActivity(I);
                    finish();
                } else {
                    Toast.makeText(Signup.this, "Please register first", Toast.LENGTH_SHORT).show();
                }
            }
        };
        signup_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailId.getEditText().getText().toString();
                final String pass = password.getEditText().getText().toString();
                final String name = username.getEditText().getText().toString();
                if (name.isEmpty()) {
                    username.setError("Set your Username");
                    username.requestFocus();

                } else
                    username.setError(null);
                if (email.isEmpty()) {
                    emailId.setError("Provide your Email");
                    emailId.requestFocus();
                } else
                    emailId.setError(null);
                if (pass.isEmpty()) {
                    password.setError("Set your Password");
                    password.requestFocus();

                } else
                    password.setError(null);

                if (!email.isEmpty() && !pass.isEmpty() && !name.isEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(Signup.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(Signup.this.getApplicationContext(),
                                        "Signup unsuccessful: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                String userid = firebaseAuth.getCurrentUser().getUid();
                                DocumentReference docRef=mfirestore.collection("Users").document(userid);
                                Map<String,Object> user=new HashMap<>();
                                user.put("Username",name);
                                user.put("Email",email);
                                user.put("Password",pass);
                                docRef.set(user);
                                startActivity(new Intent(Signup.this, home.class));
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Signup.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Signup.this, Login.class);
                startActivity(I);
               finish();
            }
        });
}
}