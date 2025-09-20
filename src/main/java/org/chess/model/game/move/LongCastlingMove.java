package org.chess.model.game.move;

import java.util.HashMap;
import java.util.Map;

import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.piece.King;
import org.chess.model.piece.Piece;
import org.chess.model.piece.Rook;

public class LongCastlingMove extends Move {
    public LongCastlingMove(Location start, Location end) {
        super(start, end);
    }

    @Override
    public void execute(BoardModel board) {
        board.movePiece(getStart(), getEnd());
        Location rookStart = new Location(getStart().rank(), 1);
        Location rookEnd = new Location(getStart().rank(), 4);
        board.movePiece(rookStart, rookEnd);
    }

    // TODO: There's duplication of logic here and in GameModel...
    @Override
    public boolean isValid(BoardModel board) {
        Location start = getStart();
        Location end = getEnd();
        Piece king = board.pieceAt(start);
        Piece rook = board.pieceAt(new Location(start.rank(), 1));

        return !board.isEmpty(start) 
            && start.file() == 5
            && end.file() == 3
            && king.getAlliance().getStartingPieceRank() == start.rank()
            && king instanceof King
            && rook instanceof Rook 
            && board.hasPieceAtLocationNotMoved(start)
            && board.hasPieceAtLocationNotMoved(new Location(start.rank(), 1))
            && board.isEmpty(new Location(getStart().rank(), 2))
            && board.isEmpty(new Location(getStart().rank(), 3))
            && board.isEmpty(new Location(getStart().rank(), 4));
    }

    @Override
    public Map<Location, Location> getLocationMappings(BoardModel board) {
        Map<Location, Location> locationMappings = new HashMap<>(4);
        Location rookStart = new Location(getStart().rank(), 1);
        Location rookEnd = new Location(getStart().rank(), 4);
        locationMappings.put(getStart(), getEnd());
        locationMappings.put(rookStart, rookEnd);
        return locationMappings;
    }
}
