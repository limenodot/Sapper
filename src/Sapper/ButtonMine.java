package Sapper;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static Sapper.Main.X_CELLS;
import static Sapper.Main.Y_CELLS;

public class ButtonMine extends Button {
    protected int xPos;
    protected int yPos;
    protected boolean isMined;
    protected boolean isActivated;
    protected boolean isFlagged;
    protected boolean isAsked;
    protected int numberOfBombsAround;

    public ButtonMine(int xPos, int yPos, boolean isMined, boolean isActivated, boolean isFlagged, int bombsAround) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isMined = isMined;
        this.isActivated = isActivated;
        this.isFlagged = isFlagged;
        this.numberOfBombsAround = bombsAround;
        this.isAsked = isAsked;
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
    public boolean isAsked() {
        return isAsked;
    }

    public void setAsked(boolean asked) {
        isAsked = asked;
    }

    public int getNumberOfBombsAround() {
        return numberOfBombsAround;
    }

    public void setNumberOfBombsAround(int bombsAround) {
        this.numberOfBombsAround = bombsAround;
    }



    public List<ButtonMine> getNeighbours(ButtonMine button, ButtonMine[][] minefield) {
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
        if (this.getNumberOfBombsAround() != 0) {
            String path = String.format("/Pics/%s.png", this.getNumberOfBombsAround());
            InputStream input = getClass().getResourceAsStream(path);
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            this.setStyle("-fx-background-color: white; -fx-border-color: grey");
            this.setGraphic(imageView);
            this.setActivated(true);
        } else {

            this.setStyle("-fx-background-color: white; -fx-border-color: grey");
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
                if (newX >= 0 && newX < X_CELLS && newY >= 0 && newY < Y_CELLS) {
                    if (minefield[newX][newY].isActivated == false) {
                        try {
                            minefield[newX][newY].press(minefield);
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            System.out.println(" ");
                        }
                    }
                }
                this.setActivated(true);
            }

            return;
        }
    }


}