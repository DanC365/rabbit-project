package com.sparta.djc.controller;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        RabbitManager rabbitManager = new RabbitManager();
        rabbitManager.startSimulation(100);
    }
}
