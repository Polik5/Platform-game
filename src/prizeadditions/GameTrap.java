package prizeadditions;

import main.Game;
public class GameTrap extends GamePrize {
    public GameTrap(int x, int y, int obj) {
        super(x, y, obj);
        initializationHitbox(32, 16);
        offsetX = 0;
        offsetY = (int)(Game.SCALE * 16);
        hitbox.y += offsetY;
    }
}
