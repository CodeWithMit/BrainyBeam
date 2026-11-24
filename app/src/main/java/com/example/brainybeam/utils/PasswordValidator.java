package com.example.brainybeam.utils;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator {
    private static final int Min=6;
    public static String isvalid(String password){
        if(password==null || password.isEmpty()){
            return "Password cannot be empty";
        }
        if(password.length()<Min ) {
            return "Password should be atleast 6 characters";
        }
//        boolean Uppercase=false;
//        boolean Lowercase=false;
//        boolean Digit=false;
//        boolean SpecialChar=false;
//        for(char c:password.toCharArray()){
//            if(Character.isUpperCase(c)){
//                Uppercase=true;
//            }
//            else if(Character.isLowerCase(c)){
//                Lowercase=true;
//            }
//            else if(Character.isDigit(c)){
//                Digit=true;
//            }
//            else{
//                SpecialChar=true;
//            }
//        }
//        List<String> missing = new ArrayList<>();
//        if (!Uppercase) missing.add("Uppercase");
//        if (!Lowercase) missing.add("Lowercase");
//        if (!Digit) missing.add("Digit");
//        if (!SpecialChar) missing.add("Special Character");
//
//        if (!missing.isEmpty()) {
//            return "Password should contain: " + String.join(", ", missing);
//        }
        return null;
    }
}

