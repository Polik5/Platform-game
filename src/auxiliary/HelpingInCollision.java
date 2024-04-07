package auxiliary;

import entities.ExactlyEnemy;
import main.Game;
import prizeadditions.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static auxiliary.Consts.EnemyConstants.ENEMY;
import static auxiliary.Consts.PrizeConstants.*;

public class HelpingInCollision {
    public static boolean CheckingPosition(float x, float y, float width, float height, int[][] levelCreat) {
        if (!IsInsideTile(x, y, levelCreat)) {
            if (!IsInsideTile(x + width, y + height, levelCreat)) {
                if (!IsInsideTile(x + width, y, levelCreat)) {
                    if (!IsInsideTile(x, y + height, levelCreat)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static boolean IsInsideTile(float x, float y, int[][] levelCreat) {
        int maxWidth = levelCreat[0].length * Game.SIZE_TILE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.HEIGHT_GAME) {
            return true;
        }
        float xIndex = x / Game.SIZE_TILE;
        float yIndex = y / Game.SIZE_TILE;
        return IsTileSolid((int) xIndex, (int) yIndex, levelCreat);
    }
    public static boolean IsTileSolid(int xTile, int yTile, int[][] levelCreat) {
        int value = levelCreat[yTile][xTile];
        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }
    public static float RelationshipsHitboxXpos(Rectangle2D.Float hitbox, float speedX) {
        int currentTile = (int) (hitbox.x / Game.SIZE_TILE);
        if (speedX > 0) {
            int tileXPos = currentTile * Game.SIZE_TILE;
            int xOffset = (int) (Game.SIZE_TILE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else {
            return currentTile * Game.SIZE_TILE;
        }
    }
    public static float PauseOverRoofOrFloor(Rectangle2D.Float hitbox, float flightSpeed) {
        int currentTile = (int) (hitbox.y / Game.SIZE_TILE);
        if (flightSpeed > 0) {
            int tileYPos = currentTile * Game.SIZE_TILE;
            int yOffset = (int) (Game.SIZE_TILE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            return currentTile * Game.SIZE_TILE;
        }
    }
    public static boolean ObjectOnFloor(Rectangle2D.Float hitbox, int[][] levelCreat) {
        if (!IsInsideTile(hitbox.x, hitbox.y + hitbox.height + 1, levelCreat)) {
            if (!IsInsideTile(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelCreat)) {
                return false;
            }
        }
        return true;
    }
    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelCreat) {
        if (xSpeed > 0)
            return IsInsideTile(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, levelCreat);
        else
            return IsInsideTile(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelCreat);
    }
    public static boolean IsItPossibleToWalkOnAllTiles(int xStart, int xEnd, int y, int[][] levelCreat) {
        if (IsAllTilesClear(xStart, xEnd, y, levelCreat)){
            for (int i = 0; i < xEnd - xStart; i++) {
                if (!IsTileSolid(xStart + i, y + 1, levelCreat))
                    return false;
            }
        }
        return true;
    }
    public static boolean IsSightClear(int[][] levelCreat, Rectangle2D.Float enemyBox, Rectangle2D.Float playerBox, int yTile) {
        int firstXTile = (int) (enemyBox.x / Game.SIZE_TILE);
        int secondXTile;
        if (IsInsideTile(playerBox.x, playerBox.y + playerBox.height + 1, levelCreat))
            secondXTile = (int) (playerBox.x / Game.SIZE_TILE);
        else
            secondXTile = (int) ((playerBox.x + playerBox.width) / Game.SIZE_TILE);

        if (firstXTile > secondXTile)
            return IsItPossibleToWalkOnAllTiles(secondXTile, firstXTile, yTile, levelCreat);
        else
            return IsItPossibleToWalkOnAllTiles(firstXTile, secondXTile, yTile, levelCreat);
    }
    public static int[][] GetCreatingLevel(BufferedImage image){
        int[][] levelCreat = new int[image.getHeight()][image.getWidth()];
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                levelCreat[j][i] = value;
            }
        }
        return levelCreat;
    }
    public static ArrayList<ExactlyEnemy> GetEnemies(BufferedImage image) {
        ArrayList<ExactlyEnemy> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == ENEMY) {
                    list.add(new ExactlyEnemy(i * Game.SIZE_TILE, j * Game.SIZE_TILE));
                }
            }
        }
        return list;
    }
    public static Point GetPlacePlayerAppearance(BufferedImage image) {
        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * Game.SIZE_TILE, j * Game.SIZE_TILE);
            }
        return new Point(1 * Game.SIZE_TILE, 1 * Game.SIZE_TILE);
    }
    public static ArrayList<Potion> GetPotions(BufferedImage img) {
        ArrayList<Potion> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == HEALTH_POTION || value == POWER_POTION)
                    list.add(new Potion(i * Game.SIZE_TILE, j * Game.SIZE_TILE, value));
            }
        return list;
    }
    public static ArrayList<GameBox> GetBoxs(BufferedImage img) {
        ArrayList<GameBox> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == BOX1 || value == BOX2)
                    list.add(new GameBox(i * Game.SIZE_TILE, j * Game.SIZE_TILE, value));
            }
        return list;
    }
    public static ArrayList<GameTrap> GetTraps(BufferedImage img) {
        ArrayList<GameTrap> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == TRAP_1)
                    list.add(new GameTrap(i * Game.SIZE_TILE, j * Game.SIZE_TILE, TRAP_1));
            }
        return list;
    }
    public static ArrayList<Weapon> GetWeapons(BufferedImage img) {
        ArrayList<Weapon> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == WEAPON_LEFT || value == WEAPON_RIGHT)
                    list.add(new Weapon(i * Game.SIZE_TILE, j * Game.SIZE_TILE, value));
            }
        return list;
    }
    public static boolean CanWeaponSeePlayer(int[][] creatLevel, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.SIZE_TILE);
        int secondXTile = (int) (secondHitbox.x / Game.SIZE_TILE);
        if (firstXTile > secondXTile)
            return IsAllTilesClear(secondXTile, firstXTile, yTile, creatLevel);
        else
            return IsAllTilesClear(firstXTile, secondXTile, yTile, creatLevel);
    }
    public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] creatLevel) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, creatLevel)) {
                return false;
            }
        }
        return true;
    }
    public static boolean CoreWeaponHittingLevel(CoreWeapon c, int[][] creatLevel) {
        return IsInsideTile(c.getHitbox().x + c.getHitbox().width / 2, c.getHitbox().y + c.getHitbox().height / 2, creatLevel);
    }
}
