package com.hilpitome.msamizi.data;

public enum UnitOfMeasure {
    KG("Kg"), LITRES("Lts"), PIECES("pieces");
    private String strVal;
    UnitOfMeasure(String strVal){
        this.strVal = strVal;
    }

    public String getStrVal() {
        return strVal;
    }
}
