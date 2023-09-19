package com.example.project;

public class DiagnosisData {
    private String ratiodiag;
    private String pocketdiag;
    private String selectedOption;
    private String roundedBoneLoss;
    private String calcivalue;

    public DiagnosisData() {
        // Default constructor required for Firestore
    }

    public DiagnosisData(String ratiodiag, String pocketdiag, String selectedOption, String roundedBoneLoss, String calcivalue) {
        this.ratiodiag = ratiodiag;
        this.pocketdiag = pocketdiag;
        this.selectedOption = selectedOption;
        this.roundedBoneLoss = roundedBoneLoss;
        this.calcivalue = calcivalue;
    }

    public String getRatiodiag() {
        return ratiodiag;
    }

    public void setRatiodiag(String ratiodiag) {
        this.ratiodiag = ratiodiag;
    }

    public String getPocketdiag() {
        return pocketdiag;
    }

    public void setPocketdiag(String pocketdiag) {
        this.pocketdiag = pocketdiag;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getRoundedBoneLoss() {
        return roundedBoneLoss;
    }

    public void setRoundedBoneLoss(String roundedBoneLoss) {
        this.roundedBoneLoss = roundedBoneLoss;
    }

    public String getCalcivalue() {
        return calcivalue;
    }

    public void setCalcivalue(String calcivalue) {
        this.calcivalue = calcivalue;
    }
}
