package org.chess.chess.game;

import org.chess.chess.board.Location;

public record Path(Location start, Location end) {
    public Path(Location location, Direction direction) {
        this(location, location.shift(direction));
    }

    public boolean isWithinBounds() {
        return start().isWithinBounds() && end.isWithinBounds();
    }
}
