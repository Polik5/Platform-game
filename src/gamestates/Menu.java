package gamestates;

import auxiliary.GetDownloadSave;
import main.Game;
import userinterface.ButtonsInMenu;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
public class Menu extends State implements Methods{
    private ButtonsInMenu[] buttons = new ButtonsInMenu[3];
    private BufferedImage menuBackgroundImage, backMenu;
    private int menuX, menuY, menuWidth, menuHeight;
    public Menu(Game game) {
        super(game);
        loadingButtons();
        loadingBackground();
        backMenu = GetDownloadSave.GetPresenceSprite(GetDownloadSave.MENU_BACKGROUND_IMAGE);
    }
    @Override
    public void update() {
        for (ButtonsInMenu buttonMenu : buttons) {
            buttonMenu.update();
        }
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(backMenu, 0, 0, Game.WIDTH_GAME, Game.HEIGHT_GAME, null);
        g.drawImage(menuBackgroundImage, menuX, menuY, menuWidth, menuHeight, null);
        for (ButtonsInMenu buttonMenu : buttons) {
            buttonMenu.draw(g);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {
        for (ButtonsInMenu buttonMenu : buttons) {
            if (pressButtonInside(e, buttonMenu)) {
                buttonMenu.setMousePressed(true);
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        for (ButtonsInMenu buttonMenu : buttons) {
            if (pressButtonInside(e, buttonMenu)) {
                if (buttonMenu.isMousePressed())
                    buttonMenu.applyGameState();
                break;
            }
        }
        resetButtons();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        for (ButtonsInMenu buttonMenu : buttons) {
            buttonMenu.setMouseOver(false);
        }
        for (ButtonsInMenu buttonMenu : buttons) {
            if (pressButtonInside(e, buttonMenu)) {
                buttonMenu.setMouseOver(true);
                break;
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            GameState.state = GameState.PLAY;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    private void loadingButtons() {
        buttons[0] = new ButtonsInMenu(Game.WIDTH_GAME / 2, (int) (150 * Game.SCALE), 0, GameState.PLAY);
        buttons[1] = new ButtonsInMenu(Game.WIDTH_GAME / 2, (int) (220 * Game.SCALE), 1, GameState.SETTINGS);
        buttons[2] = new ButtonsInMenu(Game.WIDTH_GAME / 2, (int) (290 * Game.SCALE), 2, GameState.EXIT);
    }
    private void resetButtons() {
        for (ButtonsInMenu buttonMenu : buttons) {
            buttonMenu.resetBools();
        }
    }
    private void loadingBackground() {
        menuBackgroundImage = GetDownloadSave.GetPresenceSprite(GetDownloadSave.MENU_BACKGROUND);
        menuWidth = (int) (menuBackgroundImage.getWidth() * Game.SCALE);
        menuHeight = (int) (menuBackgroundImage.getHeight() * Game.SCALE);
        menuX = Game.WIDTH_GAME / 2 - menuWidth / 2;
        menuY = (int) (45 * Game.SCALE);
    }
}
