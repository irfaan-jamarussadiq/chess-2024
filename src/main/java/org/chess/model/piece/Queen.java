package org.chess.model.piece;

import java.util.Collection;
import java.util.HashSet;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.MoveRecord;

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
    
    @Override
    public char getLetter() {
        return alliance.isWhite() ? 'Q' : 'q';
    }

    @Override
    public Collection<MoveRecord> getLegalMoves(Location location, BoardModel board) {
        Collection<MoveRecord> legalMoves = new HashSet<>();
        legalMoves.addAll(new Bishop(alliance).getLegalMoves(location, board));
        legalMoves.addAll(new Rook(alliance).getLegalMoves(location, board));
        return legalMoves;
    }
}
