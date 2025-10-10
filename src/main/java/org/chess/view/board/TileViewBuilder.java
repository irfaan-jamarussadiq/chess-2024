package org.chess.view.board;

import org.chess.ChessApplication;
import org.chess.model.board.Alliance;
import org.chess.model.board.TileModel;
import org.chess.model.piece.Piece;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Builder;

public class TileViewBuilder implements Builder<Region> {
    private static final int TILE_SIZE = 80;

    private final Color color;
    private final TileModel tileModel;

    TileViewBuilder(Color color, TileModel tileModel) {
        this.color = color;
        this.tileModel = tileModel;
    }

    @Override
    public Region build() {
        StackPane pane = new StackPane();
        Node square = createSquare(color, TILE_SIZE);
        pane.getChildren().add(square);
        if (tileModel.getPieceProperty().get() != null) {
            Node pieceView = createPiece(tileModel.getPieceProperty());
            pane.getChildren().add(pieceView);
        }
        return pane;
    }
   
    private Shape createSquare(Color color, int sideLength) {
        Rectangle square = new Rectangle(sideLength, sideLength);
        square.setHeight(TILE_SIZE);
        square.setWidth(TILE_SIZE);
        square.setFill(color);
        return square;
    }

    private Node createPiece(SimpleObjectProperty<Piece> pieceProperty) {
        ImageView pieceView = new ImageView();
        String type = pieceProperty.get().getClass().getSimpleName().toLowerCase();
        String color = pieceProperty.get().getAlliance() == Alliance.WHITE ? "white" : "black";
        String piecePath = String.format("/images/%s_%s.png", color, type);
        Image pieceImage = new Image(String.valueOf(ChessApplication.class.getResource(piecePath)));
        pieceView.setImage(pieceImage);
        pieceView.setFitWidth(TILE_SIZE);
        pieceView.setFitHeight(TILE_SIZE);
        return pieceView;
    }
}
