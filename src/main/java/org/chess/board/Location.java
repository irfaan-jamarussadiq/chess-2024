package org.chess.board;

import org.chess.game.Direction;

public record Location(int rank, int file) {
    public boolean isWithinBounds() {
        return rank >= 1 && rank <= BoardModel.SIZE && file >= 1 && file <= BoardModel.SIZE;
    }

    public Location offset(int rankOffset, int fileOffset) {
        return new Location(rank + rankOffset, file + fileOffset);
    }

    public Location shift(Direction direction, int amount) {
        return new Location(rank + amount * direction.rankOffset(), file + amount * direction.fileOffset());
    }

    public Location shift(Direction direction) {
        return shift(direction, 1);
    }
}
