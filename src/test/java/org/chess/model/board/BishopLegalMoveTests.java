package org.chess.model.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.chess.model.game.Move;
import org.chess.model.piece.Piece;
import org.junit.Test;

public class BishopLegalMoveTests {
    @Test
    public void testKingsideBishopCannotMoveInStartingPosition() {
        testPieceAtLocationHasNoMoves(new Location(1, 3));
    }

    @Test
    public void testQueensideBishopCannotMoveInStartingPosition() {
        testPieceAtLocationHasNoMoves(new Location(1, 6));
    }

    @Test
    public void testBlackKingsideBishopCannotMoveInStartingPosition() {
        testPieceAtLocationHasNoMoves(new Location(8, 3));
    }

    @Test
    public void testBlackQueensideBishopCannotMoveInStartingPosition() {
        testPieceAtLocationHasNoMoves(new Location(8, 6));
    }

    private void testPieceAtLocationHasNoMoves(Location location) {
        BoardModel boardModel = new BoardModel();
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> moves = piece.getLegalMoves(location, boardModel);
        assertEquals(0, moves.size());
    }

    @Test
    public void testBishopCannotMoveThroughPiece() {
        BoardModel boardModel = new BoardModel();
        Location location = new Location(1, 3);
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> legalMoves = piece.getLegalMoves(location, boardModel);
        assertFalse(legalMoves.contains(new Move(location, new Location(2, 2))));
    }

    @Test
    public void testBishopCanMoveToEmptySquare() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 5), new Location(4, 5));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));
        Location start = new Location(1, 6);
        Piece piece = boardModel.pieceAt(start);
        Collection<Move> legalMoves = piece.getLegalMoves(start, boardModel);
        assertTrue(legalMoves.contains(new Move(start, new Location(3, 4))));
    }
}
