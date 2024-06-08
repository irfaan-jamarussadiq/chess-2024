package org.chess.chess.board.piece;

public abstract class Piece {
    private final PieceColor pieceColor;

    public Piece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Piece otherPiece = (Piece) other;
        return otherPiece.pieceColor == this.pieceColor;
    }
}
