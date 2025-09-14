package org.chess.model.game;

import java.util.ArrayList;
import java.util.List;

import org.chess.model.board.Location;

public final class PathHelpers {
    private PathHelpers() {
        throw new UnsupportedOperationException("Cannot instantiate this class!");
    }

    public static List<Path> getAllDiagonalPaths(Location location, int maxSquares) {
        List<Path> paths = new ArrayList<>();
        paths.addAll(getLineOfPossiblePaths(location, new Direction(1, 1), maxSquares));
        paths.addAll(getLineOfPossiblePaths(location, new Direction(1, -1), maxSquares));
        paths.addAll(getLineOfPossiblePaths(location, new Direction(-1, 1), maxSquares));
        paths.addAll(getLineOfPossiblePaths(location, new Direction(-1, -1), maxSquares));
        return paths;
    }

    public static List<Path> getAllStraightPaths(Location location, int maxSquares) {
        List<Path> paths = new ArrayList<>();
        paths.addAll(getLineOfPossiblePaths(location, new Direction(1, 0), maxSquares));
        paths.addAll(getLineOfPossiblePaths(location, new Direction(0, 1), maxSquares));
        paths.addAll(getLineOfPossiblePaths(location, new Direction(-1, 0), maxSquares));
        paths.addAll(getLineOfPossiblePaths(location, new Direction(0, -1), maxSquares));
        return paths;
    }

    public static List<Path> getAllPossiblePaths(Location location, int maxSquares) {
        List<Path> paths = new ArrayList<>();

        List<Path> knightDirections = List.of(
                new Path(location, new Direction(-2, -1)),
                new Path(location, new Direction(-2, 1)),
                new Path(location, new Direction(-1, -2)),
                new Path(location, new Direction(-1, 2)),
                new Path(location, new Direction(1, -2)),
                new Path(location, new Direction(1, 2)),
                new Path(location, new Direction(2, -1)),
                new Path(location, new Direction(2, 1))
        );
        List<Path> bishopDirections = getAllDiagonalPaths(location, maxSquares);
        List<Path> rookDirections = getAllStraightPaths(location, maxSquares);

        paths.addAll(knightDirections);
        paths.addAll(bishopDirections);
        paths.addAll(rookDirections);
        return paths;
    }

    private static List<Path> getLineOfPossiblePaths(Location location, Direction direction, int maxSquares) {
        List<Path> paths = new ArrayList<>();
        for (int numSquares = 1; numSquares <= maxSquares; numSquares++) {
            Location end = location.offset(direction.rankOffset() * numSquares, direction.fileOffset() * numSquares);
            Path path = new Path(location, end);
            paths.add(path);
        }

        return paths;
    }
}
