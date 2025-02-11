package org.chess.chess.board.piece;

import org.chess.chess.game.Direction;
import org.chess.chess.board.Alliance;
import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.game.Path;
import org.chess.chess.game.PathHelpers;

import java.util.List;

public class Bishop extends Piece {
    public Bishop(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Path> getCandidatePaths(Location location) {
        int maxRank = Math.max(location.rank(), BoardModel.SIZE - location.rank());
        int maxFile = Math.max(location.file(), BoardModel.SIZE - location.file());
        int maxSquares = Math.max(maxRank, maxFile);
        return PathHelpers.getAllDiagonalPaths(location, maxSquares);
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        if (Math.abs(end.rank() - start.rank()) != Math.abs(end.file() - start.file())) {
            return false;
        }

        int rankOffset = (start.rank() < end.rank()) ? 1 : -1;
        int fileOffset = (start.file() < end.file()) ? 1 : -1;
        Direction direction = new Direction(rankOffset, fileOffset);

        Location current = start.shift(direction);
        while (current.isWithinBounds() && !current.equals(end)) {
            if (!board.isEmpty(current)) {
                return false;
            }

            current = current.shift(direction);
        }

        return current.isWithinBounds() && !this.isFriendOf(board.pieceAt(current));
    }
}
