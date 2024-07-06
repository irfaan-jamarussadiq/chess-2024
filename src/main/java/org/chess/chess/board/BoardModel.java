package org.chess.chess.board;

import org.chess.chess.board.piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardModel {
    public static final int SIZE = 8;
    private final Tile[] tiles;

    public BoardModel() {
        tiles = new Tile[SIZE * SIZE];
        for (int i = 0; i < SIZE * SIZE; i++) {
            tiles[i] = new Tile();
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

    public BoardModel(BoardModel board) {
        tiles = new Tile[SIZE * SIZE];
        for (int i = 0; i < SIZE * SIZE; i++) {
            Piece piece = board.tiles[i].getPiece();
            tiles[i] = new Tile();
            tiles[i].setPiece(piece);
        }
    }

    public void movePiece(Location start, Location end) {
        if (!start.isWithinBounds() || !end.isWithinBounds()) {
            return;
        }

        Piece piece = pieceAt(start);

        if (piece == null) {
            throw new IllegalArgumentException("No piece at starting square.");
        }

        piece.setHasMoved(true);
        removePiece(start);
        addPiece(piece, end);
    }

    public Piece pieceAt(Location location) {
        if (!location.isWithinBounds()) {
            throw new IllegalArgumentException("Location is out of bounds");
        }

        int tileCoordinate = SIZE * (location.rank() - 1) + (location.file() - 1);
        return tiles[tileCoordinate].getPiece();
    }

    public boolean isEmpty(Location location) {
        return pieceAt(location) == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                Piece piece = pieceAt(new Location(9 - rank, file));
                if (piece == null) {
                    sb.append(". ");
                } else {
                    String p = piece.getClass().getSimpleName().charAt(0) + "";
                    String p2 = (piece.getAlliance() == Alliance.WHITE) ? p.toUpperCase() : p.toLowerCase();
                    sb.append(p2).append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private void addPiece(Piece piece, Location location) {
        int tileCoordinate = SIZE * (location.rank() - 1) + (location.file() - 1);
        tiles[tileCoordinate].setPiece(piece);
    }

    private void removePiece(Location location) {
        int tileCoordinate = SIZE * (location.rank() - 1) + (location.file() - 1);
        tiles[tileCoordinate].removePiece();
    }
}
