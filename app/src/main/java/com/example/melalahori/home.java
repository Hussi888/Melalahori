package com.example.melalahori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {

    Button logout_bt;
    FirebaseAuth mfirebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logout_bt=findViewById(R.id.logout_bt);

        logout_bt.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            FirebaseAuth.getInstance().signOut();
                                            Intent goback = new Intent(home.this, Login.class);
                                            startActivity(goback);
                                        finish();
                                        }
                                    }
        );
    }
}
