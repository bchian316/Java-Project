
import java.util.ArrayList;

public abstract class Mage {
    //PlayerTypes are prebuilt templates for playable classes eg knight, warrior, etc
    //template for building a playable character
    //NO MAGES FATTER THAN 82.5 based on PLAYERSTARTY
    private final String name;
    private final int size;
    private final int maxHealth; //maximum hp
    private final int regen;
    private final int speed; //movement speed
    private final int reload; //delay between attacking ms
    private final AttackStats attackStats;

    public Mage(String name, int size, int maxHealth, int regen, int speed, int reload, AttackStats attackStats) {
        this.name = name;
        this.size = size;
        this.maxHealth = maxHealth;
        this.regen = regen;
        this.speed = speed;
        this.reload = reload;
        this.attackStats = attackStats;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public int getHealth() {
        return this.maxHealth;
    }

    public int getRegen() {
        return this.regen;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getReload() {
        return this.reload;
    }

    public AttackStats getAttackStats() {
        return this.attackStats;
    }

    @Override
    public String toString() {
        return this.name;
    }

    //special method that doesn't take in attkstats cuz it uses the one attributed to the object already
    public abstract ArrayList<Projectile> createProjectiles(double x, double y, double targetX, double targetY); //returns projectiles to add to game list
}