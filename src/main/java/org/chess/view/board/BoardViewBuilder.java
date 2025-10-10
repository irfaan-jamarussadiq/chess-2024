package org.chess.view.board;

import java.util.Collection;

import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.board.TileModel;
import org.chess.model.piece.Piece;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Builder;

public class BoardViewBuilder implements Builder<Region> {
    public static final int SIDE_LENGTH = 8;
    private static final Color LIGHT_SQUARE = Color.rgb(157, 172, 255);
    private static final Color DARK_SQUARE = Color.rgb(111, 115, 210);
    private static final Color HIGHLIGHT_COLOR = Color.rgb(135, 239, 172);

    private final BoardModel boardModel;

    public BoardViewBuilder(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    @Override
    public Region build() {
        GridPane boardView = new GridPane();
        for (int rank = 1; rank <= SIDE_LENGTH; rank++) {
            for (int file = 1; file <= SIDE_LENGTH; file++) {
                Location location = new Location(rank, file);
                Color color = ((rank + file) % 2 == 0) ? LIGHT_SQUARE : DARK_SQUARE;
                Region tile = buildTile(location, color);
                boardView.add(tile, file, BoardModel.SIZE - rank);
            }
        }

        boardView.addEventHandler(HighlightEvent.HIGHLIGHT_REQUEST, event -> {
            Collection<Location> locations = event.getLocations();
            for (Location location : locations) {
                Region highlightedTile = buildTile(location, HIGHLIGHT_COLOR);
                boardView.getChildren().set(location.getCoordinate() - 1, highlightedTile);
            }
        });

        return boardView;
    }

    private Region buildTile(Location location, Color color) {
        Piece piece = boardModel.pieceAt(location);
        TileModel tileModel = piece == null ? new TileModel() : new TileModel(piece);
        TileViewBuilder tileViewBuilder = new TileViewBuilder(color, tileModel);
        return tileViewBuilder.build();
    }

}
