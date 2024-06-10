package org.chess.chess.game;

import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;

public record BoardSnapshot(BoardModel board, Player player) {
    public BoardSnapshot(BoardModel board, Player player) {
        this.board = new BoardModel(board);
        this.player = new Player(player.getAlliance(), player.getKingLocation());
    }

    public Player getPlayer() {
        return player;
    }
}
