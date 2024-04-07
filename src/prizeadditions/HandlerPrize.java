package prizeadditions;

import auxiliary.GetDownloadSave;
import entities.Player;
import gamestates.Play;
import levels.Level;
import main.Game;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static auxiliary.Consts.ForWeapons.*;
import static auxiliary.Consts.PrizeConstants.*;
import static auxiliary.HelpingInCollision.CanWeaponSeePlayer;
import static auxiliary.HelpingInCollision.CoreWeaponHittingLevel;

public class HandlerPrize {
    private Play play;
    private BufferedImage[][] potionImages, boxImages;
    private ArrayList<Potion> potions;
    private ArrayList<GameBox> boxs;
    private ArrayList<GameTrap> traps;
    private ArrayList<Weapon> weapons;
    private ArrayList<CoreWeapon> coreWeapons = new ArrayList<>();
    private BufferedImage trapImage, coreWeaponsImage;
    private BufferedImage[] weaponImages;
    public HandlerPrize(Play play) {
        this.play = play;
        loadingImages();
    }
    public void checkTouch(Rectangle2D.Float hitbox) {
        for (Potion p : potions) {
            if (p.isActive()) {
                if (hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    whatIsEffectIfPotion(p);
                }
            }
        }
    }
    public void whatIsEffectIfPotion(Potion p) {
        if (p.getObj() == HEALTH_POTION)
            play.getPlayer().changeHealth(HEALTH_POTION_VALUE);
        else
            play.getPlayer().changePower(POWER_POTION_VALUE);
    }
    public void checkHit(Rectangle2D.Float attackbox) {
        for (GameBox b : boxs)
            if (b.isActive() && !b.prizeAnimation) {
                if (b.getHitbox().intersects(attackbox)) {
                    b.setPrizeAnimation(true);
                    int type = 0;
                    if (b.getObj() == BOX1)
                        type = 1;
                    potions.add(new Potion((int) (b.getHitbox().x + b.getHitbox().width / 2), (int) (b.getHitbox().y - b.getHitbox().height / 2), type));
                    return;
                }
            }
    }
    public void checkTouchTrap(Player p) {
        for (GameTrap t : traps)
            if (t.getHitbox().intersects(p.getHitbox()))
                p.kill();
    }
    private void loadingImages() {
        BufferedImage potionSprite = GetDownloadSave.GetPresenceSprite(GetDownloadSave.POTION);
        potionImages = new BufferedImage[2][7];
        for (int j = 0; j < potionImages.length; j++) {
            for (int i = 0; i < potionImages[j].length; i++) {
                potionImages[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
            }
        }
        BufferedImage boxSprite = GetDownloadSave.GetPresenceSprite(GetDownloadSave.TREASURE_CHEST);
        boxImages = new BufferedImage[2][7];
        for (int j = 0; j < boxImages.length; j++) {
            for (int i = 0; i < boxImages[j].length; i++) {
                boxImages[j][i] = boxSprite.getSubimage(40 * i, 30 * j, 40, 30);
            }
        }
        trapImage = GetDownloadSave.GetPresenceSprite(GetDownloadSave.TRAP);
        weaponImages = new BufferedImage[7];
        BufferedImage temp = GetDownloadSave.GetPresenceSprite(GetDownloadSave.WEAPON_ENEMY);
        for (int i = 0; i < weaponImages.length; i++) {
            weaponImages[i] = temp.getSubimage(i * 40, 0, 40, 26);
        }
        coreWeaponsImage = GetDownloadSave.GetPresenceSprite(GetDownloadSave.CORE_WEAPON);
    }
    public void loadingPrize(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        boxs = new ArrayList<>(newLevel.getBoxs());
        traps = newLevel.getTraps();
        weapons = newLevel.getWeapons();
        coreWeapons.clear();
    }
    private boolean isPlayerInRange(Weapon w, Player player) {
        int absValue = (int) Math.abs(player.getHitbox().x - w.getHitbox().x);
        return absValue <= Game.SIZE_TILE * 5;
    }
    private boolean isPlayerInfrontOfWeapon(Weapon w, Player player) {
        if (w.getObj() == WEAPON_LEFT) {
            if (w.getHitbox().x > player.getHitbox().x) {
                return true;
            }
        } else if (w.getHitbox().x < player.getHitbox().x) {
            return true;
        }
        return false;
    }
    private void shootWeapon(Weapon w) {
        int dir = 1;
        if (w.getObj() == WEAPON_LEFT)
            dir = -1;
        coreWeapons.add(new CoreWeapon((int) w.getHitbox().x, (int) w.getHitbox().y, dir));
    }
    public void update(int[][] creatLevel, Player player) {
        for (Potion p : potions)
            if (p.isActive())
                p.update();
        for (GameBox b : boxs)
            if (b.isActive())
                b.update();
        updateWeapons(creatLevel, player);
        updateCoreWeapons(creatLevel, player);
    }
    private void updateCoreWeapons(int[][] creatLevel, Player player) {
        for (CoreWeapon c : coreWeapons)
            if (c.isActive()) {
                c.updatePosition();
                if (c.getHitbox().intersects(player.getHitbox())) {
                    player.changeHealth(-25);
                    c.setActive(false);
                } else if (CoreWeaponHittingLevel(c, creatLevel))
                    c.setActive(false);
            }
    }
    private void updateWeapons(int[][] creatLevel, Player player) {
        for (Weapon w : weapons) {
            if (!w.prizeAnimation)
                if (w.getTileY() == player.getTileY())
                    if (isPlayerInRange(w, player))
                        if (isPlayerInfrontOfWeapon(w, player))
                            if (CanWeaponSeePlayer(creatLevel, player.getHitbox(), w.getHitbox(), w.getTileY())) {
                                w.setPrizeAnimation(true);
                            }
            w.update();
            if (w.getIndexAni() == 4 && w.getTickAni() == 0) {
                shootWeapon(w);
            }
        }
    }
    public void draw(Graphics g, int xLevelOffset) {
        drawPotions(g, xLevelOffset);
        drawBoxs(g, xLevelOffset);
        drawTraps(g, xLevelOffset);
        drawWeapons(g, xLevelOffset);
        drawCoreWeapons(g, xLevelOffset);
    }
    private void drawCoreWeapons(Graphics g, int xLevelOffset) {
        for (CoreWeapon c : coreWeapons)
            if (c.isActive())
                g.drawImage(coreWeaponsImage, (int) (c.getHitbox().x - xLevelOffset), (int) (c.getHitbox().y), CORE_WEAPON_WIDTH, CORE_WEAPON_HEIGHT, null);
    }
    private void drawWeapons(Graphics g, int xLevelOffset) {
        for (Weapon w : weapons) {
            int x = (int) (w.getHitbox().x - xLevelOffset);
            int width = WEAPON_WIDTH;
            if (w.getObj() == WEAPON_RIGHT) {
                x += width;
                width *= -1;
            }
            g.drawImage(weaponImages[w.getIndexAni()], x, (int) (w.getHitbox().y), width, WEAPON_HEIGHT, null);
        }
    }
    private void drawPotions(Graphics g, int xLevelOffset) {
        for (Potion p : potions)
            if (p.isActive()) {
                int type = 0;
                if (p.getObj() == HEALTH_POTION)
                    type = 1;
                g.drawImage(potionImages[type][p.getIndexAni()], (int) (p.getHitbox().x - p.getOffsetX() - xLevelOffset), (int) (p.getHitbox().y - p.getOffsetY()), POTION_WIDTH, POTION_HEIGHT,
                        null);
            }
    }
    private void drawBoxs(Graphics g, int xLevelOffset) {
        for (GameBox b : boxs)
            if (b.isActive()) {
                int type = 0;
                if (b.getObj() == BOX1)
                    type = 1;
                g.drawImage(boxImages[type][b.getIndexAni()], (int) (b.getHitbox().x - b.getOffsetX() - xLevelOffset), (int) (b.getHitbox().y - b.getOffsetY()), BOX_WIDTH,
                        BOX_HEIGHT, null);
            }
    }
    public void resetAllObjects() {
        loadingPrize(play.getHandlerLevel().getPresentLevel());
        for (Potion p : potions)
            p.reset();
        for (GameBox b : boxs)
            b.reset();
        for (Weapon w : weapons)
            w.reset();
    }
    private void drawTraps(Graphics g, int xLvlOffset) {
        for (GameTrap t : traps)
            g.drawImage(trapImage, (int) (t.getHitbox().x - xLvlOffset), (int) (t.getHitbox().y - t.getOffsetY()), TRAP_1_WIDTH, TRAP_1_HEIGHT, null);
    }
}
