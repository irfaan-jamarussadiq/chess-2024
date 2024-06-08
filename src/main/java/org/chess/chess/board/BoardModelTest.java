package org.chess.chess.board;

import org.chess.chess.board.piece.Pawn;
import org.chess.chess.board.piece.Rook;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
}
