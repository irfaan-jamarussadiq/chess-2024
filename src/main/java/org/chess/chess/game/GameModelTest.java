package org.chess.chess.game;

import org.chess.chess.board.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameModelTest {
    @Test
    public void testGameStartsOnWhiteTurn() {
        GameModel game = new GameModel();
        assertEquals(game.getNextPlayer(), GameModel.BLACK);
    }

    @Test
    public void testMoveChangesTurn() {
        GameModel game = new GameModel();
        game.move(new Location(2, 4), new Location(4, 4));
        assertEquals(game.getNextPlayer(), GameModel.WHITE);
    }

    @Test
    public void testPlayerKingLocationUpdatesOnKingMove() {
        GameModel game = new GameModel();
        game.move(new Location(2, 5), new Location(4, 5));
        game.move(new Location(7, 5), new Location(5, 5));
        game.move(new Location(1, 5), new Location(2, 5));
        assertEquals(GameModel.WHITE.getKingLocation(), new Location(2, 5));
    }
}
