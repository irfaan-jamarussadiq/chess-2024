package org.chess.model.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.chess.model.board.Alliance;
import org.chess.model.board.Location;
import org.junit.Test;

public class KingCheckTests {
    @Test
    public void testWhiteKingIsNotInCheckAtStart() {
        GameModel gameModel = new GameModel();
        assertFalse(gameModel.isInCheck(Player.getPlayer(Alliance.WHITE)));
    }

    @Test
    public void testBlackKingIsNotInCheckAtStart() {
        GameModel gameModel = new GameModel();
        assertFalse(gameModel.isInCheck(Player.getPlayer(Alliance.BLACK)));
    }

    @Test
    public void testWhiteKingIsInCheckByBlackBishop() {
        GameModel gameModel = new GameModel();
        gameModel.move(new Move(new Location(2, 4), new Location(4, 4)));
        gameModel.move(new Move(new Location(7, 5), new Location(5, 5)));
        gameModel.move(new Move(new Location(4, 4), new Location(5, 5)));
        gameModel.move(new Move(new Location(8, 6), new Location(4, 2)));
        assertTrue(gameModel.isInCheck(Player.getPlayer(Alliance.WHITE)));
    }
}
