package org.chess.board.piece;

import org.chess.board.Alliance;
import org.chess.board.BoardModel;
import org.chess.board.Location;
import org.chess.game.Direction;
import org.chess.game.Path;

import java.util.List;

public class Knight extends Piece {
    public Knight(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Path> getCandidatePaths(Location location) {
        return List.of(
                new Path(location, new Direction(-2, 1)),
                new Path(location, new Direction(-1, -2)),
                new Path(location, new Direction(-2, -1)),
                new Path(location, new Direction(-1, 2)),
                new Path(location, new Direction(1, -2)),
                new Path(location, new Direction(1, 2)),
                new Path(location, new Direction(2, -1)),
                new Path(location, new Direction(2, 1))
        );
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        int diffRank = Math.abs(end.rank() - start.rank());
        int diffFile = Math.abs(end.file() - start.file());

        if ((diffRank == 2 && diffFile == 1) || (diffRank == 1 && diffFile == 2)) {
            return start.isWithinBounds() && end.isWithinBounds() && !this.isFriendOf(board.pieceAt(end));
        }

        return false;
    }
}
