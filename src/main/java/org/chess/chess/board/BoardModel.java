package org.chess.chess.board;

public class BoardModel {
    public static final int SIZE = 8;
    private final Tile[] tiles;

    public BoardModel() {
        tiles = new Tile[SIZE * SIZE];
        for (int i = 0; i < SIZE * SIZE; i++) {
            tiles[i] = new Tile();
        }

        for (int file = 1; file <= 8; file++) {
            addPiece(new Pawn(PieceColor.WHITE), new Location(2, file));
            addPiece(new Pawn(PieceColor.BLACK), new Location(7, file));
        }

        addPiece(new Rook(PieceColor.WHITE), new Location(1, 1));
        addPiece(new Knight(PieceColor.WHITE), new Location(1, 2));
        addPiece(new Bishop(PieceColor.WHITE), new Location(1, 3));
        addPiece(new Queen(PieceColor.WHITE), new Location(1, 4));
        addPiece(new King(PieceColor.WHITE), new Location(1, 5));
        addPiece(new Bishop(PieceColor.WHITE), new Location(1, 6));
        addPiece(new Knight(PieceColor.WHITE), new Location(1, 7));
        addPiece(new Rook(PieceColor.WHITE), new Location(1, 8));

        addPiece(new Rook(PieceColor.BLACK), new Location(SIZE, 1));
        addPiece(new Knight(PieceColor.BLACK), new Location(SIZE, 2));
        addPiece(new Bishop(PieceColor.BLACK), new Location(SIZE, 3));
        addPiece(new Queen(PieceColor.BLACK), new Location(SIZE, 4));
        addPiece(new King(PieceColor.BLACK), new Location(SIZE, 5));
        addPiece(new Bishop(PieceColor.BLACK), new Location(SIZE, 6));
        addPiece(new Knight(PieceColor.BLACK), new Location(SIZE, 7));
        addPiece(new Rook(PieceColor.BLACK), new Location(SIZE, 8));
    }

    public void movePiece(Location start, Location end) {
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

    public Piece pieceAt(Location location) {
        int tileCoordinate = SIZE * (location.rank() - 1) + (location.file() - 1);
        return tiles[tileCoordinate].getPiece();
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
