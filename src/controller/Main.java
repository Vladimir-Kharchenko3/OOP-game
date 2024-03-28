
package controller;


import behavior.CoordXY;
import behavior.HeroesNames;
import person.*;
import view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static ArrayList<PersonBase> greenPersons = new ArrayList<>();
    public static ArrayList<PersonBase> bluePersons = new ArrayList<>();
    public static ArrayList<PersonBase> allPersons = new ArrayList<>();

    public static void main(String[] args) {
        createTeam(greenPersons, 10, 0);
        createTeam(bluePersons, 10, 3);
        allPersons.addAll(bluePersons);
        allPersons.addAll(greenPersons);
//        all.sort(new PrioritySort());
        allPersons.sort((o1, o2) -> Integer.compare(o2.priority, o1.priority));

//        for (int i = 0; i < 30; i++) {
        Scanner in = new Scanner(System.in);
        while (true)
        {
            View.view();
            for (PersonBase p : allPersons) {
//                System.out.print(p + " ходит. ");
                if (greenPersons.contains(p)) {
                    p.step(bluePersons, greenPersons);

                } else {
                    p.step(greenPersons, bluePersons);
                }
//                System.out.println();
            }
            in.nextLine();
            if (!isLiving(greenPersons))
            {
                System.out.println("Blue team wins!");
                break;
            }
            if (!isLiving(bluePersons))
            {
                System.out.println("Green wins!");
                break;
            }

        }

    }

    private static boolean isLiving(ArrayList<PersonBase> team)
    {
        for (PersonBase personBase : team) {
            if (personBase.getHealth() > 0)
                return true;
        }
        return false;
    }

    public static void createTeam(ArrayList<PersonBase> team, int num, int start)
    {
        Random rnd = new Random();
        int cy = 0;
        while (num-- > 0)
        {
            int n = start + rnd.nextInt(4);
            switch (n)
            {
                case 0:
                    team.add(new Crossbowman(HeroesNames.getRandomName(), new CoordXY(9, cy)));
                    break;
                case 1:
                    team.add(new Spearman(HeroesNames.getRandomName(), new CoordXY(9, cy)));
                    break;
                case 2:
                    team.add(new Wizard(HeroesNames.getRandomName(), new CoordXY(9, cy)));
                    break;
                case 3:
                    team.add(new Peasant(HeroesNames.getRandomName(), new CoordXY((3-start)*3, cy)));
                    break;
                case 4:
                    team.add(new Sniper(HeroesNames.getRandomName(), new CoordXY(0, cy)));
                    break;
                case 5:
                    team.add(new Monk(HeroesNames.getRandomName(), new CoordXY(0, cy)));
                    break;
                case 6:
                    team.add(new Robber(HeroesNames.getRandomName(), new CoordXY(0, cy)));
                    break;
                default:
                    System.out.println("ERROR! Пересмотри алгоритм, ламер!");
            }
            cy++;
        }
    }

}