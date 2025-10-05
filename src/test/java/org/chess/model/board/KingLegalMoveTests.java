package org.chess.model.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.chess.model.game.Move;
import org.chess.model.piece.Piece;
import org.junit.Test;

public class KingLegalMoveTests {
    @Test
    public void testWhiteKingCannotMoveInStartingPosition() {
        BoardModel boardModel = new BoardModel();
        Location location = new Location(1, 5);
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> legalMoves = piece.getLegalMoves(location, boardModel);
        assertEquals(0, legalMoves.size());
    }

    @Test
    public void testBlackKingCannotMoveInStartingPosition() {
        BoardModel boardModel = new BoardModel();
        Location location = new Location(8, 5);
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> legalMoves = piece.getLegalMoves(location, boardModel);
        assertEquals(0, legalMoves.size());
    }

    @Test
    public void testWhiteKingCanOnlyMoveToRankOneFileFive() {
        BoardModel boardModel = new BoardModel();
        Location location = new Location(1, 5);
        boardModel.movePiece(new Location(2, 5), new Location(4, 5));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> legalMoves = piece.getLegalMoves(location, boardModel);
        assertEquals(1, legalMoves.size());
        assertTrue(legalMoves.contains(new Move(location, new Location(2, 5))));
    }

    @Test
    public void testBlackKingCanOnlyMoveToRankEightFileFive() {
        BoardModel boardModel = new BoardModel();
        Location location = new Location(8, 5);
        boardModel.movePiece(new Location(2, 5), new Location(4, 5));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> legalMoves = piece.getLegalMoves(location, boardModel);
        assertEquals(1, legalMoves.size());
        assertTrue(legalMoves.contains(new Move(location, new Location(7, 5))));
    }

    @Test
    public void testWhiteCanCastle() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 5), new Location(4, 5));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));
        boardModel.movePiece(new Location(1, 7), new Location(3, 6));
        boardModel.movePiece(new Location(7, 7), new Location(6, 7));
        boardModel.movePiece(new Location(1, 6), new Location(3, 4));
        boardModel.movePiece(new Location(7, 1), new Location(5, 1));
        
        Location location = new Location(1, 5);
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> legalMoves = piece.getLegalMoves(location, boardModel);
        assertEquals(3, legalMoves.size());
        assertTrue(legalMoves.contains(new Move(location, new Location(1, 7))));
    }

    @Test
    public void testBlackCanCastle() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 5), new Location(4, 5));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));
        boardModel.movePiece(new Location(1, 7), new Location(3, 6));
        boardModel.movePiece(new Location(8, 7), new Location(6, 6));
        boardModel.movePiece(new Location(1, 6), new Location(3, 4));
        boardModel.movePiece(new Location(8, 6), new Location(6, 4));
        boardModel.movePiece(new Location(1, 5), new Location(1, 7));
        
        Location location = new Location(8, 5);
        Piece piece = boardModel.pieceAt(location);
        Collection<Move> legalMoves = piece.getLegalMoves(location, boardModel);
        assertEquals(3, legalMoves.size());
        assertTrue(legalMoves.contains(new Move(location, new Location(8, 7))));
    }
        
}
