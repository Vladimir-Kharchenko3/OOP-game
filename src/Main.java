import behavior.CoordXY;
import behavior.HeroesNames;
import person.*;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    static ArrayList<PersonBase> red = new ArrayList<>();
    static ArrayList<PersonBase> blue = new ArrayList<>();
    static ArrayList<PersonBase> all = new ArrayList<>();

    public static void main(String[] args) {
        createTeam(red, 10, 0);
        createTeam(blue, 10, 3);
        all.addAll(red);
        all.addAll(blue);
//        all.sort(new PrioritySort());
        all.sort((o1, o2) -> Integer.compare(o2.priority, o1.priority));

        for (int i = 0; i <=10; i ++ ){


            for (PersonBase p : all) {
                System.out.print(p + " ходит. ");
                if (red.contains(p)) {
//                if (p.getClass().getSimpleName().equals("Wizard"))
//                {
//                    ...
//                }
                    p.step(blue, red);

                } else {
                    p.step(red, blue);
                }
                System.out.println();
            }
        }

    }


    public static void createTeam(ArrayList<PersonBase> team, int num, int start) {
        Random rnd = new Random();
        while (--num >= 0) {
            int n = start + rnd.nextInt(4);
            switch (n) {
                case 0:
                    team.add(new Crossbowman(HeroesNames.getRandomName(), new CoordXY(0, num)));
                    break;
                case 1:
                    team.add(new Spearman(HeroesNames.getRandomName(), new CoordXY(0, num)));
                    break;
                case 2:
                    team.add(new Wizard(HeroesNames.getRandomName(), new CoordXY(0, num)));
                    break;
                case 3:
                    team.add(new Peasant(HeroesNames.getRandomName(), new CoordXY(start * 3, num)));
                    break;
                case 4:
                    team.add(new Sniper(HeroesNames.getRandomName(), new CoordXY(9, num)));
                    break;
                case 5:
                    team.add(new Monk(HeroesNames.getRandomName(), new CoordXY(9, num)));
                    break;
                case 6:
                    team.add(new Robber(HeroesNames.getRandomName(), new CoordXY(9, num)));
                    break;
                default:
                    System.out.println("ERROR! Пересмотри алгоритм, ламер!");
            }
        }
    }

}