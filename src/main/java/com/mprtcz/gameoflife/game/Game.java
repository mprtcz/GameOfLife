package com.mprtcz.gameoflife.game;

import com.mprtcz.gameoflife.board.BoardOperator;
import com.mprtcz.gameoflife.calc.Adjacency;
import com.mprtcz.gameoflife.styles.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mprtcz.gameoflife.styles.Status.ALIVE;
import static com.mprtcz.gameoflife.styles.Status.DEAD;
import static com.mprtcz.gameoflife.styles.Status.VISITED;

/**
 * @author Michal_Partacz
 */
public class Game {
    private int delay = 100;
    private boolean isRunning;

    private BoardOperator boardOperator;

    public Game(BoardOperator boardOperator) {
        this.boardOperator = boardOperator;
    }

    public void runTheGame() throws InterruptedException {
        System.out.println("Game.runTheGame");
        isRunning = true;
        while(isRunning) {
            Thread.sleep(delay);
            Map<Integer, Status> newStatusesMap = new HashMap<>();
            for (Map.Entry<Integer, Tile> entry : boardOperator.getBoardsMap().entrySet()) {
                newStatusesMap.put(entry.getKey(), calculateNewStatusOf(entry.getKey()));
            }
            isRunning = boardOperator.applyNewStatuses(newStatusesMap);
        }
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
        if((currentTile.getTileStatus() == DEAD || currentTile.getTileStatus() == VISITED)
                && howManyAdjacentAlive == 3) {
            return ALIVE;
        }
        if(currentTile.getTileStatus() == ALIVE) {
            if(howManyAdjacentAlive < 2) {
                return DEAD;
            }
            if(howManyAdjacentAlive > 3) {
                return DEAD;
            }
            if(howManyAdjacentAlive >= 2 && howManyAdjacentAlive <=3) {
                return ALIVE;
            }
        }
        return DEAD;
    }
}
