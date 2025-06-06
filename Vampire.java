
import java.awt.Color;
import java.util.ArrayList;

public class Vampire extends Enemy {

    public Vampire(int x, int y) {
        super("Vampire", x, y, 35, 40, 3, 3500, 600, new AttackStats(25, 25, 25, 600, 1, 10, new Color(255, 18, 18)));
    }


    @Override
    public ArrayList<Projectile> attack(double targetX, double targetY) {
        ArrayList<Projectile> newProjs = new ArrayList<>();
        newProjs.add(new Projectile(this.getCenterX(), this.getCenterY(), Game.getAngle(this.getCenterX(), this.getCenterY(), targetX, targetY),
                this.getAttackStats()));
        return newProjs;
    }
    
}