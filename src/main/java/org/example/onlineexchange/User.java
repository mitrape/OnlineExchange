package org.example.onlineexchange;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username;//0
    private String password;//1
    //repeat password 2
    private String firstname; //3
    private String lastname;//4
    private String email;//5
    private String phoneNumber;//6
    //code7
    private Image profilePhoto ;
    private String imageName;
    public boolean[] correctInfo = new boolean[8];

    public User(String username, String password, String firstname, String lastname, String email, String phoneNumber, WritableImage image){
        setUsername(username);
        setEmail(email);
        setFirstname(firstname);
        setLastname(lastname);
        setPassword(password);
        setPhoneNumber(phoneNumber);
        setProfilePhoto(image);
    }
    public User (){
    }

    public void setProfilePhoto(Image profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
    public void setProfilePhoto(String imageName){
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public Image getProfilePhoto() {
        return profilePhoto;
    }

    public void setUsername(String username) {
        boolean sw =true;
        if(username.length()>=3 && username.length()<=30){
            for (int i = 0; i < username.length() && sw; i++) {
                if((username.charAt(i)>='a' && username.charAt(i)<='z') || (username.charAt(i)>='A' && username.charAt(i)<='Z') || (username.charAt(i)=='.') || (username.charAt(i)>='0' && username.charAt(i)<='9'))
                    sw=true;
                else
                    sw=false;
            }
            if(sw) {
                correctInfo[0] = true;
                this.username = username;
            }
            else
                correctInfo[0]=false;
        }
        else
            correctInfo[0]=false;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        boolean specialCharSw = false;
        boolean numberSw = false;
        boolean upperSw =false;
        boolean lowerSw =false;
        if(password.length()>=8){
            for (int i = 0; i < password.length(); i++) {
                if(password.charAt(i)>='0' && password.charAt(i)<='9')
                    numberSw=true;
                if(password.charAt(i)>='a' && password.charAt(i) <='z')
                    lowerSw=true;
                if(password.charAt(i)>='A' && password.charAt(i) <='Z')
                    upperSw=true;
                if(password.charAt(i)=='@' || password.charAt(i)=='#' || password.charAt(i)=='$' || password.charAt(i)=='%' || password.charAt(i)=='*' || password.charAt(i)=='&' || password.charAt(i)=='-' || password.charAt(i)=='_')
                    specialCharSw=true;
            }
            if(specialCharSw && numberSw && upperSw && lowerSw) {
                correctInfo[1] = true;
                this.password=password;
            }
            else
                correctInfo[1]=false;
        }
        else
            correctInfo[1]=false;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        boolean sw=true;
        if(firstname.length()<=50){
            for (int i = 0; i < firstname.length() && sw; i++) {
                if(!(firstname.charAt(i)>='a' && firstname.charAt(i)<='z')){
                    sw=false;
                }
            }
            if(sw) {
                correctInfo[3] = true;
                this.firstname = firstname;
            }
            else
                correctInfo[3] = false;
        }
        else
            correctInfo[3]=false;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        boolean sw=true;
        if(lastname.length()<=50){
            for (int i = 0; i < lastname.length() && sw; i++) {
                if(!(lastname.charAt(i)>='a' && lastname.charAt(i)<='z')){
                    sw=false;
                }
            }
            if(sw) {
                correctInfo[4] = true;
                this.lastname = lastname;
            }
            else
                correctInfo[4] = false;
        }
        else
            correctInfo[4]=false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String regexPat = "^(?=.[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})(?!.[<>])[^<>]*$";
        Pattern pattern = Pattern.compile(regexPat);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            this.email = email;
            correctInfo[5] = true;
        }
        else {
            correctInfo[5] = false;
        }

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.length()==11){
            correctInfo[6]=true;
            this.phoneNumber=phoneNumber;
        }
        else
            correctInfo[6]=false;
    }
}