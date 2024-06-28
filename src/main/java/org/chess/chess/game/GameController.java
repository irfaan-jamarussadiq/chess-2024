package org.chess.chess.game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.chess.chess.board.BoardView;
import org.chess.chess.board.Location;
import org.chess.chess.board.TileView;
import org.chess.chess.board.piece.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GameController implements EventHandler<MouseEvent> {
    private final GameModel gameModel;
    private final GameView gameView;
    private Location selectedPieceLocation;
    private List<Move> movesHighlighted;
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    public GameController() {
        gameModel = new GameModel();
        gameView = new GameView();
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
        if (piece == null || !piece.canMoveFrom(start, end, gameModel.getBoard())) {
            return;
        }

        gameModel.move(start, end);
        gameView.move(start, end);
    }

    public GameView getGameView() {
        return gameView;
    }
}