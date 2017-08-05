package com.mprtcz.gameoflife.game;

import com.mprtcz.gameoflife.board.BoardOperator;
import com.mprtcz.gameoflife.calc.Adjacency;
import com.mprtcz.gameoflife.styles.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mprtcz.gameoflife.styles.Status.*;

/**
 * @author Michal_Partacz
 */
public class Game {
    private static final boolean MULTITHREADED = true;
    private int delay = 100;
    private boolean isRunning;

    private BoardOperator boardOperator;

    public Game(BoardOperator boardOperator) {
        this.boardOperator = boardOperator;
    }

    public void runTheGame() throws InterruptedException {
        System.out.println("Game.runTheGame");
        isRunning = true;
        while (isRunning) {
                Thread.sleep(delay);
            Map<Integer, Status> newStatusesMap;
            if (MULTITHREADED) {
                newStatusesMap = computeMultithreaded();
            } else {
                newStatusesMap = computeSingleThread();
            }
            boardOperator.applyNewStatuses(newStatusesMap);
        }
    }

    private Map<Integer, Status> computeSingleThread() {
        Map<Integer, Status> newStatusesMap = new HashMap<>();
        for (Map.Entry<Integer, Tile> entry : boardOperator.getBoardsMap().entrySet()) {
            newStatusesMap.put(entry.getKey(), calculateNewStatusOf(entry.getKey()));
        }
        return newStatusesMap;
    }

    private Map<Integer, Status> computeMultithreaded() {
        Map<Integer, Status> newStatusesMap = new HashMap<>();
        boardOperator.getBoardsMap().entrySet().parallelStream()
                .forEach(integerTileEntry -> newStatusesMap.put(integerTileEntry.getKey(),
                        calculateNewStatusOf(integerTileEntry.getKey())));
        return newStatusesMap;
    }

    public void terminate() {
        isRunning = false;
    }

    public void setDelay(double delay) {
        this.delay = (int) delay;
    }

    private Status calculateNewStatusOf(int tileIndex) {
        List<Integer> adjacentIndexes = Adjacency.getAllAdjacentIndexesOf(tileIndex, boardOperator.getWidth());
        long howManyAdjacentAlive = boardOperator.getBoardsMap().entrySet()
                .stream().filter(integerTileEntry -> adjacentIndexes.contains(integerTileEntry.getKey()))
                .filter(integerTileEntry -> integerTileEntry.getValue().getTileStatus() == ALIVE).count();
        Tile currentTile = boardOperator.getBoardsMap().get(tileIndex);
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
            if (howManyAdjacentAlive >= 2 && howManyAdjacentAlive <= 3) {
                return ALIVE;
            }
        }
        return DEAD;
    }
}
