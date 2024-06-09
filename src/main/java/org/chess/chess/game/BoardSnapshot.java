package org.chess.chess.game;

import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;

public record BoardSnapshot(BoardModel board, Location kingLocation) {
    public BoardSnapshot(BoardModel board, Location kingLocation) {
        this.board = new BoardModel(board);
        this.kingLocation = kingLocation;
    }
}
