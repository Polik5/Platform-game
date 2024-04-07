package gamestates;

import auxiliary.GetDownloadSave;
import entities.HandlerEnemy;
import entities.Player;
import levels.HandlerLevel;
import main.Game;
import prizeadditions.HandlerPrize;
import userinterface.CompletedLevel;
import userinterface.GameOver;
import userinterface.StopIt;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
public class Play extends State implements Methods{
    private Player player;
    private HandlerLevel handlerLevel;
    private HandlerEnemy handlerEnemy;
    private GameOver gameOver_;
    private CompletedLevel completedLevel;
    private boolean pause = false;
    private StopIt stopIt;
    private int levelOffsetX;
    private int leftBorder = (int) (0.2 * Game.WIDTH_GAME);
    private int rightBorder = (int) (0.8 * Game.WIDTH_GAME);
    private int maxLevelOffsetX;
    private BufferedImage backgroundImage;
    private boolean gameOver;
    private boolean levelCompleted;
    private HandlerPrize handlerPrize;
    private boolean playerDying;
    public Play(Game game) {
        super(game);
        initializationClasses();
        backgroundImage = GetDownloadSave.GetPresenceSprite(GetDownloadSave.PLAY_BACK_IMAGE);
        levelOffset();
        loadingStartLevel();
    }
    private void initializationClasses() {
        handlerLevel = new HandlerLevel(game);
        handlerEnemy = new HandlerEnemy(this);
        handlerPrize = new HandlerPrize(this);
        player = new Player(200,200, (int) (48 * Game.SCALE), (int) (48 * Game.SCALE), this);
        player.loadingLevelCreat(handlerLevel.getPresentLevel().getLevelCreat());
        player.setPlacePlayerAppearance(handlerLevel.getPresentLevel().getPlayerAppearance());
        stopIt = new StopIt(this);
        gameOver_ = new GameOver(this);
        completedLevel = new CompletedLevel(this);
    }
    public void loadingNextLevel() {
        handlerLevel.loadingNextLevel();
        player.setPlacePlayerAppearance(handlerLevel.getPresentLevel().getPlayerAppearance());
        resetAll();
    }
    private void loadingStartLevel() {
        handlerEnemy.loadingEnemies(handlerLevel.getPresentLevel());
        handlerPrize.loadingPrize(handlerLevel.getPresentLevel());
    }
    private void levelOffset() {
        maxLevelOffsetX = handlerLevel.getPresentLevel().getLevelOffset();
    }

    public Player getPlayer(){
        return player;
    }
    public void focusGameWindowLost(){
        player.resettingLogicalValues();
    }

    @Override
    public void update() {
        if (pause) {
            stopIt.update();
        } else if (levelCompleted) {
            completedLevel.update();
        } else if (gameOver) {
            gameOver_.update();
        } else if (playerDying) {
            player.update();
        } else {
            handlerLevel.update();
            handlerPrize.update(handlerLevel.getPresentLevel().getLevelCreat(), player);
            player.update();
            handlerEnemy.update(handlerLevel.getPresentLevel().getLevelCreat(), player);
            checkCloseToBorder();
        }
    }
    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - levelOffsetX;
        if (diff > rightBorder)
            levelOffsetX += diff - rightBorder;
        else if (diff < leftBorder)
            levelOffsetX += diff - leftBorder;
        if (levelOffsetX > maxLevelOffsetX)
            levelOffsetX = maxLevelOffsetX;
        else if (levelOffsetX < 0)
            levelOffsetX = 0;
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, Game.WIDTH_GAME, Game.HEIGHT_GAME, null);
        handlerLevel.draw(g, levelOffsetX);
        player.rendering(g, levelOffsetX);
        handlerEnemy.draw(g, levelOffsetX);
        handlerPrize.draw(g, levelOffsetX);
        if (pause) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.WIDTH_GAME, Game.HEIGHT_GAME);
            stopIt.draw(g);
        } else if (gameOver) {
            gameOver_.draw(g);
        }
        else if (levelCompleted) {
            completedLevel.draw(g);
        }
    }
    public void resetAll() {
        gameOver = false;
        pause = false;
        levelCompleted = false;
        playerDying = false;
        player.resetAll();
        handlerEnemy.resetAllEnemies();
        handlerPrize.resetAllObjects();
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        handlerEnemy.checkEnemyHit(attackBox);
    }
    public void checkTouched(Rectangle2D.Float hitbox) {
        handlerPrize.checkTouch(hitbox);
    }
    public void checkHit(Rectangle2D.Float attackBox) {
        handlerPrize.checkHit(attackBox);
    }
    public void checkTouchedTrap(Player p) {
        handlerPrize.checkTouchTrap(p);
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver){
            if(e.getButton() == MouseEvent.BUTTON1){
                player.setAttacking(true);
            } else if(e.getButton() == MouseEvent.BUTTON3) {
                player.powerAttack();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver){
            if (pause)
                stopIt.mousePressed(e);
            else if (levelCompleted)
                completedLevel.mousePressed(e);
        }else
            gameOver_.mousePressed(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver){
            if (pause)
                stopIt.mouseMoved(e);
            else if (levelCompleted)
                completedLevel.mouseMoved(e);
        }else
            gameOver_.mouseMoved(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver){
            if (pause)
                stopIt.mouseReleased(e);
            else if (levelCompleted)
                completedLevel.mouseReleased(e);
        }else
            gameOver_.mouseReleased(e);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            gameOver_.keyPressed(e);
        }
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    pause = !pause;
                    break;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        }
    }
    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }
    public void unpauseGame() {
        pause = false;
    }
    public HandlerEnemy getHandlerEnemy() {
        return handlerEnemy;
    }
    public void setMaxLevelOffset(int levelOffset) {
        this.maxLevelOffsetX = levelOffset;
    }
    public void setLevelCompleted(boolean levelCompleted_) {
        this.levelCompleted = levelCompleted_;
    }
    public HandlerPrize getHandlerPrize() {
        return handlerPrize;
    }
    public HandlerLevel getHandlerLevel() {
        return handlerLevel;
    }
}
