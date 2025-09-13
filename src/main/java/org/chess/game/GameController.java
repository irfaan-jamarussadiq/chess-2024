package org.chess.game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import org.chess.board.BoardView;
import org.chess.board.Location;
import org.chess.board.TileView;
import org.chess.game.move.Move;
import org.chess.piece.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GameController implements EventHandler<MouseEvent> {
    private final GameModel gameModel;
    private final GameView gameView;
    private Location selectedPieceLocation;
    private List<Move> movesHighlighted;
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
            logger.info("Highlighting possible moves at " + location);
            selectedPieceLocation = location;
            movesHighlighted = gameModel.getLegalMoves(location);
            logger.debug(movesHighlighted.toString());
            gameView.highlightSquares(movesHighlighted);
        } else {
            logger.info("Moving piece from " + selectedPieceLocation + " to " + location);
            gameView.resetSquares(movesHighlighted);
            movesHighlighted.clear();
            move(selectedPieceLocation, location);
            selectedPieceLocation = null;
        }
    }

    public void move(Location start, Location end) {
        Piece piece = gameModel.getBoard().pieceAt(start);
        if (piece == null) {
            logger.debug("Piece at starting location " + start + " was null!!");
            return;
        }

        if (!piece.canMoveFrom(start, end, gameModel.getBoard())) {
            logger.debug("Piece cannot move from starting location " + start + " to ending location " + end + "!!");
            return;
        }

        Move move = gameModel.findMoveFromPath(start, end, gameModel.getBoard());
        gameModel.move(move);
        gameView.move(move, move.getLocationMappings(gameModel.getBoard()));
    }
}