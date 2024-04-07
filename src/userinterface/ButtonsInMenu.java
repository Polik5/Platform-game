package userinterface;

import auxiliary.GetDownloadSave;
import gamestates.GameState;
import java.awt.*;
import java.awt.image.BufferedImage;
import static auxiliary.Consts.UserInterface.Buttons.*;
public class ButtonsInMenu {
    private int xPos, yPos, indexButton, index;
    private GameState state;
    private BufferedImage[] images;
    private int xOffsetCenter = BUTTON_WIDTH / 2;
    private boolean mouseOver, mousePressed;
    private Rectangle hitboxButton;
    public ButtonsInMenu(int xPos, int yPos, int indexButton, GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.indexButton = indexButton;
        this.state = state;
        uploadImages();
        initializationHitboxButton();
    }
    private void uploadImages() {
        images = new BufferedImage[3];
        BufferedImage temp = GetDownloadSave.GetPresenceSprite(GetDownloadSave.BUTTONS_MENU);
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * DEFAULT_WIDTH_OF_BUTTON, indexButton * DEFAULT_HEIGHT_OF_BUTTON, DEFAULT_WIDTH_OF_BUTTON, DEFAULT_HEIGHT_OF_BUTTON);
        }
    }
    public void draw(Graphics g) {
        g.drawImage(images[index], xPos - xOffsetCenter, yPos, BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }
    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }
    public boolean isMouseOver() {
        return mouseOver;
    }
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
    public boolean isMousePressed() {
        return mousePressed;
    }
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
    public Rectangle getHitboxButton() {
        return hitboxButton;
    }
    private void initializationHitboxButton() {
        hitboxButton = new Rectangle(xPos - xOffsetCenter, yPos, BUTTON_WIDTH, BUTTON_HEIGHT);
    }
    public void applyGameState() {
        GameState.state = state;
    }
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
