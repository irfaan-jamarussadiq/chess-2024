package org.chess.board;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.piece.Pawn;
import org.chess.model.piece.Rook;

public class BoardModelTest {
    @Test
    public void testMovingPieceToNegativeRankAndFileFails() {
        BoardModel board = new BoardModel();
        Location location = new Location(-1, -1);
        assertThrows(IllegalArgumentException.class, () -> board.pieceAt(location));
    }
    @Test
    public void testMovingPieceToNegativeRankFails() {
        BoardModel board = new BoardModel();
        Location location = new Location(-1, 3);
        assertThrows(IllegalArgumentException.class, () -> board.pieceAt(location));
    }

    @Test
    public void testMovingPieceToNegativeFileFails() {
        BoardModel board = new BoardModel();
        Location location = new Location(1, -4);
        assertThrows(IllegalArgumentException.class, () -> board.pieceAt(location));
    }

    @Test
    public void testMovingPieceToRankEightFileEightSucceeds() {
        BoardModel board = new BoardModel();
        Location location = new Location(8, 8);
        assertEquals(board.pieceAt(location), new Rook(Alliance.BLACK));
    }

    @Test
    public void testMovingPieceAtRankTwoFileFourToRankFourFileFourSucceeds() {
        BoardModel board = new BoardModel();
        Location start = new Location(2, 4);
        Location end = new Location(4, 4);
        board.movePiece(start, end);
        assertEquals(board.pieceAt(end), new Pawn(Alliance.WHITE));
    }

    @Test
    public void testNoPieceExistsAtRankFourFileFour() {
        BoardModel board = new BoardModel();
        Location start = new Location(4, 4);
        assertNull(board.pieceAt(start));
    }

    @Test
    public void testPawnExistsAtRankTwoFileFour() {
        BoardModel board = new BoardModel();
        Location start = new Location(2, 4);
        assertEquals(board.pieceAt(start), new Pawn(Alliance.WHITE));
    }
}
