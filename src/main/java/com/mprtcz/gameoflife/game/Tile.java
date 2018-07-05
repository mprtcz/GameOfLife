package com.mprtcz.gameoflife.game;

import com.mprtcz.gameoflife.styles.Status;
import javafx.application.Platform;
import javafx.scene.control.Button;

/**
 * @author Michal_Partacz
 */
public class Tile extends Button {
    private static final double MAX_SIZE = 1000;
    private Status tileStatus = Status.DEAD;

    public Status getTileStatus() {
        return tileStatus;
    }

    public Tile() {
        setMaxSize(MAX_SIZE, MAX_SIZE);
        this.setOnAction(event -> setOppositeState());
    }

    public void changeStatus(Status newStatus) {
        if(newStatus == Status.DEAD) {
            if(tileStatus == Status.ALIVE) {
                tileStatus = Status.VISITED;
            }
        } else {
            tileStatus = newStatus;
        }
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