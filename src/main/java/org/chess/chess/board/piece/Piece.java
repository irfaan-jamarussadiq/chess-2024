package org.chess.chess.board.piece;

import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Alliance;
import org.chess.chess.board.Location;
import org.chess.chess.game.Path;

import java.util.List;

public abstract class Piece {
    private final Alliance alliance;

    public Piece(Alliance alliance) {
        this.alliance = alliance;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Piece otherPiece = (Piece) other;
        return otherPiece.alliance == this.alliance;
    }

    public abstract List<Path> getCandidatePaths(Location location);
    public abstract boolean canMoveFrom(Location start, Location end, BoardModel board);

    public boolean isEnemyOf(Piece enemy) {
        return enemy != null && alliance != enemy.alliance;
    }

    public boolean isFriendOf(Piece friend) {
        return friend != null && alliance == friend.alliance;
    }
}
