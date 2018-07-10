package com.mprtcz.gameoflife.board;

import com.mprtcz.gameoflife.game.Tile;
import com.mprtcz.gameoflife.logger.AppLogger;
import com.mprtcz.gameoflife.styles.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.mprtcz.gameoflife.calc.Adjacency.getIndexFromXY;

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

    public void applyNewStatuses(Map<Integer, Status> statusesMap) {
        if (statusesMap.equals(oldStatusesMap)) {
            return;
        }
        statusesMap.forEach((key, value) -> board.changeBoardStatus(key, value));
        oldStatusesMap = statusesMap;
    }

    public Map<Integer, Tile> getBoardsMap() {
        return board.getBoard();
    }

    public int getWidth() {
        return width;
    }

    public void initializeBoard() {
        AppLogger.logDefault("Attempting to fill the board with buttons");
        fillTheBoard();
    }

    private void fillTheBoard() {
        for (int column = 0; column < width; column++) {
            AppLogger.logDefault("Adding row constraints for column " + column);
            board.addRowConstraint(FIELD_SIZE);
            for (int row = 0; row < width; row++) {
                if (column == 0 && row == 0) {
                    AppLogger.logDefault("Adding column constraints for row " + row);
                    board.addColumnConstraint(FIELD_SIZE);
                } else if (column == 0) {
                    AppLogger.logDefault("Adding column constraints for row " + row);
                    board.addColumnConstraint(FIELD_SIZE);
                } else {
                    createNewBoardTileAndAddIt(column, row);
                }
            }
        }
        AppLogger.logDefault("Creation completed");
    }

    private void createNewBoardTileAndAddIt(int column, int row) {
        AppLogger.logDefault(String.format("Creating a tile for column %d and row %d", column, row));
        Tile tile = new Tile();
        board.getBoard().put(getIndexFromXY(row, column, width), tile);
        AppLogger.logDefault("Adding to board...");
        board.addTileToPosition(tile, column, row);
    }

    public void randomizeTiles() {
        Random r = new Random();
        board.getBoard().entrySet().stream()
                .filter(integerTileEntry -> r.nextBoolean() && r.nextBoolean())
                .forEach(integerTileEntry -> integerTileEntry.getValue().changeStatus(Status.ALIVE));
    }

    public void clearBoard() {
        board.getBoard().forEach((key, value) -> value.changeStatus(Status.DEFAULT));
    }
}
