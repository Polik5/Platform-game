package auxiliary;

import entities.ExactlyEnemy;
import main.Game;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import static auxiliary.Consts.EnemyConstants.*;
public class GetDownloadSave {
    public static final String PRESENCE_PLAYER = "charatersprites.png";
    public static final String PRESENCE_LEVEL = "outside_sprites.png";
    public static final String BUTTONS_MENU = "menubuttons.png";
    public static final String MENU_BACKGROUND = "menubackground.png";
    public static final String PAUSE_BACKGROUND = "menuactions.png";
    public static final String PAUSE_RESTART_RETURN_BUTTONS = "buttonsactions.png";
    public static final String MENU_BACKGROUND_IMAGE = "Cartoon_Forest_BG_01.png";
    public static final String PLAY_BACK_IMAGE = "Cartoon_Forest_BG_01.png";
    public static final String ENEMY_SPRITE = "enemy.png";
    public static final String HEALTH_PANEL = "health_power_panel.png";
    public static final String COMPLETED_LEVEL = "completed_sprite.png";
    public static final String POTION = "potion.png";
    public static final String TREASURE_CHEST = "box.png";
    public static final String TRAP = "trap.png";
    public static final String WEAPON_ENEMY = "weapon.png";
    public static final String CORE_WEAPON = "core.png";
    public static final String DEAT_PANEL = "death_screen.png";
    public static final String ACTIONS_MENU = "actions_menu.png";

    public static BufferedImage GetPresenceSprite(String fileName){
        BufferedImage image = null;
        InputStream inputPlayer = GetDownloadSave.class.getResourceAsStream("/" + fileName);
        try {
            image = ImageIO.read(inputPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputPlayer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }
    public static BufferedImage[] CompletingAllLevels() {
        URL url = GetDownloadSave.class.getResource("/images_levels");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];
        for (int i = 0; i < filesSorted.length; i++)
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png")) {
                    filesSorted[i] = files[j];
                }
            }
        BufferedImage[] images = new BufferedImage[filesSorted.length];
        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }
}
