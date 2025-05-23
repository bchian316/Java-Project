import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Tile implements Drawable, hasHealth {
    //3 types of wall: ground, border, and wall
    public static final int COLLISION_CUSHION = 8; //the wall has an 8 pixel cushion

    public static final int NUM_GROUND_IMAGES = 6;
    public static final int NUM_BORDER_IMAGES = 4;
    public static final int NUM_WALL_IMAGES = 16;
    public static final int IMAGE_SIZE = 50;

    public static final int WALL_MAX_HEALTH = 80;
    public static final int WALL_HEALTH_INTERVAL = WALL_MAX_HEALTH / NUM_WALL_IMAGES;

    public static final Image[] GROUND_IMAGES = new Image[NUM_GROUND_IMAGES];
    public static final Image[] BORDER_IMAGES = new Image[NUM_BORDER_IMAGES];
    public static final Image[] WALL_IMAGES = new Image[NUM_WALL_IMAGES];

    private final int x, y; //should be the coords
    private int health;
    private final boolean breakable;
    private Image image = null; //should only be border or ground, not wall

    static {
        for (int i = 0; i < Tile.NUM_BORDER_IMAGES; i++) {
            Tile.BORDER_IMAGES[i] = new ImageIcon("assets/terrain/border/" + Integer.toString(i + 1) + ".png").getImage()
                    .getScaledInstance(Tile.IMAGE_SIZE, Tile.IMAGE_SIZE, Image.SCALE_DEFAULT);
        }
        for (int i = 0; i < Tile.NUM_GROUND_IMAGES; i++) {
            Tile.GROUND_IMAGES[i] = new ImageIcon("assets/terrain/ground/" + Integer.toString(i + 1) + ".png")
                    .getImage()
                    .getScaledInstance(Tile.IMAGE_SIZE, Tile.IMAGE_SIZE, Image.SCALE_DEFAULT);
        }
        for (int i = 0; i < Tile.NUM_WALL_IMAGES; i++) {
            Tile.WALL_IMAGES[i] = new ImageIcon("assets/terrain/wall/" + Integer.toString(i + 1) + ".png").getImage()
                    .getScaledInstance(Tile.IMAGE_SIZE, Tile.IMAGE_SIZE, Image.SCALE_DEFAULT);
        }
    }

    public Tile(int x, int y, int health, boolean breakable) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.breakable = breakable;
        if (!this.breakable) {
            this.health = 1;
        }
        this.setImage();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public double getCenterX() {
        return this.x + Tile.IMAGE_SIZE/2;
    }

    @Override
    public double getCenterY() {
        return this.y + Tile.IMAGE_SIZE/2;
    }
    
    @Override
    public void getDamaged(int damage) {
        if (this.breakable && !this.isDead()) {
            this.health -= damage;
        }
    }

    @Override
    public boolean isHit(Projectile p) {
        if (this.isDead()) {//because unbreakables have a health of 1, they will not be dead and will change image
            return false;
        }
        
        return Game.circleRectCollided(p.getCenterX(), p.getCenterY(), p.getSize()/2.0, this.getX(), this.getY(), Tile.IMAGE_SIZE, Tile.IMAGE_SIZE);
    }
    
    @Override
    public boolean isDead() {//also for if can be walked on
        return this.health <= 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.image, this.x, this.y, null);
        if (!this.isDead() && this.breakable) {//is a wall
            g.drawImage(Tile.WALL_IMAGES[Tile.NUM_WALL_IMAGES
                    - ((this.health + Tile.WALL_HEALTH_INTERVAL - 1) / Tile.WALL_HEALTH_INTERVAL)], this.x, this.y, null);
        }
    }

    @Override
    public void drawHealthBar(Graphics g) {}
    
    public final void setImage() {
        if (!this.breakable) {
            this.image = Tile.BORDER_IMAGES[(int) (Math.random() * Tile.NUM_BORDER_IMAGES)];
            return;
        }
        this.image = Tile.GROUND_IMAGES[(int) (Math.random() * Tile.NUM_GROUND_IMAGES)];
    }

    public void checkProjectiles(ArrayList<Projectile> projectiles) {
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            if (this.isHit(p)) {
                this.getDamaged(p.getDamage());
                this.setImage();
                //remove projectiles if they hit a wall
                projectiles.remove(i);
            }
        }
    }
}