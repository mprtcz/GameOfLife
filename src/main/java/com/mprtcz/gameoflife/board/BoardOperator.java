package com.mprtcz.gameoflife.board;

import com.mprtcz.gameoflife.game.Tile;
import com.mprtcz.gameoflife.logger.AppLogger;
import com.mprtcz.gameoflife.styles.Status;
import javafx.application.Platform;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.mprtcz.gameoflife.calc.Adjacency.getIndexFromXY;
import static com.mprtcz.gameoflife.logger.AppLogger.DEFAULT_LEVEL;

/**
 * @author Michal_Partacz
 */
public class BoardOperator {

    private static final double FIELD_SIZE = 30;
    private int width;
    private Board board;
    private Map<Integer, Status> oldStatusesMap = new HashMap<>();

    public BoardOperator(int width, Board board) {
        this.width = width;
        this.board = board;
    }

    public boolean applyNewStatuses(Map<Integer, Status> statusesMap) {
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
        AppLogger.logger.log(DEFAULT_LEVEL, "Attempting to fill the board with buttons");
        fillTheBoard(board.getGridPane());
    }

    private void fillTheBoard(GridPane gridPane) {
        for (int column = 0; column < width; column++) {
            AppLogger.logger.log(DEFAULT_LEVEL, "Adding row constraints for column " +column);
            Platform.runLater(() -> gridPane.getRowConstraints().add(new RowConstraints(FIELD_SIZE)));
            for (int row = 0; row < width; row++) {
                if (column == 0 && row == 0) {
                    AppLogger.logger.log(DEFAULT_LEVEL, "Adding column constraints for row " +row);
                    Platform.runLater(() -> gridPane.getColumnConstraints().add(new ColumnConstraints(FIELD_SIZE)));
                } else if (column == 0) {
                    AppLogger.logger.log(DEFAULT_LEVEL, "Adding column constraints for row " +row);
                    Platform.runLater(() -> gridPane.getColumnConstraints().add(new ColumnConstraints(FIELD_SIZE)));
                } else {
                    createNewBoardTileAndAddIt(column, row);
                }
            }
        }
        AppLogger.logger.log(DEFAULT_LEVEL, "Creation completed");
    }

    private void createNewBoardTileAndAddIt(int column, int row) {
        AppLogger.logger.log(DEFAULT_LEVEL, String.format("Creating a tile for column %d and row %d", column, row));
        Tile tile = new Tile();
        board.getBoard().put(getIndexFromXY(row, column, width), tile);
        AppLogger.logger.log(DEFAULT_LEVEL, "Adding to board...");
        Platform.runLater(() -> board.getGridPane().add(tile, column, row));
    }

    public void randomizeTiles() {
        Random r = new Random();
        board.getBoard().entrySet().stream()
                .filter(integerTileEntry -> r.nextBoolean() && r.nextBoolean())
                .forEach(integerTileEntry -> integerTileEntry.getValue().changeStatus(Status.ALIVE));
    }

    public void clearBoard() {
        board.getBoard().entrySet().forEach(integerTileEntry ->
                integerTileEntry.getValue().changeStatus(Status.DEFAULT));
    }
}
