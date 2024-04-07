package levels;

import auxiliary.GetDownloadSave;
import gamestates.GameState;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HandlerLevel {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;
    public HandlerLevel(Game game){
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }
    public void draw(Graphics g, int levelOffsetX){
        for (int j = 0; j < Game.TILES_BY_HEIGHT; j++) {
            for (int i = 0; i < levels.get(levelIndex).getLevelCreat()[0].length; i++) {
                int index = levels.get(levelIndex).getIndexSprite(i, j);
                g.drawImage(levelSprite[index], Game.SIZE_TILE * i - levelOffsetX, Game.SIZE_TILE * j, Game.SIZE_TILE, Game.SIZE_TILE, null);
            }
        }
    }
    public void update(){

    }
    private void importOutsideSprites() {
        BufferedImage image = GetDownloadSave.GetPresenceSprite(GetDownloadSave.PRESENCE_LEVEL);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = image.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }
    public Level getPresentLevel() {
        return levels.get(levelIndex);
    }
    public void loadingNextLevel() {
        levelIndex++;
        if (levelIndex >= levels.size()) {
            levelIndex = 0;
            //System.out.println("Уровней больше нет");
            GameState.state = GameState.MENU;
        }
        Level newLevel = levels.get(levelIndex);
        game.getPlay().getHandlerEnemy().loadingEnemies(newLevel);
        game.getPlay().getPlayer().loadingLevelCreat(newLevel.getLevelCreat());
        game.getPlay().setMaxLevelOffset(newLevel.getLevelOffset());
        game.getPlay().getHandlerPrize().loadingPrize(newLevel);
    }
    private void buildAllLevels() {
        BufferedImage[] allLevels = GetDownloadSave.CompletingAllLevels();
        for (BufferedImage image : allLevels) {
            levels.add(new Level(image));
        }
    }
    public int getAmountOfLevels() {
        return levels.size();
    }
}
