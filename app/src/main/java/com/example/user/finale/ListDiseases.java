package com.example.user.finale;

public class ListDiseases {
    String diseaseName;
    String diseaseDesc;
    String diseaseSymptoms;
    String diseaseTreatment;


    public ListDiseases(String name, String desc, String symptoms,String treatment) {
        this.diseaseName = name;
        this.diseaseDesc = desc;
        this.diseaseSymptoms = symptoms;
        this.diseaseTreatment = treatment;
    }


    public String getDiseaseName() {
        return diseaseName;
    }

    public String getDiseaseDesc() {
        return diseaseDesc;
    }

    public String getDiseaseSymptoms() {
        return diseaseSymptoms;
    }

    public String getDiseaseTreatment() {
        return diseaseTreatment;
    }

}
