package org.chess.model.board;

import static org.junit.Assert.assertEquals;
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
    
}
