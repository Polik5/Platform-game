package prizeadditions;

import main.Game;
import static auxiliary.Consts.PrizeConstants.*;
public class GameBox extends GamePrize {
    public GameBox(int x, int y, int obj) {
        super(x, y, obj);
        createHitbox();
    }
    private void createHitbox() {
        if (obj== BOX2) {
            initializationHitbox(25, 18);
            offsetX = (int) (7 * Game.SCALE);
            offsetY = (int) (12 * Game.SCALE);
        } else {
            initializationHitbox(23, 25);
            offsetX = (int) (8 * Game.SCALE);
            offsetY = (int) (5 * Game.SCALE);
        }
        hitbox.y += offsetY + (int) (Game.SCALE * 2);
        hitbox.x += offsetX / 2;
    }
    public void update() {
        if (prizeAnimation) {
            updateAnimationTick();
        }
    }
}
