package org.chess.model.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.chess.model.game.Move;
import org.chess.model.piece.Piece;
import org.junit.Test;

public class KnightLegalMoveTests {
    @Test
    public void testWhiteKnightHasTwoMovesAtStart() {
        BoardModel boardModel = new BoardModel();
        Location start = new Location(1, 7);
        Piece knight = boardModel.pieceAt(start);
        Collection<Move> moves = knight.getLegalMoves(start, boardModel);
        assertEquals(2, moves.size());
        assertTrue(moves.contains(new Move(start, new Location(3, 6))));
        assertTrue(moves.contains(new Move(start, new Location(3, 8))));
    }

    @Test
    public void testWhiteKnightHasNoMoves() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(2, 6), new Location(3, 6));
        boardModel.movePiece(new Location(7, 6), new Location(5, 6));
        boardModel.movePiece(new Location(2, 8), new Location(3, 8));
        Location start = new Location(1, 7);
        Piece knight = boardModel.pieceAt(start);
        Collection<Move> moves = knight.getLegalMoves(start, boardModel);
        assertEquals(0, moves.size());
    }

    @Test
    public void testBlackKnightHasEightMoves() {
        BoardModel boardModel = new BoardModel();
        boardModel.movePiece(new Location(1, 7), new Location(3, 6));
        boardModel.movePiece(new Location(7, 5), new Location(5, 5));
        boardModel.movePiece(new Location(3, 6), new Location(5, 5));
        System.out.println(boardModel);
        Location start = new Location(5, 5);
        Piece knight = boardModel.pieceAt(start);
        Collection<Move> moves = knight.getLegalMoves(start, boardModel);
        assertEquals(8, moves.size());
    }
}
