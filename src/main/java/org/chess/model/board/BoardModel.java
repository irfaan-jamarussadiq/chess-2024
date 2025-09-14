package org.chess.model.board;

import java.util.HashSet;
import java.util.Set;

import org.chess.model.piece.*;

public class BoardModel {
    public static final int SIZE = 8;
    private final Tile[] tiles;
    private Set<Piece> movedPieces;

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

        movedPieces = new HashSet<>();
    }

    public BoardModel(BoardModel board) {
        tiles = new Tile[SIZE * SIZE];
        for (int i = 0; i < SIZE * SIZE; i++) {
            Piece piece = board.tiles[i].getPiece();
            tiles[i] = new Tile(piece);
        }
    }

    public void movePiece(Location start, Location end) {
        if (start.isWithinBounds() && end.isWithinBounds() && !isEmpty(start)) {
            Piece piece = pieceAt(start);
            movedPieces.add(piece);
            removePiece(start);
            addPiece(piece, end);
        }
    }

    public Piece pieceAt(Location location) {
        if (!location.isWithinBounds()) {
            throw new IllegalArgumentException("Location is out of bounds");
        }

        return tiles[location.getCoordinate()].getPiece();
    }

    public boolean isEmpty(Location location) {
        return pieceAt(location) == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                Location location = new Location(9 - rank, file);
                char pieceLetter = isEmpty(location) ? '.' : pieceAt(location).getLetter();
                sb.append(pieceLetter + " ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    private void addPiece(Piece piece, Location location) {
        tiles[location.getCoordinate()].setPiece(piece);
    }

    private void removePiece(Location location) {
        tiles[location.getCoordinate()].removePiece();
    }

    public boolean hasPieceNotMoved(Piece piece) {
        return !movedPieces.contains(piece);
    }
}
