package com.sparta.djc.controller;

import com.sparta.djc.model.Fox;
import com.sparta.djc.model.Rabbit;
import com.sparta.djc.view.DisplayManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RabbitManager {
    private List<Rabbit> rabbits;
    private Random random;
    private int time;
    private int maleCount;
    private int femaleCount;

    private List<Fox> foxes;
    List<Fox> newBirthsFox;
    List<Rabbit> newBirthsRabbit;

    private final int MAX_POPULATION;

    public RabbitManager() {
        MAX_POPULATION = 30000000;
        random = new Random();
    }

    public RabbitManager(int maxPopulation) {
        MAX_POPULATION = maxPopulation;
        random = new Random();
    }

    public void startSimulation(final int MAX_TIME) {
        time = 0;


        rabbits = new ArrayList<>();
        rabbits.add(new Rabbit(true));
        rabbits.add(new Rabbit(false));
        maleCount = 1;
        femaleCount = 1;

        foxes = new ArrayList<>();
        foxes.add(new Fox(true));
        foxes.add(new Fox(false));

        DisplayManager displayManager = new DisplayManager();

        while (time < MAX_TIME && rabbits.size() < MAX_POPULATION) {
            time++;

            int eatenRabbits=0;
            if(time>=13) {
                eatenRabbits = feedFoxes();
            }

            if (hasMaleFox()) {
                foxBreeding();
            }

            if (maleCount >= 1) {
                giveBirth();
            }

            int deadRabbits = increaseAgeRabbits();
            if(time>=13){
                increaseAgeFoxes();
            }
            foxes.addAll(newBirthsFox);
            rabbits.addAll(newBirthsRabbit);

            displayManager.displayDetails(time, eatenRabbits, deadRabbits, newBirthsRabbit.size(), newBirthsFox.size(), rabbits.size(), foxes.size(), maleCount, femaleCount);
        }

        if (time == MAX_TIME) {
            displayManager.maxTime(MAX_TIME, rabbits.size());
        } else if (rabbits.size() > MAX_POPULATION) {
            displayManager.maxPopulation(MAX_POPULATION, rabbits.size(), time);
        }

    }

    private void giveBirth() {
        newBirthsRabbit = new ArrayList<>();
        for (Rabbit rabbit : rabbits) {
            if (rabbit.getAge() >= 3 && rabbit.isFemale()) {
                int children = random.nextInt(14) + 1;
                birthRabbits(children);
            }
        }
    }

    private void birthRabbits(int children){
        for (int i = 0; i < children; i++) {
            Rabbit newRabbit = new Rabbit();
            newBirthsRabbit.add(newRabbit);
            if (newRabbit.isFemale()) {
                femaleCount++;
            } else {
                maleCount++;
            }
        }
    }

    private int increaseAgeRabbits() {
        List<Rabbit> deadRabbits = new ArrayList<>();
        for (Rabbit rabbit : rabbits) {
            if (rabbit.incrementAge()) {
                deadRabbits.add(rabbit);
                if (rabbit.isFemale()) {
                    femaleCount--;
                } else {
                    maleCount--;
                }
            }
        }

        rabbits.removeAll(deadRabbits);
        return deadRabbits.size();
    }

    private void increaseAgeFoxes(){
        for (Fox fox : foxes) {
            fox.incrementAge();
        }
    }

    private boolean hasMaleFox() {
        for (Fox fox : foxes) {
            if (!fox.isFemale()) {
                return true;
            }
        }
        return false;
    }


    private void foxBreeding() {
        newBirthsFox = new ArrayList<>();
        for (Fox fox : foxes) {
            if (fox.canBreed(time)) {
                int childrenCount = random.nextInt(10) + 1;
                for (int i = 0; i < childrenCount; i++) {
                    Fox newFox = new Fox();
                    newBirthsFox.add(newFox);
                }
            }
        }

    }

    private int feedFoxes() {
        int eatenRabbits = 0;
        for (Fox fox : foxes) {
            int numberEaten = random.nextInt(20) + 1;
            eatRabbits(numberEaten);
            eatenRabbits+=numberEaten;
        }
        return eatenRabbits;
    }

    private void eatRabbits(int numberEaten){
        for (int i = 0; i < numberEaten; i++) {
            int rabbitToKill = random.nextInt(rabbits.size());
            if (rabbits.get(rabbitToKill).isFemale()) {
                femaleCount--;
            } else {
                maleCount--;
            }
            rabbits.remove(rabbitToKill);

        }
    }

}
