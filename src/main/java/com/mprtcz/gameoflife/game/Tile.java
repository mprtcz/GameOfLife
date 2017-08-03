package com.mprtcz.gameoflife.game;

import com.mprtcz.gameoflife.styles.Status;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * @author Michal_Partacz
 */
public class Tile extends Button {
    private static final double MAX_SIZE = 1000;
    boolean isAlive = false;
    private Status tileStatus = Status.DEAD;

    public Tile() {
        setMaxSize(MAX_SIZE, MAX_SIZE);
        this.setOnAction(event -> setOppositeState());
    }

    public void kill() {
        if(tileStatus == Status.ALIVE) {
            tileStatus = Status.VISITED;
        } else {
            tileStatus = Status.DEAD;
        }
        colorTheButton();
    }

    public void revive() {
        tileStatus = Status.ALIVE;
        colorTheButton();
    }

    private void colorTheButton() {
        Platform.runLater(() -> setStyle(tileStatus.getStyle()));
    }

    private void setOppositeState() {
        if(tileStatus == Status.ALIVE) {
            tileStatus = Status.DEAD;
        } else {
            tileStatus = Status.ALIVE;
        }
        colorTheButton();
    }

}
