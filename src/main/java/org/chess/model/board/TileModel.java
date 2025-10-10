package org.chess.model.board;

import org.chess.model.piece.Piece;

import javafx.beans.property.SimpleObjectProperty;

public class TileModel {
    private final SimpleObjectProperty<Piece> pieceProperty;

    public TileModel() {
        this.pieceProperty = new SimpleObjectProperty<>();
    }

    public TileModel(Piece piece) {
        this.pieceProperty = new SimpleObjectProperty<>(piece);
    }

    public SimpleObjectProperty<Piece> getPieceProperty() {
        return pieceProperty;
    }

    public void setPiece(Piece piece) {
        this.pieceProperty.set(piece);
    }

    public void removePiece() {
        this.pieceProperty.set(null);
    }
}
