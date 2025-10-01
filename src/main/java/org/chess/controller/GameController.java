package org.chess.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import org.chess.model.board.Location;
import org.chess.model.game.GameModel;
import org.chess.model.game.Move;
import org.chess.view.board.BoardView;
import org.chess.view.board.TileView;
import org.chess.view.game.GameView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class GameController implements EventHandler<MouseEvent> {
    private final GameModel gameModel;
    private final GameView gameView;

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    public GameController(GameModel model, GameView view) {
        gameModel = model;
        gameView = view;
        gameView.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        int rank = BoardView.SIZE - (int) mouseEvent.getY() / TileView.SIDE_LENGTH;
        int file = (int) mouseEvent.getX() / TileView.SIDE_LENGTH + 1;
        Location location = new Location(rank, file);
        logger.debug(String.format("Highlight moves for piece at %s", location));

        gameView.resetAllSquares();
        Collection<Move> moves = gameModel.getLegalMoves(location);
        gameView.highlightSquares(moves);
    }

    public void move(Location start, Location end) {
        Move move = new Move(start, end);
        gameModel.move(move);
    }
}