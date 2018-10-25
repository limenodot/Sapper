package application;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

import static application.SapperApplication.X_CELLS;
import static application.SapperApplication.Y_CELLS;

public class ButtonMine extends Button {
    protected int xPos;
    protected int yPos;
    protected boolean isMined;
    protected boolean isActivated;
    protected boolean isFlagged;
    protected int bombsAround = 0;

    public ButtonMine(int xPos, int yPos, boolean isMined, boolean isActivated, boolean isFlagged, int bombsAround) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isMined = isMined;
        this.isActivated = isActivated;
        this.isFlagged = isFlagged;
        this.bombsAround = bombsAround;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public boolean isMined() {
        return isMined;
    }

    public void setMined(boolean mined) {
        isMined = mined;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public int getBombsAround() {
        return bombsAround;
    }

    public void setBombsAround(int bombsAround) {
        this.bombsAround = bombsAround;
    }

    public List<ButtonMine> countBombsAround(ButtonMine button, ButtonMine[][] minefield) {
        List<ButtonMine> neighbours = new ArrayList<>();

        int[] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, 1,
                1, 0,
                1, -1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];
            int newX = this.xPos + dx;
            int newY = this.yPos + dy;

            if (newX >= 0 && newX < X_CELLS
                    && newY >= 0 && newY < Y_CELLS) {
                neighbours.add(minefield[newX][newY]);
            }
        }

        return neighbours;
    }

    public void press(ButtonMine[][] minefield) {
        ButtonMine b = minefield[this.getxPos()][this.getyPos()];

        int[] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, 1,
                1, 0,
                1, -1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];
            int newX = b.xPos + dx;
            int newY = b.yPos + dy;

            if (newX >= 0 && newX < X_CELLS
                    && newY >= 0 && newY < Y_CELLS) {
                ButtonMine target = minefield[newX][newY];
                if (target.getBombsAround() == 0) {
                    target.setStyle("-fx-background-color: white");
                    target.press(minefield);
                }
                else {
                    String path = String.format("/Pics/%s.png", target.getBombsAround());
                    Image image1 = new Image(path);
                    ImageView imageView1 = new ImageView(image1);
                    b.setGraphic(imageView1);
                }
            }

        }
    }
}
