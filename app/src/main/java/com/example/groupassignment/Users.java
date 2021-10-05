package com.example.groupassignment;

public class Users {

    int User_id;
    String User_Username;
    String User_Fname;
    String User_Lname;
    String User_Password;
    int User_IsTakenVaccine;
    int User_IsHascovid;
    String User_Address;
    String User_dob;
    int User_IsAdmin;
    int User_IsDoneQuiz;
    String User_Img;
    String User_Phone;
    String User_Email;
    String User_Gender;
    int User_PostCode;
    int User_IsIntrestTakenVaccine;
    String User_Passport;
    String Comorbidites;

    public Users(int user_id, String user_Username, String user_Fname, String user_Lname, String user_Password, int user_IsTakenVaccine, int user_IsHascovid, String user_Address, String user_dob, int user_IsAdmin, int user_IsDoneQuiz, String user_Img, String user_Phone, String user_Email, String user_Gender, int user_PostCode, int user_IsIntrestTakenVaccine, String user_Passport, String comorbidites) {
        User_id = user_id;
        User_Username = user_Username;
        User_Fname = user_Fname;
        User_Lname = user_Lname;
        User_Password = user_Password;
        User_IsTakenVaccine = user_IsTakenVaccine;
        User_IsHascovid = user_IsHascovid;
        User_Address = user_Address;
        User_dob = user_dob;
        User_IsAdmin = user_IsAdmin;
        User_IsDoneQuiz = user_IsDoneQuiz;
        User_Img = user_Img;
        User_Phone = user_Phone;
        User_Email = user_Email;
        User_Gender = user_Gender;
        User_PostCode = user_PostCode;
        User_IsIntrestTakenVaccine = user_IsIntrestTakenVaccine;
        User_Passport = user_Passport;
        Comorbidites = comorbidites;
    }
    //    public Users(int user_id, String user_Username, String user_Fname, String user_Lname, String user_Password, int user_IsTakenVaccine, int user_IsHascovid, String user_Address, String user_dob, int user_IsAdmin, int user_IsDoneQuiz, String user_Img, String user_Phone, String user_Email, String user_Gender, int user_PostCode, int user_IsIntrestTakenVaccine, String user_Passport,String Comorbidites) {
//        User_id = user_id;
//        User_Username = user_Username;
//        User_Fname = user_Fname;
//        User_Lname = user_Lname;
//        User_Password = user_Password;
//        User_IsTakenVaccine = user_IsTakenVaccine;
//        User_IsHascovid = user_IsHascovid;
//        User_Address = user_Address;
//        User_dob = user_dob;
//        User_IsAdmin = user_IsAdmin;
//        User_IsDoneQuiz = user_IsDoneQuiz;
//        User_Img = user_Img;
//        User_Phone = user_Phone;
//        User_Email = user_Email;
//        User_Gender = user_Gender;
//        User_PostCode = user_PostCode;
//        User_IsIntrestTakenVaccine = user_IsIntrestTakenVaccine;
//        User_Passport = user_Passport;
//        Comorbidites = Comorbidites;
//    }

    public String getComorbidites() {
        return Comorbidites;
    }

    public void setComorbidites(String comorbidites) {
        Comorbidites = comorbidites;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public String getUser_Username() {
        return User_Username;
    }

    public void setUser_Username(String user_Username) {
        User_Username = user_Username;
    }

    public String getUser_Fname() {
        return User_Fname;
    }

    public void setUser_Fname(String user_Fname) {
        User_Fname = user_Fname;
    }

    public String getUser_Lname() {
        return User_Lname;
    }

    public void setUser_Lname(String user_Lname) {
        User_Lname = user_Lname;
    }

    public String getUser_Password() {
        return User_Password;
    }

    public void setUser_Password(String user_Password) {
        User_Password = user_Password;
    }

    public int getUser_IsTakenVaccine() {
        return User_IsTakenVaccine;
    }

    public void setUser_IsTakenVaccine(int user_IsTakenVaccine) {
        User_IsTakenVaccine = user_IsTakenVaccine;
    }

    public int getUser_IsHascovid() {
        return User_IsHascovid;
    }

    public void setUser_IsHascovid(int user_IsHascovid) {
        User_IsHascovid = user_IsHascovid;
    }

    public String getUser_Address() {
        return User_Address;
    }

    public void setUser_Address(String user_Address) {
        User_Address = user_Address;
    }

    public String getUser_dob() {
        return User_dob;
    }

    public void setUser_dob(String user_dob) {
        User_dob = user_dob;
    }

    public int getUser_IsAdmin() {
        return User_IsAdmin;
    }

    public void setUser_IsAdmin(int user_IsAdmin) {
        User_IsAdmin = user_IsAdmin;
    }

    public int getUser_IsDoneQuiz() {
        return User_IsDoneQuiz;
    }

    public void setUser_IsDoneQuiz(int user_IsDoneQuiz) {
        User_IsDoneQuiz = user_IsDoneQuiz;
    }

    public String getUser_Img() {
        return User_Img;
    }

    public void setUser_Img(String user_Img) {
        User_Img = user_Img;
    }

    public String getUser_Phone() {
        return User_Phone;
    }

    public void setUser_Phone(String user_Phone) {
        User_Phone = user_Phone;
    }

    public String getUser_Email() {
        return User_Email;
    }

    public void setUser_Email(String user_Email) {
        User_Email = user_Email;
    }

    public String getUser_Gender() {
        return User_Gender;
    }

    public void setUser_Gender(String user_Gender) {
        User_Gender = user_Gender;
    }

    public int getUser_PostCode() {
        return User_PostCode;
    }

    public void setUser_PostCode(int user_PostCode) {
        User_PostCode = user_PostCode;
    }

    public int getUser_IsIntrestTakenVaccine() {
        return User_IsIntrestTakenVaccine;
    }

    public void setUser_IsIntrestTakenVaccine(int user_IsIntrestTakenVaccine) {
        User_IsIntrestTakenVaccine = user_IsIntrestTakenVaccine;
    }

    public String getUser_Passport() {
        return User_Passport;
    }

    public void setUser_Passport(String user_Passport) {
        User_Passport = user_Passport;
    }
}
