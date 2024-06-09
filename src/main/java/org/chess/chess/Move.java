package org.chess.chess;

import org.chess.chess.board.Location;

public record Move(Location start, Location end) {
    public boolean isWithinBounds() {
        return start.isWithinBounds() && end.isWithinBounds();
    }
}
