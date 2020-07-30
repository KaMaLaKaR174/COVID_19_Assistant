package com.example.kamalakar.covidtracker.Models;

public class UserProfile {

    public String registerEmail,registerName,registerPhone,registerBloodGroup,registerAge,checkDonor,registerAdd1,registerGender;

    public UserProfile() {

    }

    public UserProfile(String registerEmail, String registerName, String registerPhone, String registerBloodGroup,String registerGender,
                       String registerAge,String registerAdd1,String checkDonor) {
        this.registerEmail = registerEmail;
        this.registerName = registerName;
        this.registerPhone = registerPhone;
        this.registerBloodGroup = registerBloodGroup;
        this.registerAge = registerAge;
        this.checkDonor=checkDonor;
        this.registerAdd1=registerAdd1;
        this.registerGender=registerGender;

    }

    public String getRegisterEmail() {
        return registerEmail;
    }

    public String getRegisterName() {
        return registerName;
    }

    public String getRegisterPhone() {
        return registerPhone;
    }

    public String getRegisterBloodGroup() {
        return registerBloodGroup;
    }

    public String getRegisterAge() {
        return registerAge;
    }

    public String getCheckDonor(){return checkDonor;}

    public String getRegisterGender(){return registerGender;}

    public String getRegisterAdd1(){return registerAdd1;}


}
