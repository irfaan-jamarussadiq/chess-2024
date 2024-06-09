package org.chess.chess;

import org.chess.chess.board.Alliance;
import org.chess.chess.board.BoardModel;

public class Player {
    private final Alliance alliance;

    public Player(Alliance alliance) {
        this.alliance = alliance;
    }

    public boolean isInCheck(BoardModel board) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public boolean isInCheckmate(BoardModel board) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public boolean isInStalemate(BoardModel board) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public Alliance getAlliance() {
        return alliance;
    }
}
