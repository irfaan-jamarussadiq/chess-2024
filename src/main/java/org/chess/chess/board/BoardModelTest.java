package org.chess.chess.board;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardModelTest {
    @Test
    public void testMovePiece() {
        BoardModel board = new BoardModel();
        Location start = new Location(2, 4);
        Location end = new Location(4, 4);
        board.movePiece(start, end);
        assertEquals(board.pieceAt(end), new Pawn(PieceColor.WHITE));
    }
}
