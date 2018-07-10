package com.mprtcz.gameoflife.board;

import com.mprtcz.gameoflife.game.Tile;
import com.mprtcz.gameoflife.styles.Status;
import javafx.application.Platform;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

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

    void changeBoardStatus(Integer key, Status value) {
        board.get(key).changeStatus(value);
    }

    public void setBoard(Map<Integer, Tile> board) {
        this.board = board;
    }

    void addTileToPosition(Tile tile, int column, int row) {
        Platform.runLater(() -> gridPane.add(tile, column, row));
    }

    void addRowConstraint(double fieldSize) {
        Platform.runLater(() -> gridPane.getRowConstraints().add(new RowConstraints(fieldSize)));
    }

    void addColumnConstraint(double fieldSize) {
        Platform.runLater(() -> gridPane.getColumnConstraints().add(new ColumnConstraints(fieldSize)));
    }
}