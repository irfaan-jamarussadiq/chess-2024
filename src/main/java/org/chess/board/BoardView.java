package org.chess.board;

import org.chess.board.piece.*;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BoardView extends GridPane {
    private static final Color LIGHT_SQUARE = Color.rgb(157, 172, 255);
    private static final Color DARK_SQUARE = Color.rgb(111, 115, 210);
    private static final Color HIGHLIGHT_COLOR = Color.rgb(134, 239, 172);

    public static final int SIZE = 8;

    private final TileView[] tiles;

    public BoardView() {
        tiles = new TileView[SIZE * SIZE];

        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                Color color = ((rank + file) % 2 == 0) ? LIGHT_SQUARE : DARK_SQUARE;
                int tileCoordinate = SIZE * (rank - 1) + file - 1;
                tiles[tileCoordinate] = new TileView(color);
                this.add(tiles[tileCoordinate], file, SIZE - rank);
            }
        }

        for (int file = 1; file <= 8; file++) {
            addPiece(new Pawn(Alliance.WHITE), new Location(2, file));
            addPiece(new Pawn(Alliance.BLACK), new Location(7, file));
        }

        addPiece(new Rook(Alliance.WHITE), new Location(1, 1));
        addPiece(new Knight(Alliance.WHITE), new Location(1, 2));
        addPiece(new Bishop(Alliance.WHITE), new Location(1, 3));
        addPiece(new Queen(Alliance.WHITE), new Location(1, 4));
        addPiece(new King(Alliance.WHITE), new Location(1, 5));
        addPiece(new Bishop(Alliance.WHITE), new Location(1, 6));
        addPiece(new Knight(Alliance.WHITE), new Location(1, 7));
        addPiece(new Rook(Alliance.WHITE), new Location(1, 8));

        addPiece(new Rook(Alliance.BLACK), new Location(SIZE, 1));
        addPiece(new Knight(Alliance.BLACK), new Location(SIZE, 2));
        addPiece(new Bishop(Alliance.BLACK), new Location(SIZE, 3));
        addPiece(new Queen(Alliance.BLACK), new Location(SIZE, 4));
        addPiece(new King(Alliance.BLACK), new Location(SIZE, 5));
        addPiece(new Bishop(Alliance.BLACK), new Location(SIZE, 6));
        addPiece(new Knight(Alliance.BLACK), new Location(SIZE, 7));
        addPiece(new Rook(Alliance.BLACK), new Location(SIZE, 8));
    }

    private void addPiece(Piece piece, Location location) {
        int tileCoordinate = SIZE * (location.rank() - 1) + location.file() - 1;
        tiles[tileCoordinate].setPiece(piece);
    }

    private void removePiece(Location location) {
        int tileCoordinate = SIZE * (location.rank() - 1) + location.file() - 1;
        tiles[tileCoordinate].setPiece(null);
    }

    public Piece pieceAt(Location location) {
        int tileCoordinate = SIZE * (location.rank() - 1) + location.file() - 1;
        return tiles[tileCoordinate].getPiece();
    }

    public void move(Location start, Location end) {
        if (!start.isWithinBounds() || !end.isWithinBounds()) {
            return;
        }

        Piece piece = pieceAt(start);

        if (piece == null) {
            throw new IllegalArgumentException("No piece at starting square.");
        }

        removePiece(start);
        addPiece(piece, end);
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
}
