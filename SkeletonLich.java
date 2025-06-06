
import java.awt.Color;
import java.util.ArrayList;

public class SkeletonLich extends SpawnerEnemy {
    public SkeletonLich(int x, int y) {
        super("SkeletonLich", x, y, 85, 135, 1, 1500, 550,
                new AttackStats(10, 35, 14, 325, 2, 35, new Color(0, 82, 94), false,
                        new AttackStats(10, 35, 21, 325, 2, 35, new Color(0, 82, 94), false,
                                new AttackStats(6, 20, 20, 175, 1, 3, new Color(196, 73, 16)))),
                9000);
    }

    @Override
    public ArrayList<Projectile> attack(double targetX, double targetY) {
        this.heal(8);
        ArrayList<Projectile> newProjs = new ArrayList<>();
        newProjs.add(new Projectile(this.getCenterX(), this.getCenterY(),
                    Game.getAngle(this.getCenterX(), this.getCenterY(), targetX, targetY) + Math.toRadians(35),
                    this.getAttackStats(),
                (x1, y1, targetX1, targetY1, splitStats) -> moreAttack(x1, y1, targetX1, targetY1, splitStats)));
        newProjs.add(new Projectile(this.getCenterX(), this.getCenterY(),
                    Game.getAngle(this.getCenterX(), this.getCenterY(), targetX, targetY),
                    this.getAttackStats(),
                (x1, y1, targetX1, targetY1, splitStats) -> moreAttack(x1, y1, targetX1, targetY1, splitStats)));
        newProjs.add(new Projectile(this.getCenterX(), this.getCenterY(),
                    Game.getAngle(this.getCenterX(), this.getCenterY(), targetX, targetY) - Math.toRadians(35),
                    this.getAttackStats(),
                    (x1, y1, targetX1, targetY1, splitStats) -> moreAttack(x1, y1, targetX1, targetY1, splitStats)));
        return newProjs;
    }

    public ArrayList<Projectile> moreAttack(double x, double y, double targetX, double targetY,
            AttackStats splitStats) {
        ArrayList<Projectile> newProjs = new ArrayList<>();
        newProjs.add(new Projectile(x, y,
                    Game.getAngle(x, y, targetX, targetY) + Math.PI,
                    splitStats,
                (x1, y1, targetX1, targetY1, splitStats1) -> evenMoreAttack(x1, y1, targetX1, targetY1, splitStats1)));
        return newProjs;
    }
    public ArrayList<Projectile> evenMoreAttack(double x, double y, double targetX, double targetY,
            AttackStats splitStats) {
        ArrayList<Projectile> newProjs = new ArrayList<>();
        // shoots twice from himself
        for (int i = 0; i < 3; i++) {
            newProjs.add(new Projectile(x, y,
                    Game.getAngle(x, y, targetX, targetY) - Math.toRadians(i*120),
                    splitStats));
        }
        return newProjs;
    }

    @Override
    public ArrayList<Enemy> spawn(Map map) {
        this.resetSpawnTimer();
        ArrayList<Enemy> newEnemies = new ArrayList<>();
        if (Math.random() >= 0.35) {
            newEnemies.add(new SkeletonShaman((int) this.getCenterX(), (int) this.getCenterY()));
        } else {
            for (int i = 0; i < 3; i++) {
                double randAngle = Math.random() * Math.PI * 2; // in radians
                double randMagnitude = Math.random() * 100;
                newEnemies.add(new Skeleton((int) (this.getCenterX() + Game.getVectorX(randAngle, randMagnitude)),
                        (int) (this.getCenterY() + Game.getVectorY(randAngle, randMagnitude))));
            }
        }
        return newEnemies;
    }
}