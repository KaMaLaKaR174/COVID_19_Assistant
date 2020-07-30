package com.example.kamalakar.covidtracker.Models;

public class Inventory {
    public String bloodGroup;
    public String donor;

    public Inventory(String bloodGroup, String donor) {
        this.bloodGroup = bloodGroup;
        this.donor = donor;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }
}
