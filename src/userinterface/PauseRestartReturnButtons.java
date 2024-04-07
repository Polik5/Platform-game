package userinterface;

import auxiliary.GetDownloadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import static auxiliary.Consts.UserInterface.PRRButtons.*;
public class PauseRestartReturnButtons extends PauseButton{
    private BufferedImage[] images;
    private int prrIndex, index;
    private boolean mouseOver, mousePressed;
    public PauseRestartReturnButtons(int x, int y, int width, int height, int prrIndex) {
        super(x, y, width, height);
        this.prrIndex = prrIndex;
        uploadImages();
    }
    private void uploadImages() {
        BufferedImage temp = GetDownloadSave.GetPresenceSprite(GetDownloadSave.PAUSE_RESTART_RETURN_BUTTONS);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * DEFAULT_SIZE_PRR, prrIndex * DEFAULT_SIZE_PRR, DEFAULT_SIZE_PRR, DEFAULT_SIZE_PRR);
        }
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
    public void draw(Graphics g) {
        g.drawImage(images[index], x, y, PRR_SIZE, PRR_SIZE, null);
    }
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
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
}
