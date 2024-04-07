package entities;

import main.Game;
import java.awt.geom.Rectangle2D;
import static auxiliary.Consts.Direction.*;
import static auxiliary.Consts.EnemyConstants.*;
import static auxiliary.Consts.*;
import static auxiliary.HelpingInCollision.*;
public abstract class Enemy extends Entity {
    protected boolean firstUpdate = true;
    protected int runDir = LEFT;
    protected int tileY;
    protected float floatingAttackDistance = Game.SIZE_TILE;
    protected boolean activeEnemy = true;
    protected boolean attackChecked;
    protected int enemyType;
    public Enemy(float X, float Y, int width, int height, int enemyType) {
        super(X, Y, width, height);
        this.enemyType = enemyType;
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
        walkingSpeed = Game.SCALE * 0.35f;
    }
    protected void firstUpdateCheck(int[][] levelCreat) {
        if (!ObjectOnFloor(hitbox, levelCreat))
            flight = true;
        firstUpdate = false;
    }
    protected void updateInFlight(int[][] levelCreat) {
        if (CheckingPosition(hitbox.x, hitbox.y + flightSpeed, hitbox.width, hitbox.height, levelCreat)) {
            hitbox.y += flightSpeed;
            flightSpeed += GRAVITY;
        } else {
            flight = false;
            hitbox.y = PauseOverRoofOrFloor(hitbox, flightSpeed);
            tileY = (int) (hitbox.y / Game.SIZE_TILE);
        }
    }
    protected void moving(int[][] levelCreat) {
        float xSpeed = 0;
        if (runDir == LEFT)
            xSpeed = -walkingSpeed;
        else
            xSpeed = walkingSpeed;
        if (CheckingPosition(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelCreat))
            if (IsFloor(hitbox, xSpeed, levelCreat)) {
                hitbox.x += xSpeed;
                return;
            }
        changeRunDir();
    }
    protected void newState(int enemyIsWaiting) {
        this.statics = enemyIsWaiting;
        tickAni = 0;
        indexAni = 0;
    }
    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x)
            runDir = RIGHT;
        else
            runDir = LEFT;
    }
    protected boolean seePlayer(int[][] levelCreat, Player player) {
        int playerTileY = (int) (player.getHitbox().y / Game.SIZE_TILE);
        if (playerTileY == tileY)
            if (playerInRange(player)) {
                if (IsSightClear(levelCreat, hitbox, player.hitbox, tileY)) {
                    return true;
                }
            }
        return false;
    }
    protected boolean playerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= floatingAttackDistance * 5;
    }
    protected boolean  playerIsCloseToAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= floatingAttackDistance;
    }
    protected void updateAnimationTick() {
        tickAni++;
        if (tickAni >= SPEED_ANI) {
            tickAni = 0;
            indexAni++;
            if (indexAni >= GetActionsEnewy(enemyType, statics)) {
                indexAni = 0;
                switch (statics) {
                    case ATTACK, HIT -> statics = STATE;
                    case DEAD -> activeEnemy = false;
                }
            }
        }
    }
    protected void checkHitOnPlayer(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox))
            player.changeHealth(-GetEnemyDealDamage(enemyType));
        attackChecked = true;
    }
    protected void changeRunDir() {
        if (runDir == LEFT) {
            runDir = RIGHT;
        } else {
            runDir = LEFT;
        }
    }
    public int getEnemyIndex() {
        return indexAni;
    }
    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            newState(DEAD);
        }
        else {
            newState(HIT);
        }
    }
    public boolean isActive() {
        return activeEnemy;
    }
    public void resetEnemy() {
        hitbox.x = X;
        hitbox.y = Y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(STATE);
        activeEnemy = true;
        flightSpeed = 0;
    }
}
