package com.yeocak.proje3.model;
public enum Months {
    OCAK(0),
    SUBAT(1),
    MART(2),
    NISAN(3),
    MAYIS(4),
    HAZIRAN(5),
    TEMMUZ(6),
    AGUSTOS(7),
    EYLUL(8),
    EKIM(9),
    KASIM(10),
    ARALIK(11);

    public final int index;

    Months(int i) {
        this.index = i;
    }

    public static Months get(int i){
        return Months.values()[i];
    }

    public static Months get(String value){
        return Months.valueOf(value);
    }

    public Months getNextMonthWithMod(){
        int newIndex = (this.index + 1) % 12;
        return Months.get(newIndex);
    }
}
