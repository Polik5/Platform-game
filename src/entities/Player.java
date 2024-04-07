package entities;

import auxiliary.GetDownloadSave;
import gamestates.Play;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import gamestates.Play;
import static auxiliary.Consts.ConstsPlayer.*;
import static auxiliary.Consts.Direction.*;
import static auxiliary.Consts.Direction.DOWN;
import static auxiliary.Consts.*;
import static auxiliary.HelpingInCollision.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private boolean moving = false, attacking = false;
    private boolean left, right, jump;
    private int[][] levelCreat;
    private float xOffsetHitbox = 13 * Game.SCALE;
    private float yOffsetHitbox = 20 * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallingInCollisionSpeed = 0.5f * Game.SCALE;
    private BufferedImage statusPanelImage;
    private int statusPanelWidth = (int) (192 * Game.SCALE);
    private int statusPanelHeight = (int) (58 * Game.SCALE);
    private int statusPanelX = (int) (10 * Game.SCALE);
    private int statusPanelY = (int) (10 * Game.SCALE);
    private int healthPanelWidth = (int) (125 * Game.SCALE);
    private int healthPanelHeight = (int) (10 * Game.SCALE);
    private int healthPanelXStart = (int) (56 * Game.SCALE);
    private int healthPanelYStart = (int) (7 * Game.SCALE);
    private int healthWidth = healthPanelWidth;
    private int powerPanelWidth = (int) (107 * Game.SCALE);
    private int powerPanelHeight = (int) (7 * Game.SCALE);
    private int powerPanelXStart = (int) (61 * Game.SCALE);
    private int powerPanelYStart = (int) (37 * Game.SCALE);
    private int powerWidth = powerPanelWidth;
    private int powerMaxValue = 200;
    private int powerValue = powerMaxValue;
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;
    private Play play;
    private int tileY = 0;
    private boolean powerAttackActive;
    private int powerAttackTick;
    private int powerSpeed = 15;
    private int powerTick;
    public Player(float X, float Y, int width, int height, Play play){
        super(X,Y, width, height);
        this.play = play;
        this.statics = STATICS;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkingSpeed = 1.0f * Game.SCALE;
        loadingAnimation();
        initializationHitbox(20 , 27);
        initializationAttackBox();
    }
    public void update(){
        updateHealthPanel();
        updatePowerPanel();
        if (currentHealth <= 0) {
            if (statics != DEAD) {
                statics = DEAD;
                tickAni = 0;
                indexAni = 0;
                play.setPlayerDying(true);
            } else if (indexAni == GetActions(DEAD) - 1 && tickAni >= SPEED_ANI - 1) {
                play.setGameOver(true);
            } else
                tickAnimation();
            return;
        }
        updateAttackBox();
        updatePosition();
        if (moving) {
            checkTouched();
            checkTouchedTrap();
            tileY = (int) (hitbox.y / Game.SIZE_TILE);
            if (powerAttackActive) {
                powerAttackTick++;
                if (powerAttackTick >= 35) {
                    powerAttackTick = 0;
                    powerAttackActive = false;
                }
            }
        }
        if (attacking || powerAttackActive)
            checkAttack();
        tickAnimation();
        setAnimation();
    }
    private void checkTouchedTrap() {
        play.checkTouchedTrap(this);
    }

    private void checkTouched() {
        play.checkTouched(hitbox);
    }
    private void updateHealthPanel() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthPanelWidth);
    }
    private void updatePowerPanel() {
        powerWidth = (int) ((powerValue / (float) powerMaxValue) * powerPanelWidth);
        powerTick++;
        if (powerTick >= powerSpeed) {
            powerTick = 0;
            changePower(1);
        }
    }
    private void updateAttackBox() {
        if (right && left) {
            if (flipW == 1) {
                attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
            } else {
                attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
            }
        } else if (right || (powerAttackActive && flipW == 1)) {
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 5);
        }
        else if (left || (powerAttackActive && flipW == -1)) {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 5);
        }
        attackBox.y = hitbox.y + (Game.SCALE * 5);
    }
    public void rendering(Graphics g, int levelOffsetX){
        g.drawImage(animations[statics][indexAni], (int) (hitbox.x - xOffsetHitbox) - levelOffsetX + flipX, (int) (hitbox.y - yOffsetHitbox), width * flipW, height, null);
        //drawHitbox(g, levelOffsetX);
        drawUI(g);
        //drawAttackBox(g, levelOffsetX);
    }
    private void drawUI(Graphics g) {
        g.drawImage(statusPanelImage, statusPanelX, statusPanelY, statusPanelWidth, statusPanelHeight, null);
        g.setColor(Color.orange);
        g.fillRect(healthPanelXStart + statusPanelX, healthPanelYStart + statusPanelY, healthWidth, healthPanelHeight);
        g.setColor(Color.yellow);
        g.fillRect(powerPanelXStart + statusPanelX, powerPanelYStart + statusPanelY, powerWidth, powerPanelHeight);
    }
    private void tickAnimation() {
        tickAni++;
        if(tickAni >= SPEED_ANI){
            tickAni = 0;
            indexAni++;
            if(indexAni >= GetActions(statics)){
                indexAni = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }
    private void setAnimation() {
        int animationStart = statics;
        if(moving){
            statics = RUN;
        } else {
            statics = STATICS;
        }
        if (flight) {
            if (flightSpeed < 0) {
                statics = JUMP;
            } else {
                statics = FALL;
            }
        }
        if (powerAttackActive) {
            statics = ATTACK_ONE;
            indexAni = 1;
            tickAni = 0;
            return;
        }
        if(attacking){
            statics = ATTACK_ONE;
            if (animationStart != ATTACK_ONE) {
                indexAni = 1;
                tickAni = 0;
                return;
            }
        }
        if(animationStart != statics){
            resettingAnimationTick();
        }
    }
    private void updatePosition() {
        moving = false;
        if (jump) {
            jump();
        }
        if (!flight) {
            if (!powerAttackActive){
                if ((!left && !right) || (right && left)) {
                    return;
                }
            }
        }
        float speedX = 0;
        if(left && !right){
            speedX -= walkingSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right && !left){
            speedX += walkingSpeed;
            flipX = 0;
            flipW = 1;
        }
        if (powerAttackActive) {
            if ((!left && !right) || (left && right))  {
                if (flipW == -1)
                    speedX = -walkingSpeed;
                else
                    speedX = walkingSpeed;
            }
            speedX *= 3;
        }
        if (!flight) {
            if (!ObjectOnFloor(hitbox, levelCreat)) {
                flight = true;
            }
        }
        if (flight && !powerAttackActive){
            if (CheckingPosition(hitbox.x, hitbox.y + flightSpeed, hitbox.width, hitbox.height, levelCreat)) {
                hitbox.y += flightSpeed;
                flightSpeed += GRAVITY;
                updateXPos(speedX);
            } else {
                hitbox.y = PauseOverRoofOrFloor(hitbox, flightSpeed);
                if (flightSpeed > 0) {
                    droppedCrashed();
                }
                else {
                    flightSpeed = fallingInCollisionSpeed;
                }
                updateXPos(speedX);
            }
        } else {
            updateXPos(speedX);
        }
        moving = true;
    }
    public void changeHealth(int value) {
        currentHealth += value;
        if (currentHealth <= 0) {
            currentHealth = 0;
        }
        else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }
    public void changePower(int value) {
        powerValue += value;
        if (powerValue >= powerMaxValue) {
            powerValue = powerMaxValue;
        } else if (powerValue <= 0) {
            powerValue = 0;
        }
    }
    private void loadingAnimation() {
        BufferedImage image = GetDownloadSave.GetPresenceSprite(GetDownloadSave.PRESENCE_PLAYER);
        animations = new BufferedImage[7][8];
        for(int j = 0; j < animations.length; j++){
            for(int i = 0; i < animations[j].length; i++){
                animations[j][i] = image.getSubimage(i * 48, j * 48, 48, 48);
            }
        }
        statusPanelImage = GetDownloadSave.GetPresenceSprite(GetDownloadSave.HEALTH_PANEL);
    }
    public void loadingLevelCreat(int[][] levelCreat) {
        this.levelCreat = levelCreat;
        if (!ObjectOnFloor(hitbox, levelCreat)) {
            flight = true;
        }
    }
    public void resettingLogicalValues(){
        left = false;
        right = false;
    }
    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }
    public boolean isLeft() {
        return left;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public boolean isRight() {
        return right;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    private void resettingAnimationTick() {
        tickAni = 0;
        indexAni = 0;
    }
    private void updateXPos(float speedX) {
        if (CheckingPosition(hitbox.x + speedX, hitbox.y, hitbox.width, hitbox.height, levelCreat)) {
            hitbox.x += speedX;
        } else {
            hitbox.x = RelationshipsHitboxXpos(hitbox, speedX);
            if (powerAttackActive) {
                powerAttackActive = false;
                powerAttackTick = 0;
            }
        }
    }
    private void droppedCrashed() {
        flight = false;
        flightSpeed = 0;
    }
    private void jump() {
        if (flight) {
            return;
        }
        flight = true;
        flightSpeed = jumpSpeed;
    }
    public void setJump(boolean jump) {
        this.jump = jump;
    }
    private void initializationAttackBox() {
        attackBox = new Rectangle2D.Float(X, Y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
        resetAttackBox();
    }
    private void checkAttack() {
        if (attackChecked || indexAni != 1) {
            return;
        }
        attackChecked = true;
        if (powerAttackActive)
            attackChecked = false;
        play.checkEnemyHit(attackBox);
        play.checkHit(attackBox);
    }
    public void resetAll() {
        resettingLogicalValues();
        flight = false;
        attacking = false;
        moving = false;
        flightSpeed = 0f;
        statics = STATICS;
        currentHealth = maxHealth;
        hitbox.x = X;
        hitbox.y = Y;
        resetAttackBox();
        if (!ObjectOnFloor(hitbox, levelCreat))
            flight = true;
    }
    private void resetAttackBox() {
        if (flipW == 1) {
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
        } else {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
        }
    }
    public void setPlacePlayerAppearance(Point appearance) {
        this.X = appearance.x;
        this.Y = appearance.y;
        hitbox.x = X;
        hitbox.y = Y;
    }
    public void kill() {
        currentHealth = 0;
    }
    public int getTileY() {
        return tileY;
    }
    public void powerAttack() {
        if (powerAttackActive)
            return;
        if (powerValue >= 60) {
            powerAttackActive = true;
            changePower(-60);
        }
    }
}
