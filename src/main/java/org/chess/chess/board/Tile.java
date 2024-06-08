package org.chess.chess.board;

import org.chess.chess.board.piece.Piece;

public class Tile {
    private Piece piece;

    Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }
}
