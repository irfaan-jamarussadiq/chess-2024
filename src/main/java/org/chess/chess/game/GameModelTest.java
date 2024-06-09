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
}
