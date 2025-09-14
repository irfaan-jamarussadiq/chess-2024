package org.chess.model.game;

import org.chess.model.board.Location;

public record Path(Location start, Location end) {
    public Path(Location location, Direction direction) {
        this(location, location.offset(direction.rankOffset(), direction.fileOffset()));
    }

    public boolean isWithinBounds() {
        return start().isWithinBounds() && end.isWithinBounds();
    }
}
