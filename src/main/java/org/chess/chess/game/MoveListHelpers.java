package org.chess.chess.game;

import org.chess.chess.board.Location;

import java.util.ArrayList;
import java.util.List;

public final class MoveListHelpers {
    private MoveListHelpers() {
        throw new UnsupportedOperationException("Cannot instantiate this class!");
    }

    public static List<Move> getAllDiagonalMoves(Location location, int maxSquares) {
        List<Move> moves = new ArrayList<>();
        moves.addAll(getLineOfMoves(location, new Direction(1, 1), maxSquares));
        moves.addAll(getLineOfMoves(location, new Direction(1, -1), maxSquares));
        moves.addAll(getLineOfMoves(location, new Direction(-1, 1), maxSquares));
        moves.addAll(getLineOfMoves(location, new Direction(-1, -1), maxSquares));
        return moves;
    }

    public static List<Move> getAllStraightMoves(Location location, int maxSquares) {
        List<Move> moves = new ArrayList<>();
        moves.addAll(getLineOfMoves(location, new Direction(1, 0), maxSquares));
        moves.addAll(getLineOfMoves(location, new Direction(0, 1), maxSquares));
        moves.addAll(getLineOfMoves(location, new Direction(-1, 0), maxSquares));
        moves.addAll(getLineOfMoves(location, new Direction(0, -1), maxSquares));
        return moves;
    }

    public static List<Move> getAllPossibleMoves(Location location, int maxSquares) {
        List<Move> moves = new ArrayList<>();

        List<Move> knightMoves = List.of(
                new Move(location, location.shift(new Direction(-2, -1))),
                new Move(location, location.shift(new Direction(-2, 1))),
                new Move(location, location.shift(new Direction(-1, -2))),
                new Move(location, location.shift(new Direction(-1, 2))),
                new Move(location, location.shift(new Direction(1, -2))),
                new Move(location, location.shift(new Direction(1, 2))),
                new Move(location, location.shift(new Direction(2, -1))),
                new Move(location, location.shift(new Direction(2, 1)))
        );
        List<Move> bishopMoves = getAllDiagonalMoves(location, maxSquares);
        List<Move> rookMoves = getAllStraightMoves(location, maxSquares);

        moves.addAll(knightMoves);
        moves.addAll(bishopMoves);
        moves.addAll(rookMoves);
        return moves;
    }

    private static List<Move> getLineOfMoves(Location location, Direction direction, int maxSquares) {
        List<Move> moves = new ArrayList<>();
        for (int numSquares = 1; numSquares <= maxSquares; numSquares++) {
            Location end = location.shift(direction, numSquares);
            Move move = new Move(location, end);
            moves.add(move);
        }

        return moves;
    }
}
