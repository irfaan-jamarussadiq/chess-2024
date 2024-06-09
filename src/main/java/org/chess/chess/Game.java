package org.chess.chess;

import org.chess.chess.board.Alliance;
import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.Piece;

public class Game {
    private static final Player WHITE = new Player(Alliance.WHITE);
    private static final Player BLACK = new Player(Alliance.BLACK);
    private Player currentPlayer;
    private final BoardModel board;

    public Game() {
        this.currentPlayer = WHITE;
        this.board = new BoardModel();
    }

    public void move(Location start, Location end) {
        Move move = new Move(start, end);
        if (isValidMove(move, board)) {
            executeMove(move, board);
            currentPlayer = getNextPlayer();
        }
    }

    private void executeMove(Move move, BoardModel board) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    private boolean isValidMove(Move move, BoardModel board) {
        if (!move.isWithinBounds()) {
            return false;
        }

        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public Player getNextPlayer() {
        return (currentPlayer == WHITE) ? BLACK : WHITE;
    }
}
