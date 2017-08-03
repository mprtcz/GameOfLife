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

    private int size;
    private BoardOperator boardOperator;

    @FXML
    void initialize() {
        size = (int) sizeSlider.getValue();
        Board board = new Board(mainGridPane);
        boardOperator = new BoardOperator(size, board);
        new Thread(boardOperator::initializeBoard).start();
    }

    @FXML
    void onStartButtonCLicked() {
        //run the game
        sizeSlider.setDisable(true);
        Game game = new Game(boardOperator);
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
        //change the speed of execution
    }

    private void clearTheGridPane() {
        this.mainGridPane.getChildren().clear();
        this.mainGridPane.getRowConstraints().clear();
        this.mainGridPane.getColumnConstraints().clear();
    }
}
