package org.chess.piece;

import org.chess.board.Alliance;
import org.chess.board.BoardModel;
import org.chess.board.Location;

import java.util.Collection;

public abstract class Piece {
    protected final Alliance alliance;
    private boolean hasMoved;

    public Piece(Alliance alliance) {
        this.alliance = alliance;
    }

    public static boolean areEnemies(Piece piece1, Piece piece2) {
        return piece1 != null && piece2 != null && piece1.alliance != piece2.alliance;
    }

    public static boolean areAllies(Piece piece1, Piece piece2) {
        return piece1 != null && piece2 != null && piece1.alliance == piece2.alliance;
    }

    public Alliance getAlliance() {
        return alliance;
    } 

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public boolean equals(Object other) {
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Piece otherPiece = (Piece) other;
        return otherPiece.alliance == this.alliance;
    }

    public abstract Collection<Location> getPossibleDestinations(Location location);

    public abstract boolean canMoveFrom(Location start, Location end);

    public abstract boolean canMoveFrom(Location start, Location end, BoardModel board);

    public abstract char getLetter();
}
