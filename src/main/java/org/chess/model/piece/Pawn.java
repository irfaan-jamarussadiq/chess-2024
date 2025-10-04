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
            Move move = new Move(location, possibleDestination);
            if (isOneSquarePawnMove(location, possibleDestination, board)
                || isTwoSquarePawnMove(location, possibleDestination, board)
                || isCaptureMove(location, possibleDestination, board)
                || isEnPassantMove(location, possibleDestination, board)
                || isPromotionMove(location, possibleDestination, board)) {
                    legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    public static boolean isOneSquarePawnMove(Location start, Location end, BoardModel board) {
        Piece pawn = board.pieceAt(start);
        return start.file() == end.file()
            && end.equals(start.offset(pawn.getAlliance().getPawnDirection(), 0))
            && board.isEmpty(end);
    }

    public static boolean isTwoSquarePawnMove(Location start, Location end, BoardModel board) {
        Piece pawn = board.pieceAt(start);
		int pawnDirection = pawn.getAlliance().getPawnDirection();
		return board.hasPieceAtLocationNotMoved(start)
            && start.file() == end.file() 
            && start.rank() == pawn.getAlliance().getStartingPawnRank() 
			&& board.isEmpty(start.offset(pawnDirection, 0))
			&& end.equals(start.offset(2 * pawnDirection, 0))
			&& board.isEmpty(end);
    }

    public static boolean isCaptureMove(Location start, Location end, BoardModel board) {
        Piece pawn = board.pieceAt(start);
        Location leftCaptureLocation = start.offset(pawn.getAlliance().getPawnDirection(), -1);
        Location rightCaptureLocation = start.offset(pawn.getAlliance().getPawnDirection(), 1);

        boolean isLeftCapture = leftCaptureLocation.isWithinBounds()
            && leftCaptureLocation.equals(end)
            && board.pieceAt(leftCaptureLocation) instanceof Pawn
            && Piece.areEnemies(pawn, board.pieceAt(leftCaptureLocation));

        boolean isRightCapture = rightCaptureLocation.isWithinBounds()
            && rightCaptureLocation.equals(end)
            && board.pieceAt(rightCaptureLocation) instanceof Pawn
            && Piece.areEnemies(pawn, board.pieceAt(rightCaptureLocation));

        return isLeftCapture || isRightCapture;
    }

    public static boolean isEnPassantMove(Location start, Location end, BoardModel board) {
        Piece pawn = board.pieceAt(start);
		int rankDiff = end.rank() - start.rank(); 
		int fileDiff = end.file() - start.file(); 

        if (!end.offset(0, fileDiff).isWithinBounds()) {
            return false;
        }

		Piece enemyPawn = board.pieceAt(end.offset(0, fileDiff));	
		int enPassantRank = pawn.getAlliance().isWhite() ? 5 : 6; 

		return rankDiff == pawn.getAlliance().getPawnDirection() 
			&& Math.abs(fileDiff) == 1 
			&& start.rank() == enPassantRank 
            && enemyPawn instanceof Pawn
			&& Piece.areEnemies(pawn, enemyPawn) 
			&& board.isEmpty(end);
    }

    public static boolean isPromotionMove(Location start, Location end, BoardModel board) {
        Piece pawn = board.pieceAt(start);
        return start.file() == end.file()
            && end.rank() == start.rank() + pawn.getAlliance().getPawnDirection()
            && board.isEmpty(end)
            && end.rank() == pawn.getAlliance().getEnemy().getStartingPieceRank();
    }
}
