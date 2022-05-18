package com.yeocak.proje3.model;

public enum Role {
    MUSTERI,
    TEMSILCI,
    MUDUR;

    public Boolean isAuthorized(){
        return this != Role.MUSTERI;
    }
}
