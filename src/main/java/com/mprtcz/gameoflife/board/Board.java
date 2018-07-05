package com.mprtcz.gameoflife.board;

import com.mprtcz.gameoflife.game.Tile;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal_Partacz
 */
public class Board {

    private Map<Integer, Tile> board;

    private GridPane gridPane;
    public Board(GridPane gridPane) {
        this.gridPane = gridPane;
        this.board = new HashMap<>();
    }

    Map<Integer, Tile> getBoard() {
        return board;
    }

    public void setBoard(Map<Integer, Tile> board) {
        this.board = board;
    }

    GridPane getGridPane() {
        return gridPane;
    }
}