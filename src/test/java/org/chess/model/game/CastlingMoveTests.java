package org.chess.model.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.chess.model.board.Location;
import org.junit.Test;

public class CastlingMoveTests {
    @Test
    public void testWhiteKingHasCastled() {
        GameModel gameModel = new GameModel();
        gameModel.move(new Move(new Location(2, 4), new Location(4, 4)));
        gameModel.move(new Move(new Location(7, 4), new Location(5, 4)));
        gameModel.move(new Move(new Location(1, 7), new Location(3, 6)));
        gameModel.move(new Move(new Location(8, 7), new Location(6, 6)));
        gameModel.move(new Move(new Location(1, 6), new Location(4, 3)));
        gameModel.move(new Move(new Location(8, 6), new Location(4, 2)));
        gameModel.move(new Move(new Location(1, 5), new Location(1, 7)));
    }

    @Test
    public void testBlackKingHasCastled() {
        GameModel gameModel = new GameModel();
        gameModel.move(new Move(new Location(2, 4), new Location(4, 4)));
        gameModel.move(new Move(new Location(7, 4), new Location(5, 4)));
        gameModel.move(new Move(new Location(1, 7), new Location(3, 6)));
        gameModel.move(new Move(new Location(8, 7), new Location(6, 6)));
        gameModel.move(new Move(new Location(1, 6), new Location(4, 3)));
        gameModel.move(new Move(new Location(8, 6), new Location(4, 2)));
        gameModel.move(new Move(new Location(1, 5), new Location(1, 7)));
        gameModel.move(new Move(new Location(8, 5), new Location(8, 7)));
    }

    @Test
    public void testWhiteShortCastlingIsBlockedByPiecesInStartingPosition() {
        GameModel gameModel = new GameModel();
        Location start = new Location(1, 5);
        Location end = new Location(1, 7);
        assertTrue(gameModel.isValidMove(new Move(start, end)));
    }

    @Test
    public void testWhiteKingCannotCastleThroughCheck() {
        GameModel gameModel = new GameModel();
        gameModel.move(new Move(new Location(2, 4), new Location(4, 4)));
        gameModel.move(new Move(new Location(7, 4), new Location(5, 4)));
        gameModel.move(new Move(new Location(2, 5), new Location(4, 5)));
        gameModel.move(new Move(new Location(8, 7), new Location(6, 6)));
        gameModel.move(new Move(new Location(2, 1), new Location(4, 1)));
        gameModel.move(new Move(new Location(8, 6), new Location(4, 2)));
        assertFalse(gameModel.isValidMove(new Move(new Location(1, 5), new Location(1, 7))));
    }
}
