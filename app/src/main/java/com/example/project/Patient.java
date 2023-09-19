package com.example.project;
public class Patient {
    private String name;
    private String age;
    private String gender;
    private String doctorName;
    private DiagnosisData diagnosisData;

    public Patient() {
        // Default constructor required for Firestore
    }

    public Patient(String name, String age, String gender, String doctorName) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.doctorName = doctorName;
        this.diagnosisData = new DiagnosisData("","","","","");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDoctorEmail() {
        return doctorName;
    }

    public void setDoctorEmail(String doctorName) {
        this.doctorName = doctorName;
    }

    public DiagnosisData getDiagnosisData() {
        return diagnosisData;
    }
    public void setDiagnosisData(DiagnosisData diagnosisData){
        this.diagnosisData = diagnosisData;
    }
}
