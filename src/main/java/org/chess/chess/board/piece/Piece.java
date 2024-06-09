package org.chess.chess.board.piece;

import org.chess.chess.game.Move;
import org.chess.chess.board.Alliance;
import org.chess.chess.board.Location;

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

    public abstract List<Move> getCandidateMoves(Location location);

    public boolean isEnemyOf(Piece enemyPawn) {
        return enemyPawn != null && alliance != enemyPawn.alliance;
    }
}
