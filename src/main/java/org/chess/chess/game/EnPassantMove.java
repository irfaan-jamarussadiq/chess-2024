package org.chess.chess.game;

import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.Pawn;
import org.chess.chess.board.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class EnPassantMove extends Move {
    public EnPassantMove(Location start, Location end) {
        super(start, end);
    }

    @Override
    public void execute(BoardModel board) {
        Location capturedPawnLocation = new Location(getStart().rank(), getEnd().file());
        board.movePiece(capturedPawnLocation, getEnd());
        board.movePiece(getStart(), getEnd());
    }

    @Override
    public boolean isValid(BoardModel board) {
        Piece pawn = board.pieceAt(getStart());
        if (!(pawn instanceof Pawn) || board.pieceAt(getEnd()) != null) {
            return false;
        }

        Location enemyPawnLocation = new Location(getEnd().rank(), getStart().file());
        Piece enemyPawn = board.pieceAt(enemyPawnLocation);
        if (!pawn.isEnemyOf(enemyPawn) || !(enemyPawn instanceof Pawn)) {
            return false;
        }

        return Math.abs(getStart().file() - getEnd().file()) == 1
                && getStart().rank() == pawn.getAlliance().getEnPassantStartingRank()
                && getEnd().rank() == pawn.getAlliance().getEnPassantEndingRank();
    }

    @Override
    public Map<Location, Location> getLocationMappings(BoardModel board) {
        Map<Location, Location> affectedLocations = new HashMap<>(3);
        Location capturedPawnStart = new Location(getStart().rank(), getEnd().file());
        Location capturedPawnEnd = new Location(getEnd().file(), getStart().rank());
        affectedLocations.put(getStart(), getEnd());
        affectedLocations.put(capturedPawnStart, capturedPawnEnd);
        return affectedLocations;
    }
}
