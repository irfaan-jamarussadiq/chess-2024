package org.chess.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import org.chess.model.board.Location;
import org.chess.model.game.GameModel;
import org.chess.model.game.Move;
import org.chess.model.piece.Piece;
import org.chess.view.board.BoardView;
import org.chess.view.board.TileView;
import org.chess.view.game.GameView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

public class GameController implements EventHandler<MouseEvent> {
    private final GameModel gameModel;
    private final GameView gameView;
    private Location selectedPieceLocation;
    private Collection<Move> movesHighlighted;
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    public GameController(GameModel model, GameView view) {
        gameModel = model;
        gameView = view;
        movesHighlighted = new ArrayList<>();
        gameView.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        int rank = BoardView.SIZE - (int) mouseEvent.getY() / TileView.SIDE_LENGTH;
        int file = (int) mouseEvent.getX() / TileView.SIDE_LENGTH + 1;
        Location location = new Location(rank, file);

        if (movesHighlighted.isEmpty()) {
            logger.info(String.format("Highlighting possible moves at %s", location));
            selectedPieceLocation = location;
            movesHighlighted = gameModel.getLegalMoves(location);
            logger.debug(movesHighlighted.toString());
            gameView.highlightSquares(movesHighlighted);
        } else {
            logger.info(String.format("Moving piece from %s to %s", selectedPieceLocation, location));
            gameView.resetSquares(movesHighlighted);
            movesHighlighted.clear();
            move(selectedPieceLocation, location);
            selectedPieceLocation = null;
        }
    }

    public void move(Location start, Location end) {
        if (gameModel.getBoard().isEmpty(start)) {
            logger.debug(String.format("No piece at starting location %s!", start));
            return;
        }

        Piece piece = gameModel.getBoard().pieceAt(start);
        if (!piece.canMoveFrom(start, end)) {
            logger.debug(String.format("Piece cannot move from %s to %s!!", start, end));
            return;
        }

        Move move = new Move(start, end);
        gameModel.move(move);
        gameView.move(move);
    }
}