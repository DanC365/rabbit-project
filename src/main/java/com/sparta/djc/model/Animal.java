package com.sparta.djc.model;

import java.util.Random;

public abstract class Animal {
    protected boolean isFemale;
    protected int age=0;



    public abstract boolean incrementAge();


    public int getAge(){
        return age;
    }

    public boolean isFemale(){
        return isFemale;
    }

}
