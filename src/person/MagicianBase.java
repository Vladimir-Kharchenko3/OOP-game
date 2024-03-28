package person;

import behavior.CoordXY;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Базовый класс для Волшебников, в данном случае для Кодуна и Монаха,
 * но можно добавить Некроманты или Шамана.
 */
public abstract class MagicianBase extends PersonBase {

    protected int mana;                     // мана для волшебства
    protected int maxMana;

    /**
     * Конструктор базы Волшебников
     *
     * @param name     Имя
     * @param priority Приоритет хода
     * @param health   Текущее здоровье
     * @param power    Сила
     * @param agility  Ловкость (%). 3 ловкости = 1% к увороту, и 10 ловкости = 1% к критическому удару
     * @param defence  Защита (% к сопротивлению урону)
     * @param distance Дистанция воздействия на другой объект (10 у мага, 1 у крестьянина и тд)
     * @param mana     Маны в наличии
     * @param pos      Положение в прогстранстве
     */
    protected MagicianBase(String name, int priority, int health, int power, int agility, int defence, int distance, int mana, CoordXY pos) {
        super(name, priority, health, power, agility, defence, distance, pos);
        this.mana = mana;
        this.maxMana = mana;
    }

    @Override
    public void step(ArrayList<PersonBase> enemies, ArrayList<PersonBase> friends) {
        PersonBase pt = null;
        int min = Integer.MAX_VALUE;
        if (health <= 0)
            return;
        if (mana <= 0) {
            mana += 5;
            return;
        }
        if (getDieds(friends) > 3 ){
            doRes(friends);

        }
        else doHealth(friends);

    }
    private void doRes(ArrayList<PersonBase> friends){
        if (mana < 50)
            return;
        PersonBase pt = friends.stream()
                .filter(n-> n.health == 0)
                .sorted((n1, n2) -> n2.priority - n1.priority)
                .collect(Collectors.toList())
                .getFirst();
        pt.healed(Integer.MAX_VALUE);
        mana -= 50;
    }


    private void doHealth(ArrayList<PersonBase> friends) {
        PersonBase pt = null;
        int min = Integer.MAX_VALUE;
        for (PersonBase friend : friends) {
            int hp = friend.getHealth();
            if (hp > 0 && hp < friend.maxHealth) {
                hp = hp * 100 / maxHealth;
                if (hp < min) {
                    min = hp;
                    pt = friend;
                }
            }
        }
        if (pt != null) {
            int ig71 = 10;
            mana -= 10;
            if (mana < 0) {
                ig71 += mana;
                mana = 0;
            }
            pt.healed(ig71 * 3);
        }
    }

    int getDieds(ArrayList<PersonBase> paris) {
        return (int) paris.stream().filter(n -> n.health == 0).count();
    }

}