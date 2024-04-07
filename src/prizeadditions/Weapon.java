package prizeadditions;

import main.Game;
public class Weapon extends GamePrize{
    private int tileY;
    public Weapon(int x, int y, int obj) {
        super(x, y, obj);
        tileY = y / Game.SIZE_TILE;
        initializationHitbox(40, 26);
        hitbox.x -= (int) (4 * Game.SCALE);
        hitbox.y += (int) (6 * Game.SCALE);
    }
    public void update() {
        if (prizeAnimation)
            updateAnimationTick();
    }
    public int getTileY() {
        return tileY;
    }
}
