package application;

import java.awt.*;
import javafx.scene.control.Button;

public class ButtonMine extends Button {
    protected boolean isMined;

    public ButtonMine(boolean isMined) throws HeadlessException {
        this.isMined = isMined;
    }

    public boolean isMined() {
        return isMined;
    }

    public void setMined(boolean mined) {
        isMined = mined;
    }
}
