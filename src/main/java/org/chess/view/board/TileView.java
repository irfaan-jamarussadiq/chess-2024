package org.chess.view.board;

import org.chess.ChessApplication;
import org.chess.model.board.Alliance;
import org.chess.model.piece.Piece;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileView extends StackPane {
    public static final int TILE_SIZE = 80;
    private static final Color HIGHLIGHT_SQUARE = Color.rgb(135, 239, 172);

    private final Rectangle square;
    private final Node piece;
    private final Color baseColor;

    public TileView(SimpleObjectProperty<Piece> pieceProperty, Color color) {
        this.square = createSquare(color, TILE_SIZE);
        this.piece = createPiece(pieceProperty);
        this.baseColor = color;
        this.getChildren().addAll(square, piece);
    }

    public void highlight() {
        square.setFill(HIGHLIGHT_SQUARE);
    }

    public void reset() {
        square.setFill(baseColor);
    }

    private Rectangle createSquare(Color color, int sideLength) {
        Rectangle square = new Rectangle(sideLength, sideLength);
        square.setHeight(TILE_SIZE);
        square.setWidth(TILE_SIZE);
        square.setFill(color);
        return square;
    }

    private Node createPiece(SimpleObjectProperty<Piece> pieceProperty) {
        ImageView pieceView = new ImageView();
        if (pieceProperty.get() != null) {
            String type = pieceProperty.get().getClass().getSimpleName().toLowerCase();
            String color = pieceProperty.get().getAlliance() == Alliance.WHITE ? "white" : "black";
            String piecePath = String.format("/images/%s_%s.png", color, type);
            Image pieceImage = new Image(String.valueOf(ChessApplication.class.getResource(piecePath)));
            pieceView.setImage(pieceImage);
        }

        pieceView.setFitWidth(TILE_SIZE);
        pieceView.setFitHeight(TILE_SIZE);
        return pieceView;
    }
}
