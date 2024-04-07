package entities;

import auxiliary.GetDownloadSave;
import gamestates.Play;
import levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static auxiliary.Consts.EnemyConstants.*;
public class HandlerEnemy {
    private Play play;
    private BufferedImage[][] arrayEnemy;
    private ArrayList<ExactlyEnemy> enemies = new ArrayList<>();
    public HandlerEnemy(Play play) {
        this.play = play;
        loadingEnemyImages();
    }
    public void loadingEnemies(Level level) {
        enemies = level.getEnemes();
    }
    public void update(int[][] levelCreat, Player player) {
        boolean isActive = false;
        for (ExactlyEnemy en : enemies) {
            if (en.isActive()) {
                en.update(levelCreat, player);
                isActive = true;
            }
        }
        if(!isActive) {
            play.setLevelCompleted(true);
        }
    }
    public void draw(Graphics g, int levelOffsetX) {
        drawEm(g, levelOffsetX);
    }
    private void loadingEnemyImages() {
        arrayEnemy = new BufferedImage[5][13];
        BufferedImage temp = GetDownloadSave.GetPresenceSprite(GetDownloadSave.ENEMY_SPRITE);
        for (int j = 0; j < arrayEnemy.length; j++) {
            for (int i = 0; i < arrayEnemy[j].length; i++) {
                arrayEnemy[j][i] = temp.getSubimage(i * ENEMY_WIDTH_DEFAULT, j * ENEMY_HEIGHT_DEFAULT, ENEMY_WIDTH_DEFAULT, ENEMY_HEIGHT_DEFAULT);
            }
        }
    }
    private void drawEm(Graphics g, int levelOffsetX) {
        for (ExactlyEnemy en : enemies) {
            if (en.isActive()) {
                //g.drawImage(arrayEnemy[en.getEnemyIsWaiting()][en.getEnemyIndex()], (int) en.getHitbox().x - levelOffsetX - ENEMY_OUTPUT_OFFSET_X, (int) en.getHitbox().y - ENEMY_OUTPUT_OFFSET_Y, ENEMY_WIDTH, ENEMY_HEIGHT, null);
                g.drawImage(arrayEnemy[en.getEnemyIsWaiting()][en.getEnemyIndex()], (int) en.getHitbox().x - levelOffsetX - ENEMY_OUTPUT_OFFSET_X + en.flipX(), (int) en.getHitbox().y - ENEMY_OUTPUT_OFFSET_Y, ENEMY_WIDTH * en.flipW(),
                        ENEMY_HEIGHT, null);
                //en.drawHitbox(g, levelOffsetX);
                //en.drawAttackBox(g, levelOffsetX);
            }
        }
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (ExactlyEnemy en : enemies) {
            if (en.isActive()) {
                if (en.getCurrentHealth() > 0) {
                    if (attackBox.intersects(en.getHitbox())) {
                        en.hurt(10);
                        return;
                    }
                }
            }
        }
    }
    public void resetAllEnemies() {
        for (ExactlyEnemy en : enemies)
            en.resetEnemy();
    }
}
