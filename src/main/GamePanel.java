package main;

import inputdate.InputKeyboard;
import inputdate.InputMouse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static auxiliary.Consts.ConstsPlayer.*;
import static auxiliary.Consts.Direction.*;
import static main.Game.HEIGHT_GAME;
import static main.Game.WIDTH_GAME;


public class GamePanel extends JPanel {
    private InputMouse inputMouse;
    private Game game;
    public GamePanel(Game game){
        inputMouse = new InputMouse(this);
        this.game = game;
        sizePanel();
        addKeyListener(new InputKeyboard(this));
        addMouseListener(inputMouse);
        addMouseMotionListener(inputMouse);
    }
    private void sizePanel() {
        Dimension size = new Dimension(WIDTH_GAME,HEIGHT_GAME);
        setPreferredSize(size);
        System.out.println("size " + WIDTH_GAME + " : " + HEIGHT_GAME);
    }

    public void GameUpdate(){

    }
    public void paint(Graphics g){
        super.paint(g);
        game.rendering(g);
    }
    public Game getGame(){
        return game;
    }
}
