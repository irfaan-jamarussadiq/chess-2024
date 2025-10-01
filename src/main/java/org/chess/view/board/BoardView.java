package org.chess.view.board;

import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.piece.Piece;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BoardView extends GridPane {
    private static final Color LIGHT_SQUARE = Color.rgb(157, 172, 255);
    private static final Color DARK_SQUARE = Color.rgb(111, 115, 210);
    private static final Color HIGHLIGHT_COLOR = Color.rgb(134, 239, 172);

    public static final int SIZE = 8;

    private final TileView[] tiles;

    public BoardView(BoardModel boardModel) {
        tiles = new TileView[SIZE * SIZE];

        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                Location location = new Location(rank, file);
                Piece piece = boardModel.pieceAt(location);
                Color color = ((rank + file) % 2 == 0) ? LIGHT_SQUARE : DARK_SQUARE;
                int tileCoordinate = SIZE * (rank - 1) + file - 1;
                tiles[tileCoordinate] = new TileView(color, piece);
                this.add(tiles[tileCoordinate], file, SIZE - rank);
            }
        }
    }

    public void highlightSquare(Location location) {
        int tileCoordinate = SIZE * (location.rank() - 1) + location.file() - 1;
        tiles[tileCoordinate].setFill(HIGHLIGHT_COLOR);
    }

    public void resetSquare(Location location) {
        int tileCoordinate = SIZE * (location.rank() - 1) + location.file() - 1;
        Color defaultColor = (location.rank() + location.file()) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;
        tiles[tileCoordinate].setFill(defaultColor);
    }

    public void resetAllSquares() {
        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                resetSquare(new Location(rank, file));
            }
        }
    }
}
