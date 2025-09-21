package org.chess.game;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.Move;
import org.chess.model.piece.Piece;
import org.junit.Test;

public class PawnMoveTests {
    @Test
    public void testWhitePawnCanMoveForwardOneSquare() {
        BoardModel boardModel = new BoardModel();
        Location start = new Location(2, 5);
        Piece pawn = boardModel.pieceAt(start);
        Collection<Move> legalMoves = pawn.getLegalMoves(start, boardModel);
        assertTrue(legalMoves.contains(new Move(start, start.offset(1, 0))));
    } 

    @Test
    public void testWhitePawnCanMoveForwardTwoSquares() {
        BoardModel boardModel = new BoardModel();
        Location start = new Location(2, 5);
        Piece pawn = boardModel.pieceAt(start);
        Collection<Move> legalMoves = pawn.getLegalMoves(start, boardModel);
        assertTrue(legalMoves.contains(new Move(start, start.offset(2, 0))));
    }     
}
