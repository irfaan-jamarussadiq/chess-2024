package org.chess.model.game.move;

import java.util.HashMap;
import java.util.Map;

import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.piece.King;
import org.chess.model.piece.Piece;
import org.chess.model.piece.Rook;

public class ShortCastlingMove extends Move {
    public ShortCastlingMove(Location start, Location end) {
        super(start, end);
    }

    @Override
    public void execute(BoardModel board) {
        Location start = getStart();
        board.movePiece(start, getEnd());
        Location rookStart = new Location(start.rank(), 8);
        Location rookEnd = new Location(start.rank(), 6);
        board.movePiece(rookStart, rookEnd);
    }

    @Override
    public boolean isValid(BoardModel board) {
        Location start = getStart();
        Location end = getEnd();
        Piece king = board.pieceAt(start);
        if (!(king instanceof King) || !board.hasPieceNotMoved(king) || board.isEmpty(start)) {
            return false;
        }

        if (start.file() != 5 || start.rank() != end.rank() || king.getAlliance().getStartingPieceRank() != start.rank()) {
            return false;
        }

        Piece rook = board.pieceAt(new Location(start.rank(), 8));
        return end.file() == 7 && rook instanceof Rook && !rook.hasMoved()
            && board.isEmpty(new Location(start.rank(), 6))
            && board.isEmpty(new Location(start.rank(), 7));
    }

    @Override
    public Map<Location, Location> getLocationMappings(BoardModel board) {
        Map<Location, Location> locationMappings = new HashMap<>(4);
        Location start = getStart();
        Location rookStart = new Location(start.rank(), 8);
        Location rookEnd = new Location(start.rank(), 6);
        locationMappings.put(start, getEnd());
        locationMappings.put(rookStart, rookEnd);
        return locationMappings;
    }
}
