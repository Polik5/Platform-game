package prizeadditions;

import main.Game;

public class Potion extends GamePrize{
    private float cursorOffset;
    private int maxCursorOffset, cursorDir = 1;
    public Potion(int x, int y, int obj) {
        super(x, y, obj);
        prizeAnimation = true;
        initializationHitbox(7,14);
        offsetX = (int) (3 * Game.SCALE);
        offsetY = (int) (2 * Game.SCALE);
        maxCursorOffset = (int) (10 * Game.SCALE);
    }
    public void update() {
        updateAnimationTick();
        cursorUpdate();
    }
    private void cursorUpdate() {
        cursorOffset += (0.075f * Game.SCALE * cursorDir);
        if (cursorOffset >= maxCursorOffset)
            cursorDir = -1;
        else if (cursorOffset < 0)
            cursorDir = 1;
        hitbox.y = y + cursorOffset;
    }
}
