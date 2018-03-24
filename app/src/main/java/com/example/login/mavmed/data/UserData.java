package com.example.login.mavmed.data;

/**
 * Created by Nhi K luong on 3/16/2018.
 */

public class UserData {
    private String birthYear;
    private String fullName;
    private String email;
    private String gender;
    public UserData() {}
    public UserData(String fullName, String birthYear,String email,String gender) {
        this.fullName = fullName;
        this.birthYear = birthYear;
        this.email = email;
        this.gender = gender;
    }
    public String getBirthYear() {return birthYear;}
    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender;
    }
}
