package org.chess.chess.game;

import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.Piece;

import java.util.Map;

public abstract class Move {
    private final Location start;
    private final Location end;

    public Move(Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    public boolean isWithinBounds() {
        return start.isWithinBounds() && end.isWithinBounds();
    }

    public abstract void execute(BoardModel board);

    public abstract boolean isValid(BoardModel board);

    public abstract Map<Location, Location> getLocationMappings(BoardModel board);

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Move otherMove = (Move) other;
        return start.equals(otherMove.start) && end.equals(otherMove.end);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " from " + start + " to " + end;
    }
}
