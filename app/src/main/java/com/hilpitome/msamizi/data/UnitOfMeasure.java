package com.hilpitome.msamizi.data;

public enum UnitOfMeasure {
    KG("Kg"), LITRES("Lts"), PIECES("pieces");


    String strVal;
    UnitOfMeasure(String strVal){
        this.strVal = strVal;
    }
}
