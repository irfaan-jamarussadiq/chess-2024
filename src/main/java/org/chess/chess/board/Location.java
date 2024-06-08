package org.chess.chess.board;

public record Location(int rank, int file) {
    public boolean isWithinBounds() {
        return rank >= 1 && rank <= BoardModel.SIZE && file >= 1 && file <= BoardModel.SIZE;
    }
}
