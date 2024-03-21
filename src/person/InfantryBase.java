package person;

import behavior.CoordXY;

import java.util.ArrayList;

/**
 * Абстрактный класс Пехота, в данном случае база для Разбойников и Копейщиков,
 * но можно добавить Мечников, Варваров и тд.
 */
public abstract class InfantryBase extends PersonBase {

    protected int level;            // уровень, увеличивается по мере опыта

    /**
     * Конструктор базы Пехоты
     *
     * @param name     Имя
     * @param priority Приоритет хода
     * @param health   Текущее здоровье
     * @param power    Сила
     * @param agility  Ловкость (%). 3 ловкости = 1% к увороту, и 10 ловкости = 1% к критическому удару
     * @param defence  Защита (% к сопротивлению урону)
     * @param distance Дистанция воздействия на другой объект (10 у мага, 1 у крестьянина и тд)
     * @param pos      Положение в прогстранстве
     */
    protected InfantryBase(String name, int priority, int health, int power, int agility, int defence, int distance, CoordXY pos)
    {
        super(name, priority, health, power, agility, defence, distance, pos);
        level = 1;
    }
    public void attack(PersonBase target) {
        int damage = getRound(power, 10) + (power / 10) * level;
        boolean critical = (this.agility/3) >= rnd.nextInt(100);
        if (critical)
        {
            damage *= 2.0f;
        }
        int res = target.getDamage(damage);
        if (res > 0)
        {
            if (critical)
                System.out.print(" и наносит критический удар в " + res + " повреждений!");
            else
                System.out.print(" и наносит " + res + " повреждений.");
        } else {
            System.out.print(", но " + target.name + " увернулся!");
        }
        if (target.health <= 0)
        {
            System.out.print("\n" + target + " вышел из чата!");
        }
    }

    public void move(PersonBase target, ArrayList<PersonBase> friends){
        CoordXY delta = position.getDelta(target.position);
        CoordXY newPoz = new CoordXY(position.getX(),position.getY());

        int dx = delta.getX();
        if (dx != 0)
            dx = Math.abs(dx)/dx;
        int dy = delta.getY();
        if (dy != 0)
            dy = Math.abs(dy)/dy;
        if (dx != 0 && dy != 0)
            dy = 0;
        newPoz.add(dx,dy);

        for (PersonBase vin: friends){
            if(vin.position.check(newPoz))
                return;
        }

        position = newPoz;
        System.out.println(this);

    }



    @Override
    public void step(ArrayList<PersonBase> enemies, ArrayList<PersonBase> friends)
    {
        PersonBase target = this.findNearestPerson(enemies);
        if (health <= 0 || target == null)
            return;

        if (position.distanceTo(target.position)<2)
            attack(target);
        else
            move(target, friends);

    }


}