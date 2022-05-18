package com.yeocak.proje3.model;

public class Exchange {
    private String name;
    private Double ratio;

    public Exchange(String name, Double howTl) {
        this.name = name;
        this.ratio = howTl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }


}
