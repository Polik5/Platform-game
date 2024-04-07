package prizeadditions;

import main.Game;
import java.awt.geom.Rectangle2D;
import static auxiliary.Consts.ForWeapons.*;
public class CoreWeapon {
    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active = true;
    public CoreWeapon(int x, int y, int dir) {
        int xOffset = (int) (-3 * Game.SCALE);
        int yOffset = (int) (5 * Game.SCALE);
        if (dir == 1)
            xOffset = (int) (29 * Game.SCALE);
        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, CORE_WEAPON_WIDTH, CORE_WEAPON_HEIGHT);
        this.dir = dir;
    }
    public void updatePosition() {
        hitbox.x += dir * SPEED;
    }
    public void setPosition(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean isActive() {
        return active;
    }
}
