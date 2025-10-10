package org.chess.controller;

import org.chess.model.board.Location;
import org.chess.model.game.GameModel;
import org.chess.model.game.Move;
import org.chess.view.game.GameViewBuilder;

public class GameController {
    private final GameModel gameModel;
    private final GameViewBuilder gameView;

    public GameController(GameModel gameModel, GameViewBuilder gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        // gameView.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
    }

    public void move(Location start, Location end) {
        Move move = new Move(start, end);
        gameModel.move(move);
    }
}