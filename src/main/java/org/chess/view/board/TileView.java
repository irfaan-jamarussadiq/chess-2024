package org.chess.view.board;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.chess.ChessApplication;
import org.chess.model.board.Alliance;
import org.chess.model.piece.Piece;

public class TileView extends StackPane {
    public static final int SIDE_LENGTH = 80;

    private final ImageView pieceView;
    private final Rectangle square;
    private Piece piece;

    public TileView(Color color) {
        square = new Rectangle();
        square.setHeight(SIDE_LENGTH);
        square.setWidth(SIDE_LENGTH);
        square.setFill(color);

        pieceView = new ImageView();
        this.getChildren().addAll(square, pieceView);
    }

    public TileView(Color color, Piece piece) {
        this(color);
        this.setPiece(piece);
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
        pieceView.setFitWidth(square.getWidth());
        pieceView.setFitHeight(square.getHeight());
    }

    public Piece getPiece() {
        return piece;
    }

    public void setFill(Color color) {
        square.setFill(color);
    }
}
