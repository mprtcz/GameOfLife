package com.mprtcz.gameoflife.board;

import com.mprtcz.gameoflife.game.Tile;
import com.mprtcz.gameoflife.styles.Status;
import javafx.application.Platform;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;
import java.util.Map;

import static com.mprtcz.gameoflife.calc.Adjacency.getIndexFromXY;

/**
 * @author Michal_Partacz
 */
public class BoardOperator {

    private static final double FIELD_SIZE = 30;
    private int width;
    private Board board;
    Map<Integer, Status> oldStatusesMap = new HashMap<>();

    public BoardOperator(int width, Board board) {
        this.width = width;
        this.board = board;
    }

    public boolean applyNewStatuses(Map<Integer, Status> statusesMap) {
        System.out.println("statusesMap = " + statusesMap);
        if(statusesMap.equals(oldStatusesMap)) {return false;}
        for (Map.Entry<Integer, Status> entry: statusesMap.entrySet()){
            board.getBoard().get(entry.getKey()).changeStatus(entry.getValue());
        }
        oldStatusesMap = statusesMap;
        return true;
    }

    public Map<Integer, Tile> getBoardsMap() {
        return board.getBoard();
    }

    public int getWidth() {
        return width;
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
