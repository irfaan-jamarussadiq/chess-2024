package org.chess.game;

import org.chess.board.Alliance;
import org.chess.board.BoardModel;
import org.chess.board.Location;
import org.chess.game.move.NormalMove;
import org.chess.game.move.TwoSquarePawnMove;
import org.chess.piece.King;
import org.chess.piece.Pawn;
import org.chess.piece.Piece;
import org.chess.piece.Rook;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameModelTest {
    @Test
    public void testCanMoveFrom() {
        BoardModel board = new BoardModel();
        GameModel game = new GameModel(board);

        moveHelper(game, 2, 5, 4, 5);
        moveHelper(game, 7, 4, 5, 4);
        moveHelper(game, 5, 4, 4, 5);

        Location start = new Location(8, 4);
        Piece piece = board.pieceAt(start);
        assertFalse(piece.canMoveFrom(start, new Location(1, 4), game.getBoard()));
    }

    @Test
    public void testPlayerKingLocationUpdatesOnKingMove() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 5, 4, 5);
        moveHelper(game, 7, 5, 5, 5);
        moveHelper(game, 1, 5, 2, 5);
        assertEquals(Player.getPlayer(Alliance.WHITE).getKingLocation(), new Location(2, 5));
    }

    @Test
    public void testWhiteIsInCheck() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 6, 4, 6);
        moveHelper(game, 7, 5, 5, 5);
        moveHelper(game, 4, 6, 5, 5);
        moveHelper(game, 8, 4, 4, 8);
        assertFalse(game.isInCheck(Player.getPlayer(Alliance.BLACK)));
        assertTrue(game.isInCheck(Player.getPlayer(Alliance.WHITE)));
    }

    @Test
    public void testFoolsMateIsCheckmate() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 5, 4, 5);
        moveHelper(game, 7, 7, 5, 7);
        moveHelper(game, 2, 4, 4, 4);
        moveHelper(game, 7, 6, 5, 6);
        moveHelper(game, 1, 4, 5, 8);
        assertFalse(game.isInCheckmate(Player.getPlayer(Alliance.WHITE)));
        assertTrue(game.isInCheckmate(Player.getPlayer(Alliance.BLACK)));
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
        assertFalse(game.isInCheckmate(Player.getPlayer(Alliance.WHITE)));
        assertTrue(game.isInCheckmate(Player.getPlayer(Alliance.BLACK)));
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

    @Test
    public void testCastlingIsSuccessful() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 5, 4, 5);
        moveHelper(game, 7, 5, 5, 5);
        moveHelper(game, 1, 7, 3, 6);
        moveHelper(game, 8, 7, 6, 6);
        moveHelper(game, 1, 6, 4, 3);
        moveHelper(game, 8, 6, 5, 3);
        moveHelper(game, 1, 5, 1, 7);
        assertEquals(game.getBoard().pieceAt(new Location(1, 7)), new King(Alliance.WHITE));
        assertEquals(game.getBoard().pieceAt(new Location(1, 6)), new Rook(Alliance.WHITE));
    }

    @Test
    public void testPawnTwoSquareMoveIsSuccessful() {
        GameModel game = new GameModel();
        Location start = new Location(2, 5);
        Location end = new Location(4, 5);
        Piece piece = game.getBoard().pieceAt(start);
        assertTrue(piece.canMoveFrom(start, end, game.getBoard()));
        game.move(start, end);
        Piece pawn = game.getBoard().pieceAt(end);
        assertEquals(pawn, new Pawn(Alliance.WHITE));
    }

    @Test
    public void testPawnTwoSquareMoveIsValid() {
        GameModel game = new GameModel();
        Location start = new Location(2, 5);
        Location end = new Location(4, 5);
        TwoSquarePawnMove move = new TwoSquarePawnMove(start, end);
        assertFalse(game.isInCheck(Player.getPlayer(Alliance.WHITE)));
        assertTrue(move.isValid(game.getBoard()));
    }

    @Test
    public void testPawnCanEnPassant() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 4, 4, 4);
        moveHelper(game, 7, 4, 6, 4);
        moveHelper(game, 4, 4, 5, 4);
        moveHelper(game, 7, 5, 5, 5);
        moveHelper(game, 5, 4, 6, 5);
        assertEquals(game.getBoard().pieceAt(new Location(6, 5)), new Pawn(Alliance.WHITE));
    }

    @Test
    public void testPawnCanEnPassant2() {
        GameModel game = new GameModel();
        moveHelper(game, 2, 5, 4, 5);
        moveHelper(game, 7, 5, 6, 5);
        moveHelper(game, 4, 5, 5, 5);
        moveHelper(game, 7, 4, 5, 4);
        moveHelper(game, 5, 5, 6, 4);
        assertEquals(game.getBoard().pieceAt(new Location(6, 4)), new Pawn(Alliance.WHITE));
    }

    private void moveHelper(GameModel game, int startRank, int startFile, int endRank, int endFile) {
        game.move(new Location(startRank, startFile), new Location(endRank, endFile));
    }
}
