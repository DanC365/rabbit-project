package com.sparta.djc.view;

import java.text.DecimalFormat;

public class DisplayManager {
    DecimalFormat format;
    public DisplayManager(){
        format = new DecimalFormat("###,###.##");
        System.out.println("------------------------------------------------------------------");
    }

    public void displayDetails(int month, int eatenRabbits, int deadRabbits, int bornRabbits, int bornFoxes, int totalRabbits, int totalFoxes, int maleRabbits, int femaleRabbits){
        System.out.println(format.format(eatenRabbits) + " rabbits eaten by foxes in month " + month);
        System.out.println(format.format(deadRabbits) + " rabbit deaths from age in month " + month);
        System.out.println(format.format(bornRabbits) + " new rabbits born in month " + month);
        System.out.println(format.format(bornFoxes) + " new foxes born in month " + month);
        System.out.println("After " + month + " months there are " + format.format(totalRabbits) + " rabbits");
        System.out.println("After " + month + " months there are " + format.format(totalFoxes) + " foxes");
        System.out.println("Male rabbits: " + format.format(maleRabbits) + " | Female rabbits: " + format.format(femaleRabbits));
        System.out.println("------------------------------------------------------------------");
    }

    public void maxPopulation(int MAX_POPULATION, int endPopulation, int months){
        System.out.println("Maximum population " + format.format(MAX_POPULATION) + " reached");
        System.out.println("Ended with population of " + format.format(endPopulation) + " rabbits in " + months + " months");
    }

    public void maxTime(int MAX_TIME, int endPopulation){
        System.out.println("Maximum time " + format.format(MAX_TIME) + " reached");
        System.out.println("Ended with population of " + format.format(endPopulation) + " rabbits");
    }
}
