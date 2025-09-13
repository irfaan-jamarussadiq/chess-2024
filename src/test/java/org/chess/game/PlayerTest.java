package org.chess.game;

import static org.junit.Assert.*;

import org.chess.board.Alliance;
import org.junit.Test;

public class PlayerTest {
    @Test
    public void testBlackIsOpponentOfWhite() {
        Player player = Player.getPlayer(Alliance.WHITE);
        assertEquals(Player.getPlayer(Alliance.BLACK), player.getOpponent());
    }    

    @Test
    public void testWhiteIsOpponentOfBlack() {
        Player player = Player.getPlayer(Alliance.BLACK);
        assertEquals(Player.getPlayer(Alliance.WHITE), player.getOpponent());
    }    
}
