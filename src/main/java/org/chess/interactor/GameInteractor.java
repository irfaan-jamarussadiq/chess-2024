package org.chess.interactor;

import java.util.Collection;

import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.GameModel;
import org.chess.model.game.Move;
import org.chess.view.board.BoardView;
import org.chess.view.board.TileView;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GameInteractor {
    private GameModel gameModel;

    public GameInteractor(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void addBoardClickEventHandler(BoardView boardView) {
        boardView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int rank = BoardModel.SIZE - (int) event.getY() / TileView.TILE_SIZE;
                int file = (int) event.getX() / TileView.TILE_SIZE + 1;
                Location location = new Location(rank, file);
                Collection<Move> legalMoves = gameModel.getLegalMoves(location);
                Collection<Location> destinations = legalMoves.stream().map(m -> m.end()).toList();
                boardView.highlightSquares(destinations);
            }
        });
    }
}
