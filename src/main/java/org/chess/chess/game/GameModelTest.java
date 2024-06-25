package org.chess.chess.game;

import org.chess.chess.board.Alliance;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.King;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest {
    @Test
    public void testGameStartsOnWhiteTurn() {
        GameModel game = new GameModel();
        assertEquals(game.getNextPlayer(), game.black);
    }

    @Test
    public void testMoveChangesTurn() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 4, 4, 4);
        assertEquals(game.getNextPlayer(), game.white);
    }

    @Test
    public void testPlayerKingLocationUpdatesOnKingMove() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 5, 4, 5);
        moveHelper(game, 7, 5, 5, 5);
        moveHelper(game, 1, 5, 2, 5);
        assertEquals(game.white.getKingLocation(), new Location(2, 5));
    }

    @Test
    public void testWhiteIsInCheck() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 6, 4, 6);
        moveHelper(game, 7, 5, 5, 5);
        moveHelper(game, 4, 6, 5, 5);
        moveHelper(game, 8, 4, 4, 8);
        assertFalse(game.isInCheck(game.black));
        assertTrue(game.isInCheck(game.white));
    }

    @Test
    public void testFoolsMateIsCheckmate() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 5, 4, 5);
        moveHelper(game, 7, 7, 5, 7);
        moveHelper(game, 2, 4, 4, 4);
        moveHelper(game, 7, 6, 5, 6);
        moveHelper(game, 1, 4, 5, 8);
        assertFalse(game.isInCheckmate(game.white));
        assertTrue(game.isInCheckmate(game.black));
    }

    @Test
    public void testScholarsMateIsCheckmate() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 5, 4, 5);
        moveHelper(game, 7, 5, 5, 5);
        moveHelper(game, 1, 6, 4, 3);
        moveHelper(game, 8, 2, 6, 3);
        moveHelper(game, 1, 4, 5, 8);
        moveHelper(game, 8, 7, 6, 6);
        moveHelper(game, 5, 8, 7, 6);
        assertFalse(game.isInCheckmate(game.white));
        assertTrue(game.isInCheckmate(game.black));
    }

    @Test
    public void testMoveOutOfBoundsIsInvalid() {
        GameModel game = new GameModel();
        assertFalse(game.isValidMove(new NormalMove(new Location(1, 4), new Location(0, 4))));
        assertFalse(game.isValidMove(new NormalMove(new Location(1, 1), new Location(1, -1))));
        assertFalse(game.isValidMove(new NormalMove(new Location(8, 8), new Location(8, 9))));
        assertFalse(game.isValidMove(new NormalMove(new Location(8, 1), new Location(9, 1))));
    }

    @Test
    public void testCastlingInvalidAtStart() {
        GameModel game = new GameModel();
        moveHelper(game, 1, 5, 1, 7);
        assertEquals(game.getBoard().pieceAt(new Location(1, 5)), new King(Alliance.WHITE));
    }

    private void moveHelper(GameModel game, int startRank, int startFile, int endRank, int endFile) {
        game.move(new Location(startRank, startFile), new Location(endRank, endFile));
        System.out.println(game.getBoard());
    }
}
