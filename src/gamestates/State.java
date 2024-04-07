package gamestates;

import main.Game;
import userinterface.ButtonsInMenu;
import java.awt.event.MouseEvent;
public class State {
    protected Game game;
    public State(Game game) {
        this.game = game;
    }
    public Game getGame() {
        return game;
    }
    public boolean pressButtonInside(MouseEvent e, ButtonsInMenu buttonMenu) {
        return buttonMenu.getHitboxButton().contains(e.getX(), e.getY());
    }
}
