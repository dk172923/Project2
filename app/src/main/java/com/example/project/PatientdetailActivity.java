package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientdetailActivity extends AppCompatActivity {

    private  EditText patientName, patientAge, patientGender;
    private Button btnSave;
    private FirebaseFirestore db;
    FirebaseUser user;
    String doctorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientdetail);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            doctorName = user.getDisplayName();
        }

        patientName = findViewById(R.id.PatientName);
        patientAge = findViewById(R.id.PatientAge);
        patientGender = findViewById(R.id.PatientGender);
        btnSave = findViewById(R.id.btnsave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePatientDetails();
            }
        });

    }
    private void savePatientDetails() {
        String name = patientName.getText().toString();
        String age = patientAge.getText().toString();
        String gender = patientGender.getText().toString();
        int Age = Integer.parseInt(age);

      /*  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String doctorEmail = user.getEmail();*/

        Patient patient = new Patient(name, age, gender, doctorName);
        patient.setDiagnosisData(new DiagnosisData("","","","",""));
       // patient.setDoctorEmail(doctorEmail);

        db.collection("patients")
                .add(patient)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Data added successfully
                        Toast.makeText(PatientdetailActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(PatientdetailActivity.this, DiagnosisActivity.class);
                                intent.putExtra("Age",Age);
                                intent.putExtra("PatientDocumentId", documentReference.getId());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        },2000);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(PatientdetailActivity.this, "Failure: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}