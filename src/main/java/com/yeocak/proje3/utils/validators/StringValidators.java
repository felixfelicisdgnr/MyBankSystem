package com.yeocak.proje3.utils.validators;

public class StringValidators {
    public static Boolean validateAnyBlank(String... strings){
        for (String string : strings) {
            if (string.isBlank()) {
                return true;
            }
        }
        return false;
    }
}
