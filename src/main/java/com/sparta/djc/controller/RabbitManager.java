package com.sparta.djc.controller;

import com.sparta.djc.model.Fox;
import com.sparta.djc.model.Rabbit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RabbitManager {
    private List<Rabbit> rabbits;
    private Random random;
    private int time;
    private int maleCount;
    private int femaleCount;
    private DecimalFormat format;

    private List<Fox> foxes;
    List<Fox> newBirthsFox;
    List<Rabbit> newBirthsRabbit;

    private final long MAX_POPULATION = 35000000;

    public void startSimulation(final int MAX_TIME) {
        time = 0;
        random = new Random();
        rabbits = new ArrayList<>();
        rabbits.add(new Rabbit(true));
        rabbits.add(new Rabbit(false));
        maleCount = 1;
        femaleCount = 1;

        foxes = new ArrayList<>();
        foxes.add(new Fox(true));
        foxes.add(new Fox(false));

        format = new DecimalFormat("###,###.##");



        while (time < MAX_TIME && rabbits.size() < MAX_POPULATION) {
            time++;

            feedFoxes();


            Thread foxBirthThread = new Thread(() -> foxBreeding());
            Thread rabbitBirthThread = new Thread(() -> giveBirth());
            Thread increaseAgeThread = new Thread(() -> increaseAge());

            foxBirthThread.start();
            rabbitBirthThread.start();
            increaseAgeThread.start();

            try {
                foxBirthThread.join();
                rabbitBirthThread.join();
                increaseAgeThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(format.format(newBirthsRabbit.size()) + " new rabbit children in month " + time);
            System.out.println(format.format(newBirthsFox.size()) + " new fox children in month " + time);

            foxes.addAll(newBirthsFox);
            rabbits.addAll(newBirthsRabbit);

            System.out.println("After " + time + " months, there are " + format.format(foxes.size()) + " foxes");
            System.out.println("After " + time + " months, there are " + format.format(rabbits.size()) + " rabbits");
            System.out.println("Male rabbits: " + format.format(maleCount) + "| Female rabbits: " + format.format(femaleCount));
            System.out.println("------------------------------------------------------------------");
        }

        if (time == MAX_TIME) {
            System.out.println("Maximum time " + MAX_TIME + " reached");
        } else if (rabbits.size() > MAX_POPULATION) {
            System.out.println("Maximum population " + format.format(MAX_POPULATION) + " reached in " + time + " months");
        }

    }

    private void giveBirth() {
        newBirthsRabbit = new ArrayList<>();
        if (maleCount > 0) {
            for (Rabbit rabbit : rabbits) {
                if (rabbit.getAge() >= 3 && rabbit.isFemale()) {
                    int children = random.nextInt(14) + 1;
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
            }
        }
    }


    private void increaseAge() {
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
        for (Fox fox : foxes) {
            fox.incrementAge();
        }
        System.out.println(format.format(deadRabbits.size()) + " rabbit deaths from age in month " + time);
        rabbits.removeAll(deadRabbits);
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
        if (hasMaleFox()) {
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
    }

    private void feedFoxes() {
        int eatenRabbits = 0;
        for (Fox fox : foxes) {
            if (fox.getAge() >= 10) {
                int numberEaten = random.nextInt(20) + 1;
                for (int i = 0; i < numberEaten; i++) {
                    int rabbitToKill = random.nextInt(rabbits.size());
                    if (rabbits.get(rabbitToKill).isFemale()) {
                        femaleCount--;
                    } else {
                        maleCount--;
                    }
                    rabbits.remove(rabbitToKill);
                    eatenRabbits++;
                }
            }
        }
        System.out.println(format.format(eatenRabbits) + " rabbits eaten by foxes in month " + time);
    }
}
