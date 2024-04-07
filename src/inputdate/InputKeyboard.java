package inputdate;

import gamestates.GameState;
import main.GamePanel;
import static auxiliary.Consts.Direction.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class InputKeyboard implements KeyListener {
    private GamePanel gamePanel;
    public InputKeyboard(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAY:
                gamePanel.getGame().getPlay().keyPressed(e);
                break;
            case SETTINGS:
                gamePanel.getGame().getActionsMenu().keyPressed(e);
                break;
            default:
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAY:
                gamePanel.getGame().getPlay().keyReleased(e);
                break;
            default:
                break;
        }
    }
}
