package main;

import gamestates.ActionsMenu;
import gamestates.GameState;
import gamestates.Menu;
import gamestates.Play;
import java.awt.*;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int SetFPS = 120;
    private final int SetUPS = 200;
    public final static int DEFAULT_TILE_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_BY_WIDTH = 26;
    public final static int TILES_BY_HEIGHT = 14;
    public final static int SIZE_TILE = (int) (DEFAULT_TILE_SIZE * SCALE);
    public final static int WIDTH_GAME = SIZE_TILE * TILES_BY_WIDTH;
    public final static int HEIGHT_GAME = SIZE_TILE * TILES_BY_HEIGHT;
    private Play play;
    private Menu menu;
    private ActionsMenu actionsMenu;

    public Game(){
        initializationClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();
    }
    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    private void update() {
        switch (GameState.state) {
            case MENU:
                menu.update();
                break;
            case PLAY:
                play.update();
                break;
            case SETTINGS:
                actionsMenu.update();
                break;
            case EXIT:
            default:
                System.exit(0);
                break;
        }
    }
    public void rendering(Graphics g){
        switch (GameState.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAY:
                play.draw(g);
                break;
            case SETTINGS:
                actionsMenu.draw(g);
                break;
            default:
                break;
        }
    }
    @Override
    public void run() {
        double timePerFrame = 1000000000.0/SetFPS;
        double timePerUpdate = 1000000000.0/SetUPS;
        long previousTime = System.nanoTime();
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double U = 0;
        double F = 0;
        while (true){
            long currentTime = System.nanoTime();
            U += (currentTime - previousTime) / timePerUpdate;
            F += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if(U >= 1){
                update();
                updates++;
                U--;
            }
            if(F >= 1){
                gamePanel.repaint();
                frames++;
                F--;
            }
            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("fps " + frames + " | ups " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
    private void initializationClasses() {
        menu = new Menu(this);
        play = new Play(this);
        actionsMenu = new ActionsMenu(this);
    }
    public void focusGameWindowLost(){
        if (GameState.state == GameState.PLAY) {
            play.getPlayer().resettingLogicalValues();
        }
    }
    public Menu getMenu() {
        return menu;
    }
    public Play getPlay() {
        return play;
    }
    public ActionsMenu getActionsMenu() {
        return actionsMenu;
    }
}
