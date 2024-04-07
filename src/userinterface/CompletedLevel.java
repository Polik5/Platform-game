package userinterface;

import auxiliary.GetDownloadSave;
import gamestates.GameState;
import gamestates.Play;
import main.Game;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.image.BufferedImage;

import static auxiliary.Consts.UserInterface.PRRButtons.*;

public class CompletedLevel {
    private Play play;
    private PauseRestartReturnButtons menu, next;
    private BufferedImage image;
    private int bgX, bgY, bgW, bgH;
    public CompletedLevel(Play play) {
        this.play = play;
        initializationImage();
        initializationButtons();
    }
    private void initializationButtons() {
        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        next = new PauseRestartReturnButtons(nextX, y, PRR_SIZE, PRR_SIZE, 0);
        menu = new PauseRestartReturnButtons(menuX, y, PRR_SIZE, PRR_SIZE, 2);
    }
    private void initializationImage() {
        image = GetDownloadSave.GetPresenceSprite(GetDownloadSave.COMPLETED_LEVEL);
        bgW = (int) (image.getWidth() * Game.SCALE);
        bgH = (int) (image.getHeight() * Game.SCALE);
        bgX = Game.WIDTH_GAME / 2 - bgW / 2;
        bgY = (int) (75 * Game.SCALE);
    }
    private boolean isIn(PauseRestartReturnButtons p, MouseEvent e) {
        return p.getBounds().contains(e.getX(), e.getY());
    }
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.WIDTH_GAME, Game.HEIGHT_GAME);
        g.drawImage(image, bgX, bgY, bgW, bgH, null);
        next.draw(g);
        menu.draw(g);
    }
    public void update() {
        next.update();
        menu.update();
    }
    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);
        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(next, e))
            next.setMouseOver(true);
    }
    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                play.resetAll();
                GameState.state = GameState.MENU;
            }
        } else if (isIn(next, e))
            if (next.isMousePressed())
                play.loadingNextLevel();
        menu.resetBools();
        next.resetBools();
    }
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(next, e))
            next.setMousePressed(true);
    }
}
