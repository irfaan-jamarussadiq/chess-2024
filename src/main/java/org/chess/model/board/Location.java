package org.chess.model.board;

public record Location(int rank, int file) {
    public boolean isWithinBounds() {
        return rank >= 1 && rank <= BoardModel.SIZE && file >= 1 && file <= BoardModel.SIZE;
    }

    public Location offset(int rankOffset, int fileOffset) {
        return new Location(rank + rankOffset, file + fileOffset);
    }

    public int getCoordinate() {
        return BoardModel.SIZE * (rank - 1) + (file - 1);
    }
}
