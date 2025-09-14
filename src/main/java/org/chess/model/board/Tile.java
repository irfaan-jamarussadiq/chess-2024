package org.chess.model.board;

import org.chess.model.piece.Piece;

public class Tile {
    private Piece piece;

    public Tile() {

    }

    public Tile(Piece piece) {
        this.piece = piece;
    }

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
