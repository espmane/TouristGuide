package com.hectorpetersen.touristguideapi.Validation;

public class Validation {

    public static void ValidateName(String name){

        if (name == null){
            throw new IllegalArgumentException("Name can not be empty.");
        }

        if (!name.matches("[a-zA-ZæøåÆØÅ ]+")){
            throw new IllegalArgumentException("Name contains invalid characters");
        }
    }




}
