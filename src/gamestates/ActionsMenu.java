package gamestates;

import auxiliary.GetDownloadSave;
import main.Game;
import userinterface.PauseButton;
import userinterface.PauseRestartReturnButtons;
import userinterface.StopIt;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static auxiliary.Consts.UserInterface.PRRButtons.PRR_SIZE;

public class ActionsMenu extends State implements Methods{
    private BufferedImage backImg, actionsBackImg;
    private int bgX, bgY, bgW, bgH;
    private PauseRestartReturnButtons menuB;
    public ActionsMenu(Game game) {
        super(game);
        loadingImages();
        loadingButton();
    }
    private void loadingButton() {
        int menuX = (int) (387 * Game.SCALE);
        int menuY = (int) (325 * Game.SCALE);
        menuB = new PauseRestartReturnButtons(menuX, menuY, PRR_SIZE, PRR_SIZE, 2);
    }
    private void loadingImages() {
        backImg = GetDownloadSave.GetPresenceSprite(GetDownloadSave.MENU_BACKGROUND_IMAGE);
        actionsBackImg = GetDownloadSave.GetPresenceSprite(GetDownloadSave.ACTIONS_MENU);
        bgW = (int) (actionsBackImg.getWidth() * Game.SCALE);
        bgH = (int) (actionsBackImg.getHeight() * Game.SCALE);
        bgX = Game.WIDTH_GAME / 2 - bgW / 2;
        bgY = (int) (33 * Game.SCALE);
    }
    @Override
    public void update() {
        menuB.update();
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(backImg, 0, 0, Game.WIDTH_GAME, Game.HEIGHT_GAME, null);
        g.drawImage(actionsBackImg, bgX, bgY, bgW, bgH, null);
        menuB.draw(g);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        if (isIn(e, menuB))
            menuB.setMouseOver(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                GameState.state = GameState.MENU;
            }
        }
        menuB.resetBools();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            GameState.state = GameState.MENU;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    private boolean isIn(MouseEvent e, PauseButton p) {
        return p.getBounds().contains(e.getX(), e.getY());
    }
}
