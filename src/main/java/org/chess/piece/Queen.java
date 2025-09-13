package org.chess.piece;

import org.chess.board.Alliance;
import org.chess.board.BoardModel;
import org.chess.board.Location;

import java.util.Collection;
import java.util.HashSet;

public class Queen extends Piece {
    public Queen(Alliance alliance) {
        super(alliance);
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        Piece bishop = new Bishop(alliance);
        Piece rook = new Rook(alliance);
        return bishop.canMoveFrom(start, end, board) || rook.canMoveFrom(start, end, board);
    }

    @Override
    public Collection<Location> getPossibleDestinations(Location location) {
        Collection<Location> possibleDestinations = new HashSet<>();
        possibleDestinations.addAll(new Bishop(alliance).getPossibleDestinations(location));
        possibleDestinations.addAll(new Rook(alliance).getPossibleDestinations(location));
        return possibleDestinations;
    }

    @Override
    public boolean canMoveFrom(Location start, Location end) {
        Piece bishop = new Bishop(alliance);
        Piece rook = new Rook(alliance);
        return bishop.canMoveFrom(start, end) || rook.canMoveFrom(start, end);
    }
}
