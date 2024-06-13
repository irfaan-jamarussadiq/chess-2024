package org.chess.chess.game;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.chess.chess.board.BoardView;
import org.chess.chess.board.Location;
import org.chess.chess.board.TileView;
import org.chess.chess.board.piece.Piece;

import java.util.List;

public class GameController implements EventHandler<MouseEvent> {
    private GameModel gameModel;
    private GameView gameView;
    private Piece selectedPiece;

    public GameController() {
        gameModel = new GameModel();
        gameView = new GameView();
        gameView.getBoardView().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        int rank = BoardView.SIZE - (int) mouseEvent.getY() / TileView.SIDE_LENGTH;
        int file = (int) mouseEvent.getX() / TileView.SIDE_LENGTH + 1;

        Location location = new Location(rank, file);
        this.selectedPiece = gameModel.getBoard().pieceAt(location);
        if (selectedPiece != null) {
            highlightSquares(location);
        }
    }

    private void highlightSquares(Location location) {
        List<Move> moves = gameModel.getLegalMoves(location);
        for (Move move : moves) {
            if (move.isWithinBounds()) {
                gameView.getBoardView().highlightSquare(move.end());
            }
        }
    }

    public GameView getGameView() {
        return gameView;
    }
}
