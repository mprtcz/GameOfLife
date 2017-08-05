package com.mprtcz.gameoflife.game;

import com.mprtcz.gameoflife.board.Board;
import com.mprtcz.gameoflife.board.BoardOperator;
import com.mprtcz.gameoflife.logger.AppLogger;
import com.mprtcz.gameoflife.styles.Status;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.GridPane;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static com.mprtcz.gameoflife.styles.Status.ALIVE;
import static org.testng.Assert.assertEquals;

/**
 * @author mprtcz
 */
@Test
public class GameTest {

    @BeforeSuite
    public void setupJavaFx() {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });
    }

    @BeforeClass
    public void setUpLogger() {
        AppLogger.initializeLogger();
    }

    public void compareComputingTwoSingleThreadedMaps() {
        Map<Integer, Tile> initialMap = getSingleCrawlerMap(10);
        Game game = getGameInstance(10, initialMap);
        runTheSingleThreadAssertionLoop(game, initialMap);
    }

    public void compareComputingTwoSingleThreadedMaps_randomBoard() {
        Map<Integer, Tile> initialMap = getRandomMap(10);
        Game game = getGameInstance(10, initialMap);
        runTheSingleThreadAssertionLoop(game, initialMap);
    }

    public void compareComputingSingleThreadStreamAndParallel() {
        Map<Integer, Tile> initialMap = getSingleCrawlerMap(10);
        Game game = getGameInstance(10, initialMap);
        runTheDifferentThreadsAssertionLoop(game, initialMap);
    }

    public void compareTwoBasicParallelStreams() {
        Map<Integer, Tile> initialMap = getSingleCrawlerMap(10);
        Game game = getGameInstance(10, initialMap);
        runTwoParallelsAssertionLoop(game, initialMap);
    }

    public void compareComputingSingleThreadStreamAndParallel_randomMap() {
        Map<Integer, Tile> initialMap = getRandomMap(10);
        Game game = getGameInstance(10, initialMap);
        runTheDifferentThreadsAssertionLoop(game, initialMap);
    }


    private void runTheSingleThreadAssertionLoop(Game game, Map<Integer, Tile> initialMap) {
        Map<Integer, Status> map1 = convertTilesToStatusesMap(initialMap);
        Map<Integer, Status> map2 = convertTilesToStatusesMap(initialMap);
        for (int i = 0; i < 100; i++) {
            map1 = game.computeSingleThread(convertStatusesMapToTileMap(map1));
            map2 = game.computeSingleThreadAsStream(convertStatusesMapToTileMap(map2));
            for (int j = 0; j < map1.entrySet().size(); j++) {
                assertEquals(map1.get(j), map2.get(j),
                        "Fucked up at index " + j + " with iteration number " + i);
            }
            assertEquals(map1, map2);
        }
    }

    private void runTwoParallelsAssertionLoop(Game game, Map<Integer, Tile> initialMap) {
        Map<Integer, Status> map1 = convertTilesToStatusesMap(initialMap);
        Map<Integer, Status> map2 = convertTilesToStatusesMap(initialMap);
        for (int i = 0; i < 100; i++) {
            map1 = game.computeMultithreaded(convertStatusesMapToTileMap(map1));
            map2 = game.computeMultithreaded(convertStatusesMapToTileMap(map2));
            System.out.println("map1 = " + map1.size());
            System.out.println("map2 = " + map2.size());
            for (int j = 0; j < map1.entrySet().size(); j++) {
                assertEquals(map1.get(j), map2.get(j),
                        "Fucked up at index " + j + " with iteration number " + i);
            }
        }
    }

    private void runTheDifferentThreadsAssertionLoop(Game game, Map<Integer, Tile> initialMap) {
        Map<Integer, Status> singleThreadedMap = convertTilesToStatusesMap(initialMap);
        Map<Integer, Status> parallelMap = convertTilesToStatusesMap(initialMap);
        for (int i = 0; i < 100; i++) {
            singleThreadedMap = game.computeSingleThread(convertStatusesMapToTileMap(singleThreadedMap));
            parallelMap = game.computeMultithreaded(convertStatusesMapToTileMap(parallelMap));
            for (int j = 0; j < singleThreadedMap.entrySet().size(); j++) {
                assertEquals(singleThreadedMap.get(j), parallelMap.get(j),
                        "Fucked up at index " + j + " with iteration number " + i);
            }
        }
    }


    private Map<Integer, Tile> getSingleCrawlerMap(int width) {
        Map<Integer, Tile> initialMap = new HashMap<>();
        for (int i = 0; i < width * width; i++) {
            initialMap.put(i, new Tile());
        }
        initialMap.put(2, getAliveTile());
        initialMap.put(13, getAliveTile());
        initialMap.put(23, getAliveTile());
        initialMap.put(22, getAliveTile());
        initialMap.put(21, getAliveTile());
        return initialMap;
    }

    private Map<Integer, Tile> getRandomMap(int width) {
        Map<Integer, Tile> initialMap = new HashMap<>();
        Random r = new Random();
        for (int i = 0; i < width * width; i++) {
            if (r.nextBoolean() && r.nextBoolean()) {
                initialMap.put(i, getAliveTile());
            } else {
                initialMap.put(i, new Tile());
            }
        }
        return initialMap;
    }

    private Game getGameInstance(int boardWidth, Map<Integer, Tile> map) {
        Board board = new Board(new GridPane());
        board.setBoard(map);
        BoardOperator boardOperator = new BoardOperator(boardWidth, board);
        return new Game(boardOperator);
    }

    private Tile getAliveTile() {
        Tile t = new Tile();
        t.changeStatus(ALIVE);
        return t;
    }

    private Tile getTileOf(Status status) {
        Tile t = new Tile();
        t.changeStatus(status);
        return t;
    }


    private Map<Integer, Tile> convertStatusesMapToTileMap(Map<Integer, Status> statusesMap) {
        Map<Integer, Tile> tilesMap = new HashMap<>();
        statusesMap.forEach((integer, status) -> tilesMap.put(integer, getTileOf(status)));
        return tilesMap;
    }

    private Map<Integer, Status> convertTilesToStatusesMap(Map<Integer, Tile> tilesMap) {
        Map<Integer, Status> statusesMap = new HashMap<>();
        tilesMap.forEach((integer, tile) -> statusesMap.put(integer, tile.getTileStatus()));
        return statusesMap;
    }

}