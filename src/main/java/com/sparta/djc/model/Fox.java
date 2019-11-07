package com.sparta.djc.model;

import java.util.Random;

public class Fox extends Animal{
    public int lastBirth=-2;

    public Fox(){
        isFemale = (new Random()).nextBoolean();
        age = 0;
    }

    public Fox(Boolean isFemale){
        this.isFemale = isFemale;
        age = 0;
    }

    public Fox(Boolean isFemale, int age){
        this.age = age;
        this.isFemale = isFemale;
    }

    public Fox(int age){
        isFemale = (new Random()).nextBoolean();
        this.age = age;
    }

    @Override
    public boolean incrementAge() {
        age++;
        return false;
    }

    public boolean canBreed(int time){
        if(age<10||!isFemale){
            return false;
        }
        if(time-lastBirth>=12){
            lastBirth=time;
            return true;
        }else{
            return false;
        }
    }

}
