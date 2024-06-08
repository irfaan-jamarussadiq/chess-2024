package org.chess.chess.board.piece;

import org.chess.chess.board.Alliance;

public abstract class Piece {
    private final Alliance alliance;

    public Piece(Alliance alliance) {
        this.alliance = alliance;
    }

    public Alliance getPieceColor() {
        return alliance;
    }

    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Piece otherPiece = (Piece) other;
        return otherPiece.alliance == this.alliance;
    }
}
