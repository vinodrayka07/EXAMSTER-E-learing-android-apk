package com.example.examaster;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().hide();

      
        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser CurrentUser = mAuth.getCurrentUser();
                if (CurrentUser == null){
                    Intent ilogin =new Intent(dashboard.this , homeActivity.class);
                    startActivity(ilogin);
                    finish();

                }
                else {
                    Intent ihome =new Intent(dashboard.this, homeActivity.class );
                    startActivity(ihome);
                    finish();

                }


            }
        }, 4000);



    }
}