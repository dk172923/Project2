package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

public class DiagnosisActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    EditText radigraph, calculatorforage, rootlength, bonelossvalue;
    double result, boneLossValue;
    String patientDocumentId,selectedOption, ratiodiag, pocketdiag, calcivalue, roundedBoneLoss;
    Button calculate, nextactivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        radigraph = findViewById(R.id.num);
        rootlength = findViewById(R.id.den);
        bonelossvalue = findViewById(R.id.boneloseval);
        calculatorforage = findViewById(R.id.editcalcivalue);
        calculate = findViewById(R.id.calbtn);
        nextactivity = findViewById(R.id.nextbtnnsave);

        db=FirebaseFirestore.getInstance();

        patientDocumentId = getIntent().getStringExtra("PatientDocumentId");

        // Retrieve patient's age from the intent extras
        Intent intent = getIntent();
        int age = intent.getIntExtra("Age",0);

        setupRadioGroup();

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (age != 0) {
                    result = boneLossValue / age;
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    calcivalue = decimalFormat.format(result);
                    calculatorforage.setText(calcivalue);
                } else {
                    calculatorforage.setText("N/A"); // Display a message indicating age is not available
                }


                CheckBox check1 = findViewById(R.id.checkbox1);
                CheckBox check2 = findViewById(R.id.checkbox2);
                CheckBox check3 = findViewById(R.id.checkbox3);
                CheckBox check4 = findViewById(R.id.checkbox4);

                check1.setChecked(false);
                check2.setChecked(false);
                check3.setChecked(false);
                check4.setChecked(false);

                if(result < 0.5){
                    check1.setChecked(true);
                    ratiodiag = "Secure";
                } else if (result >= 0.5 && result <= 1) {
                    check2.setChecked(true);
                    ratiodiag = "Doubtful";
                } else if (result > 1) {
                    check3.setChecked(true);
                    ratiodiag = "Poor";
                }
                else {check4.setChecked(true);
                ratiodiag = "Very Poor";}
            }
        });

        nextactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiagnosisData();
            }
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateBoneLoss();
            }
        };

        radigraph.addTextChangedListener(textWatcher);
        rootlength.addTextChangedListener(textWatcher);
    }

    void calculateBoneLoss() {
        String radiographText = radigraph.getText().toString().trim();
        String rootLengthText = rootlength.getText().toString().trim();

        if (!radiographText.isEmpty() && !rootLengthText.isEmpty()) {
            double radiographValue = Double.parseDouble(radiographText);
            double rootLengthValue = Double.parseDouble(rootLengthText);

            boneLossValue = (radiographValue * 100) / rootLengthValue;
            DecimalFormat decimalFormat = new DecimalFormat("0.00"); // Format to 3 decimal places
            roundedBoneLoss = decimalFormat.format(boneLossValue);
            bonelossvalue.setText(roundedBoneLoss);


        } else {
            bonelossvalue.setText("");
        }
    }
    void setupRadioGroup() {
        RadioGroup radioGroupOptions = findViewById(R.id.radioGroup);

        radioGroupOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedOption = selectedRadioButton.getText().toString();
                // Do something with the selected option
                Toast.makeText(getApplicationContext(), "Selected: " + selectedOption, Toast.LENGTH_LONG).show();
                if(selectedOption.equals("<= 5mm")){
                    pocketdiag = "Secure";
                } else if (selectedOption.equals("6 - 7 mm")) {
                    pocketdiag = "Doubtful";
                } else if (selectedOption.equals(">= 8 mm")) {
                    pocketdiag = "Poor";
                }
                else {
                    pocketdiag = "Very Poor";
                }
            }
        });
    }
    void saveDiagnosisData() {
        DiagnosisData diagnosisData = new DiagnosisData(ratiodiag, pocketdiag,selectedOption, roundedBoneLoss, calcivalue);

        /*DiagnosisData diagnosisData = new DiagnosisData();
        diagnosisData.setRatiodiag(ratiodiag);
        diagnosisData.setPocketdiag(pocketdiag);
        diagnosisData.setSelectedOption(selectedOption);
        diagnosisData.setRoundedBoneLoss(roundedBoneLoss);
        diagnosisData.setCalcivalue(calcivalue);*/

        db.collection("patients")
                .document(patientDocumentId)
                .update("diagnosisData", diagnosisData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DiagnosisActivity.this, "Diagnosis Data Saved ", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DiagnosisActivity.this, "Failure: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
