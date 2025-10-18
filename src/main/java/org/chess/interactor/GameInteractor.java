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
        BoardClickHandler eventHandler = new BoardClickHandler(gameModel, boardView);
        boardView.setOnMouseClicked(eventHandler);
    }

    private static class BoardClickHandler implements EventHandler<MouseEvent> {
        private static Location previousLocation;
        private GameModel gameModel;
        private BoardView boardView;

        private BoardClickHandler(GameModel gameModel, BoardView boardView) {
            this.boardView = boardView;
            this.gameModel = gameModel;
        }

        @Override
        public void handle(MouseEvent event) {
            boardView.resetAllSquares();
            int rank = BoardModel.SIZE - (int) event.getY() / TileView.TILE_SIZE;
            int file = (int) event.getX() / TileView.TILE_SIZE + 1;
            Location location = new Location(rank, file);
            if (previousLocation == null) {
                Collection<Move> legalMoves = gameModel.getLegalMoves(location);
                Collection<Location> destinations = legalMoves.stream().map(m -> m.end()).toList();
                boardView.highlightSquares(destinations);
                previousLocation = location;
            } else {
                System.out.println("Entering second branch...");
                Move move = new Move(previousLocation, location);
                gameModel.move(move);
                previousLocation = null;
            }
        }
    }
}
