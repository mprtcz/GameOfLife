package com.mprtcz.gameoflife;

import com.mprtcz.gameoflife.board.Board;
import com.mprtcz.gameoflife.board.BoardOperator;
import com.mprtcz.gameoflife.game.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

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

    @FXML
    void initialize() {
        size = (int) sizeSlider.getValue();
        Board board = new Board(mainGridPane);
        boardOperator = new BoardOperator(size, board);
        new Thread(boardOperator::initializeBoard).start();
    }

    @FXML
    void onStartButtonCLicked() {
        sizeSlider.setDisable(true);
        game = new Game(boardOperator);
        game.setDelay(speedSlider.getValue());
        new Thread(() -> {
            try {
                game.runTheGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void onSizeSliderChanged() {
        clearTheGridPane();
        size = (int) sizeSlider.getValue();
        Board board = new Board(mainGridPane);
        boardOperator = new BoardOperator(size, board);
        new Thread(boardOperator::initializeBoard).start();
    }

    @FXML
    void onSpeedSliderChanged() {
        game.setDelay(speedSlider.getValue());
    }

    private void clearTheGridPane() {
        this.mainGridPane.getChildren().clear();
        this.mainGridPane.getRowConstraints().clear();
        this.mainGridPane.getColumnConstraints().clear();
    }

    @FXML
    void onTerminateButtonClicked(){
        game.terminate();
        sizeSlider.setDisable(false);
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
