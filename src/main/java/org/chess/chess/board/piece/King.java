package org.chess.chess.board.piece;

import org.chess.chess.board.BoardModel;
import org.chess.chess.game.Direction;
import org.chess.chess.board.Alliance;
import org.chess.chess.board.Location;
import org.chess.chess.game.Path;

import java.util.List;

public class King extends Piece {
    public King(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Path> getCandidatePaths(Location location) {
        return List.of(
                new Path(location, new Direction(-1, -1)),
                new Path(location, new Direction(-1, 0)),
                new Path(location, new Direction(-1, 1)),
                new Path(location, new Direction(0, -1)),
                new Path(location, new Direction(0, 1)),
                new Path(location, new Direction(1, -1)),
                new Path(location, new Direction(1, 0)),
                new Path(location, new Direction(1, 1)),
                new Path(location, new Direction(0, -2)),
                new Path(location, new Direction(0, 2))
        );
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        if (!start.isWithinBounds() || !end.isWithinBounds()) {
            return false;
        }

        int diffRank = Math.abs(end.rank() - start.rank());
        int diffFile = Math.abs(end.file() - start.file());

        if (diffRank > 1) {
            return false;
        }

        int firstRank = (getAlliance() == Alliance.WHITE) ? 1 : BoardModel.SIZE;
        boolean isCastlingMove = start.rank() == firstRank && end.rank() == firstRank && start.file() == 5
                && (end.file() == 3 || end.file() == 7);
        boolean isFriend = !this.isFriendOf(board.pieceAt(end));
        return (diffFile > 1 && isCastlingMove && isFriend) || (!isCastlingMove && isFriend);
    }
}
