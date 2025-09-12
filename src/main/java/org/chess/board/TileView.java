package org.chess.board;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.chess.ChessApplication;
import org.chess.board.piece.Piece;

public class TileView extends StackPane {
    public static final int SIDE_LENGTH = 80;
    private final ImageView pieceView;
    private final Rectangle tile;
    private Piece piece;

    public TileView(Color color) {
        tile = new Rectangle();
        tile.setHeight(SIDE_LENGTH);
        tile.setWidth(SIDE_LENGTH);
        tile.setFill(color);

        pieceView = new ImageView();
        this.getChildren().addAll(tile, pieceView);
    }

    public void setPiece(Piece piece) {
        if (piece == null) {
            this.piece = null;
            pieceView.setImage(null);
            return;
        }

        this.piece = piece;
        String type = piece.getClass().getSimpleName().toLowerCase();
        String color = piece.getAlliance() == Alliance.WHITE ? "white" : "black";
        String piecePath = String.format("/images/%s_%s.png", color, type);
        Image pieceImage = new Image(String.valueOf(ChessApplication.class.getResource(piecePath)));
        pieceView.setImage(pieceImage);
        pieceView.setFitWidth(tile.getWidth());
        pieceView.setFitHeight(tile.getHeight());
    }

    public Piece getPiece() {
        return piece;
    }

    public void setFill(Color color) {
        tile.setFill(color);
    }
}
