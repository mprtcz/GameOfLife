package com.mprtcz.gameoflife.board;

import com.mprtcz.gameoflife.game.Tile;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal_Partacz
 */
public class Board {

    public void setBoard(Map<Integer, Tile> board) {
        this.board = board;
    }

    Map<Integer, Tile> board;
    GridPane gridPane;

    public Board(GridPane gridPane) {
        this.gridPane = gridPane;
        this.board = new HashMap<>();
    }

    public Map<Integer, Tile> getBoard() {
        return board;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

}
