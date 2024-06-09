package org.chess.chess.game;

import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;

public record BoardSnapshot(BoardModel board, Location kingLocation) {
}
