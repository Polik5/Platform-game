package userinterface;

import auxiliary.GetDownloadSave;
import gamestates.GameState;
import gamestates.Play;
import main.Game;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static auxiliary.Consts.UserInterface.PRRButtons.*;
public class GameOver {
    private Play play;
    private BufferedImage image;
    private int imgX, imgY, imgW, imgH;
    private PauseRestartReturnButtons menu, playing;
    public GameOver(Play play) {
        this.play = play;
        createImage();
        createButtons();
    }
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.WIDTH_GAME, Game.HEIGHT_GAME);
        g.drawImage(image, imgX, imgY, imgW, imgH, null);
        menu.draw(g);
        playing.draw(g);
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            play.resetAll();
            GameState.state = GameState.MENU;
        }
    }
    private void createImage() {
        image = GetDownloadSave.GetPresenceSprite(GetDownloadSave.DEAT_PANEL);
        imgW = (int) (image.getWidth() * Game.SCALE);
        imgH = (int) (image.getHeight() * Game.SCALE);
        imgX = Game.WIDTH_GAME / 2 - imgW / 2;
        imgY = (int) (100 * Game.SCALE);
    }
    private void createButtons() {
        int menuX = (int) (335 * Game.SCALE);
        int playX = (int) (440 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        playing = new PauseRestartReturnButtons(playX, y, PRR_SIZE, PRR_SIZE, 0);
        menu = new PauseRestartReturnButtons(menuX, y, PRR_SIZE, PRR_SIZE, 2);
    }
    public void update() {
        menu.update();
        playing.update();
    }
    private boolean isIn(PauseRestartReturnButtons p, MouseEvent e) {
        return p.getBounds().contains(e.getX(), e.getY());
    }
    public void mouseMoved(MouseEvent e) {
        playing.setMouseOver(false);
        menu.setMouseOver(false);
        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(playing, e))
            playing.setMouseOver(true);
    }
    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                play.resetAll();
                GameState.state = GameState.MENU;
            }
        } else if (isIn(playing, e))
            if (playing.isMousePressed())
                play.resetAll();
        menu.resetBools();
        playing.resetBools();
    }
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(playing, e))
            playing.setMousePressed(true);
    }
}
