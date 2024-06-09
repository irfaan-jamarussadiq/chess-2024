package org.chess.chess.board;

import org.chess.chess.Direction;

public record Location(int rank, int file) {
    public boolean isWithinBounds() {
        return rank >= 1 && rank <= BoardModel.SIZE && file >= 1 && file <= BoardModel.SIZE;
    }

    public Location shift(Direction direction, int amount) {
        return new Location(rank + amount * direction.rankOffset(), file + amount * direction.fileOffset());
    }

    public Location shift(Direction direction) {
        return shift(direction, 1);
    }
}
