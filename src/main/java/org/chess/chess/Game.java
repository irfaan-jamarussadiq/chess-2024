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

    private boolean isValidMove(Move move, BoardModel board) {
        if (!move.isWithinBounds()) {
            return false;
        }

        throw new UnsupportedOperationException("Not implemented yet!");
    }

    private void executeMove(Move move, BoardModel board) {
        if (isCastlingMove(move, board)) {
            castle(move.start(), move.end());
        } else if (isEnPassantMove(move, board)) {
            enPassant(move.start(), move.end());
        } else {
            board.movePiece(move.start(), move.end());
        }
    }

    private void castle(Location kingStart, Location kingEnd) {
        board.movePiece(kingStart, kingEnd);

        Location rookStart, rookEnd;
        if (kingStart.file() > kingEnd.file()) {
            // Long castling
            rookStart = new Location(kingStart.rank(), 1);
            rookEnd = new Location(kingStart.rank(), 4);
        } else {
            // Short castling
            rookStart = new Location(kingStart.rank(), 8);
            rookEnd = new Location(kingStart.rank(), 6);
        }

        board.movePiece(rookStart, rookEnd);
    }

    private boolean isCastlingMove(Move move, BoardModel board) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    private void enPassant(Location pawnStart, Location pawnEnd) {
        Location capturedPawnLocation = new Location(pawnStart.rank(), pawnEnd.file());
        board.movePiece(capturedPawnLocation, pawnEnd);
        board.movePiece(pawnStart, pawnEnd);
    }

    private boolean isEnPassantMove(Move move, BoardModel board) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public Player getNextPlayer() {
        return (currentPlayer == WHITE) ? BLACK : WHITE;
    }
}
