
import java.awt.Color;
import java.util.ArrayList;

public class Ghost extends Enemy {

    public Ghost(int x, int y) {
        super("Ghost", x, y, 35, 30, 5, 1000, 200, 500, new AttackStats(6, 25, 10, 75, 2, new Color(134, 134, 134)));
    }


    @Override
    public ArrayList<Projectile> attack(double targetX, double targetY) {
        ArrayList<Projectile> newProjs = new ArrayList<>();
        newProjs.add(new Projectile(this.getCenterX(), this.getCenterY(), Game.getAngle(this.getCenterX(), this.getCenterY(), targetX, targetY) - Math.toRadians(15),
                this.getAttackStats()));
        newProjs.add(new Projectile(this.getCenterX(), this.getCenterY(), Game.getAngle(this.getCenterX(), this.getCenterY(), targetX, targetY) + Math.toRadians(15),
                this.getAttackStats()));
        return newProjs;
    }
    
}