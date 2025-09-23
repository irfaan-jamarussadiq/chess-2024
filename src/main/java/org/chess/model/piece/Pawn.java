package org.chess.model.piece;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.Move;

public class Pawn extends Piece {

    public Pawn(Alliance alliance) {
        super(alliance);
    }

    @Override
    public Collection<Location> getPossibleDestinations(Location location) {
        return Set.of(
            location.offset(alliance.getPawnDirection(), 0),
            location.offset(2 * alliance.getPawnDirection(), 0),
            location.offset(alliance.getPawnDirection(), -1),
            location.offset(alliance.getPawnDirection(), 1)
        );
    }

    @Override
    public boolean canMoveFrom(Location start, Location end) {
        int diffRank = end.rank() - start.rank();
        int diffFile = end.file() - start.file();
        int pawnDirection = alliance.getPawnDirection();
        return (diffRank == pawnDirection && diffFile == 0)
            || (diffRank == 2 * pawnDirection && diffFile == 0)
            || (diffRank == pawnDirection && diffFile == -1)
            || (diffRank == pawnDirection && diffFile == 1); 
    }

    @Override
    public char getLetter() {
        return alliance.isWhite() ? 'P' : 'p';
    }

    @Override
    public Collection<Move> getLegalMoves(Location location, BoardModel board) {
        Collection<Move> legalMoves = new HashSet<>();

        Collection<Location> possibleDestinations = getPossibleDestinations(location);
        for (Location possibleDestination : possibleDestinations) {
            if (possibleDestination.isWithinBounds() 
                && (board.isEmpty(possibleDestination) || !Piece.areAllies(this, board.pieceAt(possibleDestination)))) {
                legalMoves.add(new Move(location, possibleDestination));
            }
        }

        return legalMoves;
    }

    public static boolean isOneSquarePawnMove(Location start, Location end, BoardModel board) {
        Piece pawn = board.pieceAt(start);
        return start.rank() == end.rank()
            && end.equals(start.offset(pawn.getAlliance().getPawnDirection(), 0))
            && board.isEmpty(end);
    }

    public static boolean isTwoSquarePawnMove(Location start, Location end, BoardModel board) {
        Piece pawn = board.pieceAt(start);
		int pawnDirection = pawn.getAlliance().getPawnDirection();
		return board.hasPieceAtLocationNotMoved(start)
            && start.rank() == end.rank() 
            && start.rank() == pawn.getAlliance().getStartingPieceRank() 
			&& board.isEmpty(start.offset(pawnDirection, 0))
			&& end.equals(start.offset(2 * pawnDirection, 0))
			&& board.isEmpty(end);
    }

    public static boolean isCaptureMove(Location start, Location end, BoardModel board) {
        Piece pawn = board.pieceAt(start);
        Location leftCaptureLocation = start.offset(pawn.getAlliance().getPawnDirection(), -1);
        Location rightCaptureLocation = start.offset(pawn.getAlliance().getPawnDirection(), 1);

        boolean isLeftCapture = leftCaptureLocation.isWithinBounds()
            && board.pieceAt(leftCaptureLocation) instanceof Pawn
            && Piece.areEnemies(pawn, board.pieceAt(leftCaptureLocation));

        boolean isRightCapture = rightCaptureLocation.isWithinBounds()
            && board.pieceAt(rightCaptureLocation) instanceof Pawn
            && Piece.areEnemies(pawn, board.pieceAt(rightCaptureLocation));

        return isLeftCapture || isRightCapture;
    }

    public static boolean isEnPassantMove(Location start, Location end, BoardModel board) {
        Piece pawn = board.pieceAt(start);
		int rankDiff = end.rank() - start.rank(); 
		int fileDiff = end.file() - start.file(); 

		Piece enemyPawn = board.pieceAt(end.offset(0, fileDiff));	
		int enPassantRank = pawn.getAlliance().isWhite() ? 6 : 3; 

		return rankDiff == pawn.getAlliance().getPawnDirection() 
			&& Math.abs(fileDiff) == 1 
			&& start.rank() == enPassantRank 
			&& Piece.areEnemies(pawn, enemyPawn) 
			&& board.isEmpty(end.offset(0, fileDiff));
    }

}
