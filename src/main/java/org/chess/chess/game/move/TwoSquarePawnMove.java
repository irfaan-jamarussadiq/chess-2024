package org.chess.chess.game.move;

import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.Pawn;
import org.chess.chess.board.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class TwoSquarePawnMove extends Move {
    public TwoSquarePawnMove(Location start, Location end) {
        super(start, end);
    }

    @Override
    public void execute(BoardModel board) {
        board.pieceAt(getStart()).setHasMoved(true);
        board.movePiece(getStart(), getEnd());
    }

    @Override
    public boolean isValid(BoardModel board) {
        Piece pawn = board.pieceAt(getStart());
        int pawnDirection = pawn.getAlliance().getPawnDirection();
        Location oneSquareForward = new Location(getStart().rank() + pawnDirection, getStart().file());
        Location twoSquaresForward = new Location(getStart().rank() + 2 * pawnDirection, getStart().file());
        return pawn instanceof Pawn && !pawn.hasMoved() && board.isEmpty(oneSquareForward) && board.isEmpty(getEnd())
                && twoSquaresForward.equals(getEnd());
    }

    @Override
    public Map<Location, Location> getLocationMappings(BoardModel board) {
        Map<Location, Location> affectedLocations = new HashMap<>(2);
        affectedLocations.put(getStart(), getEnd());
        return affectedLocations;
    }
}
