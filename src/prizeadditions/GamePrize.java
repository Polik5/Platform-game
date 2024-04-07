package prizeadditions;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import static auxiliary.Consts.PrizeConstants.*;
import static auxiliary.Consts.SPEED_ANI;

public class GamePrize {
    protected int x, y, obj;
    protected int tickAni, indexAni;
    protected Rectangle2D.Float hitbox;
    protected boolean prizeAnimation, active = true;
    protected int offsetX, offsetY;
    public GamePrize(int x, int y, int obj) {
        this.x = x;
        this.y = y;
        this.obj = obj;
    }
    protected void initializationHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }
    public void drawHitbox(Graphics g, int levelOffsetX) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - levelOffsetX, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }
    protected void updateAnimationTick() {
        tickAni++;
        if (tickAni >= SPEED_ANI) {
            tickAni = 0;
            indexAni++;
            if (indexAni >= GetSprite(obj)) {
                indexAni = 0;
                if (obj == BOX1 || obj == BOX2) {
                    prizeAnimation = false;
                    active = false;
                } else if (obj == WEAPON_LEFT || obj == WEAPON_RIGHT) {
                    prizeAnimation = false;
                }
            }
        }
    }
    public void reset() {
        indexAni = 0;
        tickAni = 0;
        active = true;
        if (obj == BOX1 || obj == BOX2 || obj == WEAPON_LEFT || obj == WEAPON_RIGHT) {
            prizeAnimation = false;
        }
        else {
            prizeAnimation = true;
        }
    }

    public int getObj() {
        return obj;
    }

    public void setObj(int obj) {
        this.obj = obj;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }

    public boolean isPrizeAnimation() {
        return prizeAnimation;
    }

    public void setPrizeAnimation(boolean prizeAnimation) {
        this.prizeAnimation = prizeAnimation;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getIndexAni() {
        return indexAni;
    }

    public void setIndexAni(int indexAni) {
        this.indexAni = indexAni;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public int getTickAni() {
        return tickAni;
    }
}
