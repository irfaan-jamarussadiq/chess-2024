package org.chess.board;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.chess.board.piece.Pawn;
import org.chess.board.piece.Rook;

public class BoardModelTest {
    @Test
    public void testMovePiece() {
        BoardModel board = new BoardModel();
        Location start = new Location(2, 4);
        Location end = new Location(4, 4);
        board.movePiece(start, end);
        assertEquals(board.pieceAt(end), new Pawn(Alliance.WHITE));
    }

    @Test
    public void testPieceAtWithinBounds() {
        BoardModel board = new BoardModel();
        Location location = new Location(1, 1);
        assertEquals(board.pieceAt(location), new Rook(Alliance.WHITE));
    }

    @Test
    public void testPieceAtOutOfBounds() {
        BoardModel board = new BoardModel();
        Location location = new Location(1, -1);
        assertThrows(IllegalArgumentException.class, () -> board.pieceAt(location));
    }

    @Test
    public void testMovePieceAtEmptySquareFails() {
        BoardModel board = new BoardModel();
        Location start = new Location(4, 4);
        Location end = new Location(5, 4);
        assertThrows(IllegalArgumentException.class, () -> board.movePiece(start, end));
    }
}
