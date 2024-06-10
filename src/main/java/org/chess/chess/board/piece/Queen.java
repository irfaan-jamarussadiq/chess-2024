package org.chess.chess.board.piece;

import org.chess.chess.game.Move;
import org.chess.chess.game.MoveListHelpers;
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

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        int diffRank = Math.abs(end.rank() - start.rank());
        int diffFile = Math.abs(end.file() - start.file());
        if (diffRank != diffFile && diffRank != 0 && diffFile != 0) {
            return false;
        }

        return !this.isFriend(board.pieceAt(end));
    }
}
