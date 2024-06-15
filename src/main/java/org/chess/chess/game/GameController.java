package org.chess.chess.game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.chess.chess.board.BoardView;
import org.chess.chess.board.Location;
import org.chess.chess.board.TileView;

import java.util.ArrayList;
import java.util.List;

public class GameController implements EventHandler<MouseEvent> {
    private final GameModel gameModel;
    private final GameView gameView;
    private Location selectedPieceLocation;
    private List<Move> movesHighlighted;

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
            selectedPieceLocation = location;
            movesHighlighted = gameModel.getLegalMoves(location);
            gameView.highlightSquares(movesHighlighted);
        } else {
            gameView.resetSquares(movesHighlighted);
            movesHighlighted.clear();
            move(selectedPieceLocation, location);
            selectedPieceLocation = null;
        }
    }

    public void move(Location start, Location end) {
        if (gameModel.getBoard().isEmpty(start)) {
            return;
        }

        gameModel.move(start, end);
        gameView.move(start, end);
    }

    public GameView getGameView() {
        return gameView;
    }
}