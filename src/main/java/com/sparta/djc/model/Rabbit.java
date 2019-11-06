package com.sparta.djc.model;

public abstract class Rabbit implements Runnable{
    private long startTime;

    public Rabbit(){
        startTime = System.nanoTime();
    }

}
