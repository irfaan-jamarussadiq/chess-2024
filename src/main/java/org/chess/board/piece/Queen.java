package org.chess.board.piece;

import org.chess.board.Alliance;
import org.chess.board.BoardModel;
import org.chess.board.Location;
import org.chess.game.Path;
import org.chess.game.PathHelpers;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Path> getCandidatePaths(Location location) {
        List<Path> paths = new ArrayList<>();

        int maxRank = Math.max(location.rank(), BoardModel.SIZE - location.rank());
        int maxFile = Math.max(location.file(), BoardModel.SIZE - location.file());
        int maxSquares = Math.max(maxRank, maxFile);

        paths.addAll(PathHelpers.getAllStraightPaths(location, maxSquares));
        paths.addAll(PathHelpers.getAllDiagonalPaths(location, maxSquares));
        return paths;
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        Piece bishop = new Bishop(getAlliance());
        Piece rook = new Rook(getAlliance());
        return bishop.canMoveFrom(start, end, board) || rook.canMoveFrom(start, end, board);
    }
}
