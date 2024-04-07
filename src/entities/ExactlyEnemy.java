package entities;

import static auxiliary.Consts.EnemyConstants.*;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import static auxiliary.Consts.Direction.*;
public class ExactlyEnemy extends Enemy {
    private int attackBoxOffsetX;
    public ExactlyEnemy(float X, float Y) {
        super(X, Y, ENEMY_WIDTH, ENEMY_HEIGHT, ENEMY);
        initializationHitbox(22 , 19 );
        initializationAttackBox();
    }
    public void update(int[][] levelCreat, Player player) {
        updateBehavior(levelCreat, player);
        updateAnimationTick();
        updateAttackBox();
    }
    private void initializationAttackBox() {
        attackBox = new Rectangle2D.Float(X, Y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 20);
    }
    private void updateAttackBox() {
//        attackBox.x = hitbox.x - attackBoxOffsetX;
//        attackBox.y = hitbox.y;
        if (runDir == RIGHT) {
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE );
        }
        else if (runDir == LEFT) {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE);
        }
        attackBox.y = hitbox.y + (Game.SCALE);
    }
    private void updateBehavior(int[][] levelCreat, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(levelCreat);
        }
        if (flight) {
            updateInFlight(levelCreat);
        } else {
            switch (statics) {
                case STATE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (seePlayer(levelCreat, player)) {
                        turnTowardsPlayer(player);
                        if (playerIsCloseToAttack(player))
                            newState(ATTACK);
                    }
                    moving(levelCreat);
                    break;
                case ATTACK:
                    if (indexAni == 0)
                        attackChecked = false;
                    if (indexAni == 6 && !attackChecked)
                        checkHitOnPlayer(attackBox, player);
                    break;
                case HIT:
                    break;

            }
        }
    }
    public int flipX() {
        if (runDir == LEFT) {
            return width;
        }
        else {
            return 0;
        }
    }
    public int flipW() {
        if (runDir == LEFT) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
