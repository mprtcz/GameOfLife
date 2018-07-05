package com.mprtcz.gameoflife.game;

import com.mprtcz.gameoflife.board.BoardOperator;
import com.mprtcz.gameoflife.calc.Adjacency;
import com.mprtcz.gameoflife.logger.AppLogger;
import com.mprtcz.gameoflife.styles.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.mprtcz.gameoflife.styles.Status.*;

/**
 * @author Michal_Partacz
 */
public class Game {
    private static final boolean MULTITHREADED = false;
    private int delay = 100;
    private boolean isRunning;

    private BoardOperator boardOperator;

    public Game(BoardOperator boardOperator) {
        this.boardOperator = boardOperator;
    }

    public void runTheGame() throws InterruptedException {
        isRunning = true;
        while (isRunning) {
            Thread.sleep(delay);
            Map<Integer, Status> newStatusesMap;
            Map<Integer, Tile> currentBoard = boardOperator.getBoardsMap();
            if (MULTITHREADED) {
                newStatusesMap = computeMultithreaded(currentBoard);
            } else {
                newStatusesMap = computeSingleThreadAsStream(currentBoard);
            }
            boardOperator.applyNewStatuses(newStatusesMap);
        }
    }

    Map<Integer, Status> computeSingleThread(Map<Integer, Tile> currentBoard) {
        Map<Integer, Status> newStatusesMap = new HashMap<>();
        for (Map.Entry<Integer, Tile> entry : currentBoard.entrySet()) {
            newStatusesMap.put(entry.getKey(), calculateNewStatusOf(currentBoard, entry.getKey()));
        }
        return newStatusesMap;
    }

    Map<Integer, Status> computeSingleThreadAsStream(Map<Integer, Tile> currentBoard) {
        Map<Integer, Status> newStatusesMap = new HashMap<>();
        currentBoard.forEach((key, value) -> newStatusesMap.put(key,
                calculateNewStatusOf(currentBoard, key)));
        return newStatusesMap;
    }

    Map<Integer, Status> computeMultithreaded(Map<Integer, Tile> currentBoard) {
        AppLogger.logDefault("Size of the input map = " + currentBoard.size());
        Map<Integer, Status> newStatusesMap = new ConcurrentHashMap<>();
        currentBoard.entrySet().parallelStream()
                .forEach(integerTileEntry -> newStatusesMap.put(integerTileEntry.getKey(),
                        calculateNewStatusOf(currentBoard, integerTileEntry.getKey())));
        AppLogger.logDefault("Size of the computed map = " + newStatusesMap.size());
        return newStatusesMap;
    }

    public void terminate() {
        isRunning = false;
    }

    public void setDelay(double delay) {
        this.delay = (int) delay;
    }

    private Status calculateNewStatusOf(Map<Integer, Tile> currentMap, int tileIndex) {
        List<Integer> adjacentIndexes = Adjacency.getAllAdjacentIndexesOf(tileIndex, boardOperator.getWidth());
        long howManyAdjacentAlive = currentMap.entrySet()
                .stream().filter(integerTileEntry -> adjacentIndexes.contains(integerTileEntry.getKey()))
                .filter(integerTileEntry -> integerTileEntry.getValue().getTileStatus() == ALIVE).count();
        Tile currentTile = currentMap.get(tileIndex);
        if ((currentTile.getTileStatus() == DEAD || currentTile.getTileStatus() == VISITED)
                && howManyAdjacentAlive == 3) {
            return ALIVE;
        }
        if (currentTile.getTileStatus() == ALIVE) {
            if (howManyAdjacentAlive < 2) {
                return DEAD;
            }
            if (howManyAdjacentAlive > 3) {
                return DEAD;
            }
            return ALIVE;
        }
        return DEAD;
    }
}
