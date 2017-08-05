package com.mprtcz.gameoflife;

import com.mprtcz.gameoflife.board.Board;
import com.mprtcz.gameoflife.board.BoardOperator;
import com.mprtcz.gameoflife.game.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Michal_Partacz
 */
public class Controller {

    @FXML
    private GridPane mainGridPane;
    @FXML
    private Button startButton;
    @FXML
    private Slider sizeSlider;
    @FXML
    private Slider speedSlider;

    private int size;
    private BoardOperator boardOperator;
    private Game game;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @FXML
    void initialize() {
        size = (int) sizeSlider.getValue();
        Board board = new Board(mainGridPane);
        boardOperator = new BoardOperator(size, board);
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(boardOperator::initializeBoard);
    }

    @FXML
    void onStartButtonCLicked() {
        sizeSlider.setDisable(true);
        game = new Game(boardOperator);
        game.setDelay(speedSlider.getValue());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                game.runTheGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void onSizeSliderChanged() {
        clearTheGridPane();
        size = (int) sizeSlider.getValue();
        Board board = new Board(mainGridPane);
        boardOperator = new BoardOperator(size, board);
        executorService.submit(boardOperator::initializeBoard);
    }

    @FXML
    void onSpeedSliderChanged() {
        if (game != null)
            game.setDelay(speedSlider.getValue());
    }

    private void clearTheGridPane() {
        this.mainGridPane.getChildren().clear();
        this.mainGridPane.getRowConstraints().clear();
        this.mainGridPane.getColumnConstraints().clear();
    }

    @FXML
    void onTerminateButtonClicked() throws InterruptedException {
        game.terminate();
        sizeSlider.setDisable(false);
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdownNow();
    }

    @FXML
    void onRandomizeButtonClicked() {
        boardOperator.randomizeTiles();
    }

    @FXML
    void onClearButtonClicked() {
        boardOperator.clearBoard();
    }
}
