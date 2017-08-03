package com.mprtcz.gameoflife.board;

import com.mprtcz.gameoflife.game.Tile;
import javafx.application.Platform;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import static com.mprtcz.gameoflife.calc.Adjacency.getIndexFromXY;

/**
 * @author Michal_Partacz
 */
public class BoardOperator {

    private static final double FIELD_SIZE = 30;
    private int width;
    private Board board;

    public BoardOperator(int width, Board board) {
        this.width = width;
        this.board = board;
    }

    public void initializeBoard() {
        fillTheBoard(board.getGridPane());
    }


    private void fillTheBoard(GridPane gridPane) {
        for (int column = 0; column < width; column++) {
            Platform.runLater(() -> gridPane.getRowConstraints().add(new RowConstraints(FIELD_SIZE)));
            for (int row = 0; row < width; row++) {
                if (column == 0 && row == 0) {
                    Platform.runLater(() -> gridPane.getColumnConstraints().add(new ColumnConstraints(FIELD_SIZE)));
                } else if (column == 0) {
                    Platform.runLater(() -> gridPane.getColumnConstraints().add(new ColumnConstraints(FIELD_SIZE)));
                } else {
                    createNewBoardTileAndAddIt(column, row);
                }
            }
        }
    }

    private void createNewBoardTileAndAddIt(int column, int row) {
        Tile tile = new Tile();
        board.getBoard().put(getIndexFromXY(row, column, width), tile);
        Platform.runLater(() -> board.getGridPane().add(tile, column, row));
    }
}
