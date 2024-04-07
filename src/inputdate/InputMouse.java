package inputdate;

import gamestates.GameState;
import main.Game;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputMouse implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;
    public InputMouse(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case PLAY:
                gamePanel.getGame().getPlay().mouseClicked(e);
                break;
            default:
                break;
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAY:
                gamePanel.getGame().getPlay().mousePressed(e);
                break;
            case SETTINGS:
                gamePanel.getGame().getActionsMenu().mousePressed(e);
                break;
            default:
                break;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAY:
                gamePanel.getGame().getPlay().mouseReleased(e);
                break;
            case SETTINGS:
                gamePanel.getGame().getActionsMenu().mouseReleased(e);
                break;
            default:
                break;
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAY:
                gamePanel.getGame().getPlay().mouseMoved(e);
                break;
            case SETTINGS:
                gamePanel.getGame().getActionsMenu().mouseMoved(e);
                break;
            default:
                break;
        }
    }
}
