package org.chess.chess.board.piece;

import org.chess.chess.Move;
import org.chess.chess.MoveListHelpers;
import org.chess.chess.board.Alliance;
import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(Alliance alliance) {
        super(alliance);
    }

    @Override
    public List<Move> getCandidateMoves(Location location) {
        List<Move> moves = new ArrayList<>();

        int maxRank = Math.max(location.rank(), BoardModel.SIZE - location.rank());
        int maxFile = Math.max(location.file(), BoardModel.SIZE - location.file());
        int maxSquares = Math.max(maxRank, maxFile);

        moves.addAll(MoveListHelpers.getAllStraightMoves(location, maxSquares));
        moves.addAll(MoveListHelpers.getAllDiagonalMoves(location, maxSquares));
        return moves;
    }
}
