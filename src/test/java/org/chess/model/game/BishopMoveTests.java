package org.chess.model.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.piece.Piece;
import org.junit.Test;

public class BishopMoveTests {
    @Test
    public void testBishopCanCapturePawn() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 5), new Location(4, 5));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));
        boardModel.movePiece(new Location(1, 6), new Location(4, 3));
        boardModel.movePiece(new Location(7, 4), new Location(5, 4));

        Location bishopStart = new Location(4, 3);
        Location bishopEnd = new Location(5, 4);
        Piece piece = boardModel.pieceAt(bishopStart);
        Collection<Move> legalMoves = piece.getLegalMoves(bishopStart, boardModel);
        assertTrue(legalMoves.contains(new Move(bishopStart, bishopEnd)));
    } 

    @Test
    public void testBishopHasNoMovesAtStart() {
        BoardModel boardModel = new BoardModel();
        Location bishopStart = new Location(1, 6);
        Piece piece = boardModel.pieceAt(bishopStart);
        Collection<Move> legalMoves = piece.getLegalMoves(bishopStart, boardModel);
        assertTrue(legalMoves.isEmpty());
    }

    @Test
    public void testBishopCannotPassThroughPawn() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 5), new Location(4, 5));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));
        boardModel.movePiece(new Location(1, 6), new Location(4, 3));
        boardModel.movePiece(new Location(7, 4), new Location(5, 4));

        Location bishopStart = new Location(4, 3);
        Piece piece = boardModel.pieceAt(bishopStart);
        Collection<Move> legalMoves = piece.getLegalMoves(bishopStart, boardModel);
        assertFalse(legalMoves.contains(new Move(bishopStart, new Location(6, 5))));
    }
}
