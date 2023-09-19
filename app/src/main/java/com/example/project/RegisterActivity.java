package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private TextView username, password, confirmpassword, goBackText;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(RegisterActivity.this, homeactivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);

        goBackText = findViewById(R.id.gobacktxt);
        mAuth = FirebaseAuth.getInstance();
        MaterialButton regbtn = findViewById(R.id.singupbtn);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass, confirmpass;
                username = findViewById(R.id.usernamereg);
                password = findViewById(R.id.passwordreg);
                confirmpassword = findViewById(R.id.confirmpasswordreg);
                email= username.getText().toString().trim();
                pass= password.getText().toString().trim();
                confirmpass = confirmpassword.getText().toString().trim();



                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmpass)){
                    Toast.makeText(RegisterActivity.this, "Enter confirmation password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pass.equals(confirmpass)){
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    } else { if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        // This exception is thrown if the email is already registered
                                        Toast.makeText(RegisterActivity.this, "Email is already registered.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Other registration failure
                                        Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                    }
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Passwords must be equal", Toast.LENGTH_SHORT).show();
                }
            }
        });
        goBackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}