package org.chess.model.board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.chess.model.game.Move;
import org.chess.model.piece.Piece;
import org.junit.Test;

public class RookLegalMoveTests {
    @Test
    public void testWhiteQueenSideRookCannotMoveInStartingPosition() {
        testPieceAtLocationHasNoMoves(new BoardModel(), new Location(1, 1));
    }

    @Test
    public void testWhiteKingSideRookCannotMoveInStartingPosition() {
        testPieceAtLocationHasNoMoves(new BoardModel(), new Location(1, 8));
    }

    @Test
    public void testBlackQueenSideRookCannotMoveInStartingPosition() {
        testPieceAtLocationHasNoMoves(new BoardModel(), new Location(8, 1));
    }

    @Test
    public void testBlackKingSideRookCannotMoveInStartingPosition() {
        testPieceAtLocationHasNoMoves(new BoardModel(), new Location(8, 8));
    }

    private void testPieceAtLocationHasNoMoves(BoardModel boardModel, Location location) {
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> moves = piece.getLegalMoves(location, boardModel);
        assertEquals(0, moves.size());
    }

    @Test
    public void testWhiteQueenSideRookCanCaptureBlackBishop() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 1), new Location(4, 1));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));
        boardModel.movePiece(new Location(4, 1), new Location(5, 1));
        boardModel.movePiece(new Location(8, 6), new Location(3, 1));

        Location rookStart = new Location(1, 1);
        Piece piece = boardModel.pieceAt(rookStart);
        Collection<Move> moves = piece.getLegalMoves(rookStart, boardModel);
        assertEquals(2, moves.size());
        assertTrue(moves.contains(new Move(rookStart, new Location(2, 1))));
        assertTrue(moves.contains(new Move(rookStart, new Location(3, 1))));
        assertFalse(moves.contains(new Move(rookStart, new Location(4, 1))));
    }

    @Test
    public void testWhiteKingSideRookCanCaptureBlackBishop() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 8), new Location(4, 8));
        boardModel.movePiece(new Location(7, 4), new Location(5, 4));
        boardModel.movePiece(new Location(4, 8), new Location(5, 8));
        boardModel.movePiece(new Location(8, 3), new Location(3, 8));
        Location rookStart = new Location(1, 8);
        Piece piece = boardModel.pieceAt(rookStart);
        Collection<Move> moves = piece.getLegalMoves(rookStart, boardModel);
        assertEquals(2, moves.size());
        assertTrue(moves.contains(new Move(rookStart, new Location(2, 8))));
        assertTrue(moves.contains(new Move(rookStart, new Location(3, 8))));
        assertFalse(moves.contains(new Move(rookStart, new Location(4,8))));
    }

    @Test
    public void testBlackKingSideRookCanCaptureWhiteBishop() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 4), new Location(4, 4));
        boardModel.movePiece(new Location(7, 8), new Location(5, 8));
        boardModel.movePiece(new Location(1, 3), new Location(6, 8));
        Location rookStart = new Location(8, 8);
        Piece piece = boardModel.pieceAt(rookStart);
        Collection<Move> moves = piece.getLegalMoves(rookStart, boardModel);
        assertEquals(2, moves.size());
        assertTrue(moves.contains(new Move(rookStart, new Location(7, 8))));
        assertTrue(moves.contains(new Move(rookStart, new Location(6, 8))));
        assertFalse(moves.contains(new Move(rookStart, new Location(5,8))));
    }

    @Test
    public void testBlackQueenSideRookCanCaptureWhiteBishop() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 5), new Location(4, 5));
        boardModel.movePiece(new Location(7, 1), new Location(5, 1));
        boardModel.movePiece(new Location(1, 3), new Location(6, 8));
        Location rookStart = new Location(8, 8);
        Piece piece = boardModel.pieceAt(rookStart);
        Collection<Move> moves = piece.getLegalMoves(rookStart, boardModel);
        assertEquals(2, moves.size());
        assertTrue(moves.contains(new Move(rookStart, new Location(7, 8))));
        assertTrue(moves.contains(new Move(rookStart, new Location(6, 8))));
        assertFalse(moves.contains(new Move(rookStart, new Location(5,8))));
    }
}
