package org.chess.controller;

import org.chess.interactor.BoardInteractor;
import org.chess.model.board.BoardModel;
import org.chess.model.game.GameModel;
import org.chess.view.board.BoardView;
import org.chess.view.game.GameView;

import javafx.scene.layout.Region;

public class GameController {
    private final GameModel gameModel;
    private final BoardInteractor gameInteractor;
    private final GameView gameView;

    public GameController() {
        BoardModel boardModel = new BoardModel();
        this.gameModel = new GameModel(boardModel);
        this.gameInteractor = new BoardInteractor(gameModel);
        BoardView boardView = new BoardView(boardModel);
        this.gameView = new GameView(gameModel, boardView);
        gameInteractor.addBoardClickEventHandler(boardView);
    }
   
    public Region getView() {
        return gameView;
    }
}