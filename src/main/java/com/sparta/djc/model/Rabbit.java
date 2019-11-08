package com.sparta.djc.model;

import java.util.Random;

public class Rabbit extends Animal{

    public Rabbit(){
        isFemale = (new Random()).nextBoolean();
        age = 0;
    }

    public Rabbit(Boolean isFemale){
        this.isFemale = isFemale;
        age = 0;
    }

    public Rabbit(Boolean isFemale, int age){
        this.age = age;
        this.isFemale = isFemale;
    }

    public Rabbit(int age){
        isFemale = (new Random()).nextBoolean();
        this.age = age;
    }

    public boolean incrementAge(){
        age++;
        return age>=18;
    }



}
