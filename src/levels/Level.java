package levels;

import auxiliary.HelpingInCollision;
import entities.ExactlyEnemy;
import main.Game;
import prizeadditions.GameBox;
import prizeadditions.GameTrap;
import prizeadditions.Potion;
import prizeadditions.Weapon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static auxiliary.HelpingInCollision.*;

public class Level {
    private int[][] levelCreat;
    private BufferedImage image;
    private ArrayList<ExactlyEnemy> exactlyEnemies;
    private ArrayList<Potion> potions;
    private ArrayList<GameBox> boxs;
    private ArrayList<GameTrap> traps;
    private ArrayList<Weapon> weapons;
    private int levelTilesOffset;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerAppearance;
    public Level(BufferedImage image) {
        this.image = image;
        createLevel();
        createEnemies();
        createPotions();
        createBoxs();
        createTraps();
        createWeapons();
        levelsOffsets();
        placePlayerAppearance();
    }
    private void createWeapons() {
        weapons = HelpingInCollision.GetWeapons(image);
    }
    private void createTraps() {
        traps = HelpingInCollision.GetTraps(image);
    }
    private void createBoxs() {
        boxs = HelpingInCollision.GetBoxs(image);
    }
    private void createPotions() {
        potions = HelpingInCollision.GetPotions(image);
    }
    private void placePlayerAppearance() {
        playerAppearance = GetPlacePlayerAppearance(image);
    }
    private void levelsOffsets() {
        levelTilesOffset = image.getWidth();
        maxTilesOffset = levelTilesOffset - Game.TILES_BY_WIDTH;
        maxLevelOffsetX = Game.SIZE_TILE * maxTilesOffset;
    }
    private void createEnemies() {
        exactlyEnemies = GetEnemies(image);
    }
    private void createLevel() {
        levelCreat = GetCreatingLevel(image);
    }
    public int getIndexSprite(int x, int y) {
        return levelCreat[y][x];
    }
    public int[][] getLevelCreat() {
        return levelCreat;
    }
    public int getLevelOffset() {
        return maxLevelOffsetX;
    }
    public ArrayList<ExactlyEnemy> getEnemes() {
        return exactlyEnemies;
    }
    public Point getPlayerAppearance() {
        return playerAppearance;
    }
    public ArrayList<Potion> getPotions() {
        return potions;
    }
    public ArrayList<GameBox> getBoxs() {
        return boxs;
    }
    public ArrayList<GameTrap> getTraps() {
        return traps;
    }
    public ArrayList<Weapon> getWeapons(){
        return weapons;
    }
}
