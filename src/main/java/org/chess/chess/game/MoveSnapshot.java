package org.chess.chess.game;

import org.chess.chess.board.BoardModel;

public record MoveSnapshot(BoardModel board, Player player) {
    public MoveSnapshot(BoardModel board, Player player) {
        this.board = new BoardModel(board);
        this.player = new Player(player.getAlliance(), player.getKingLocation());
    }

    public Player getPlayer() {
        return player;
    }
}
