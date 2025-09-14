package org.chess.model.game.move;

import java.util.HashMap;
import java.util.Map;

import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.piece.Piece;

public class NormalMove extends Move {
    public NormalMove(Location start, Location end) {
        super(start, end);
    }

    @Override
    public void execute(BoardModel board) {
        board.movePiece(getStart(), getEnd());
    }

    @Override
    public boolean isValid(BoardModel board) {
        Piece piece = board.pieceAt(getStart());
        return piece.canMoveFrom(getStart(), getEnd(), board);
    }

    @Override
    public Map<Location, Location> getLocationMappings(BoardModel board) {
        Map<Location, Location> affectedLocations = new HashMap<>(2);
        affectedLocations.put(getStart(), getEnd());
        return affectedLocations;
    }
}
