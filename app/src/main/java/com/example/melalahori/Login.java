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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputLayout emailId, password;
    Button login_bt;
    TextView no_account;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mfirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        login_bt = findViewById(R.id.login_bt);
        no_account = findViewById(R.id.no_account);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFireBaseuser = mfirebaseAuth.getCurrentUser();
                if (mFireBaseuser != null) {
                    Toast.makeText(Login.this, "You are Logged In!", Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(Login.this, home.class);
                    startActivity(I);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Please Login first", Toast.LENGTH_SHORT).show();
                }
            }
        };
        login_bt.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String email = emailId.getEditText().getText().toString();
                                            String pass = password.getEditText().getText().toString();
                                            if (email.isEmpty()) {
                                                emailId.setError("Provide your Email first!");
                                                emailId.requestFocus();

                                            }else
                                                emailId.setError(null);
                                            if (pass.isEmpty()) {
                                                password.setError("Set your password");
                                                password.requestFocus();
                                            } else
                                               password.setError(null);
                                            if (!email.isEmpty() && !pass.isEmpty()) {

                                                mfirebaseAuth.signInWithEmailAndPassword(email, pass)
                                                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                        if (!task.isSuccessful()) {
                                                                            Toast.makeText(Login.this.getApplicationContext(), "Login Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            startActivity(new Intent(Login.this, home.class));
                                                                            finish();
                                                                        }
                                                                    }
                                                                }
                                                        );
                                            }
                                        }
                                    }
        );
        no_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(Login.this,Signup.class);
                startActivity(I);
                finish();
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        mfirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
