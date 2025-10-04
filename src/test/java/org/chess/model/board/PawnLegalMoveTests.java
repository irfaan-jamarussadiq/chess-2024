package org.chess.model.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.chess.model.game.Move;
import org.chess.model.piece.Piece;
import org.junit.Test;

public class PawnLegalMoveTests {
    @Test
    public void testPawnAtRankTwoFileFourCanOnlyMoveOneOrTwoSquaresStraight() {
        BoardModel boardModel = new BoardModel();
        Location location = new Location(2, 4);
        Piece pawn = boardModel.pieceAt(location);
        Collection<Move> legalMoves = pawn.getLegalMoves(location, boardModel);
        assertEquals(legalMoves.size(), 2);
        assertTrue(legalMoves.contains(new Move(location, new Location(3, 4))));
        assertTrue(legalMoves.contains(new Move(location, new Location(4, 4))));
    }

    @Test
    public void testWhitePawnCanCaptureBlackPawn() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 4), new Location(4, 4));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));

        Location location = new Location(4, 4);
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> legalMoves = piece.getLegalMoves(location, boardModel);
        assertTrue(legalMoves.contains(new Move(location, new Location(5, 5))));
    }

    @Test
    public void testWhitePawnCanEnPassant() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 4), new Location(4, 4));
        boardModel.movePiece(new Location(7, 6), new Location(6, 6));
        boardModel.movePiece(new Location(4, 4), new Location(5, 4));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));

        Location location = new Location(5, 4);
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> legalMoves = piece.getLegalMoves(location, boardModel);
        assertTrue(legalMoves.contains(new Move(location, new Location(6, 5))));
    }  

    @Test
    public void testWhitePawnCannotEnPassantAfterOneMove() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 4), new Location(4, 4));
        boardModel.movePiece(new Location(7, 6), new Location(6, 6));
        boardModel.movePiece(new Location(4, 4), new Location(5, 4));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));
        boardModel.movePiece(new Location(7, 7), new Location(6, 7));

        Location location = new Location(5, 4);
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> legalMoves = piece.getLegalMoves(location, boardModel);
        assertFalse(legalMoves.contains(new Move(location, new Location(6, 5))));
    }
}
