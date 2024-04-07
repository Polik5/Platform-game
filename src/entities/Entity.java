package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected int tickAni, indexAni;
    protected int statics;
    protected float flightSpeed;
    protected boolean flight = false;
    protected float walkingSpeed;
    protected float X, Y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected Rectangle2D.Float attackBox;
    protected int maxHealth;
    protected int currentHealth;
    public Entity(float X, float Y, int width, int height){
        this.X = X;
        this.Y = Y;
        this.width = width;
        this.height = height;
    }
    protected void initializationHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(X, Y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
    protected void drawHitbox(Graphics g, int levelOffsetX) {
        g.setColor(Color.ORANGE);
        g.drawRect((int) hitbox.x - levelOffsetX, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);

    }
    protected void drawAttackBox(Graphics g, int levelOffsetX) {
        g.setColor(Color.orange);
        g.drawRect((int) attackBox.x - levelOffsetX, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);

    }

    public int getEnemyIsWaiting() {
        return statics;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
}
