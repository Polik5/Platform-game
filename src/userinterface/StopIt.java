package userinterface;

import auxiliary.GetDownloadSave;
import gamestates.Play;
import gamestates.GameState;
import main.Game;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static auxiliary.Consts.UserInterface.PRRButtons.*;
public class StopIt {
    private Play play;
    private BufferedImage backImage;
    private int pX, pY, pW, pH;
    private PauseRestartReturnButtons returnButton, restartButton, pauseButton;
    public StopIt(Play play) {
        this.play = play;
        loadingBack();
        createPauseRestartReturnButtons();
    }
    private void createPauseRestartReturnButtons() {
        int returnButton0 = (int) (313 * Game.SCALE);
        int restartButton0 = (int) (387 * Game.SCALE);
        int pauseButton0 = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);
        returnButton = new PauseRestartReturnButtons(returnButton0, bY, PRR_SIZE, PRR_SIZE, 2);
        restartButton = new PauseRestartReturnButtons(restartButton0, bY, PRR_SIZE, PRR_SIZE, 1);
        pauseButton = new PauseRestartReturnButtons(pauseButton0, bY, PRR_SIZE, PRR_SIZE, 0);
    }
    public void update() {
        returnButton.update();
        restartButton.update();
        pauseButton.update();
    }
    public void draw(Graphics g) {
        g.drawImage(backImage, pX, pY, pW, pH, null);
        returnButton.draw(g);
        restartButton.draw(g);
        pauseButton.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        if (pressButtonInside(e, returnButton))
            returnButton.setMousePressed(true);
        else if (pressButtonInside(e, restartButton))
            restartButton.setMousePressed(true);
        else if (pressButtonInside(e, pauseButton))
            pauseButton.setMousePressed(true);
    }
    public void mouseReleased(MouseEvent e) {
        if (pressButtonInside(e, returnButton)) {
            if (returnButton.isMousePressed()) {
                play.resetAll();
                GameState.state = GameState.MENU;
                play.unpauseGame();
            }
        } else if (pressButtonInside(e, restartButton)) {
            if (restartButton.isMousePressed()) {
                play.resetAll();
                play.unpauseGame();
            }
        } else if (pressButtonInside(e, pauseButton)) {
            if (pauseButton.isMousePressed()) {
                play.unpauseGame();
            }
        }
        returnButton.resetBools();
        restartButton.resetBools();
        pauseButton.resetBools();
    }
    public void mouseMoved(MouseEvent e) {
        returnButton.setMouseOver(false);
        restartButton.setMouseOver(false);
        pauseButton.setMouseOver(false);
        if (pressButtonInside(e, returnButton))
            returnButton.setMouseOver(true);
        else if (pressButtonInside(e, restartButton))
            restartButton.setMouseOver(true);
        else if (pressButtonInside(e, pauseButton))
            pauseButton.setMouseOver(true);
    }
    private void loadingBack() {
        backImage = GetDownloadSave.GetPresenceSprite(GetDownloadSave.PAUSE_BACKGROUND);
        pW = (int) (backImage.getWidth() * Game.SCALE);
        pH = (int) (backImage.getHeight() * Game.SCALE);
        pX = Game.WIDTH_GAME / 2 - pW / 2;
        pY = (int) (25 * Game.SCALE);
    }
    private boolean pressButtonInside(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
