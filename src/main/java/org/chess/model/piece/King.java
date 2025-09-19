package org.chess.model.piece;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.MoveRecord;

public class King extends Piece {
    public King(Alliance alliance) {
        super(alliance);
    }

    @Override
    public boolean canMoveFrom(Location start, Location end, BoardModel board) {
        return start.isWithinBounds() && end.isWithinBounds()
            && !start.equals(end)
            && Math.abs(end.rank() - start.rank()) == Math.abs(end.file() - start.file())
            && Math.abs(end.rank() - start.rank()) <= 1
            && Math.abs(end.file() - start.file()) <= 1
            && !Piece.areAllies(this, board.pieceAt(end));
    }

    @Override
    public Collection<Location> getPossibleDestinations(Location location) {
        return Set.of(
            location.offset(-1, -1),
            location.offset(0, -1),
            location.offset(1, -1),
            location.offset(-1, 0),
            location.offset(-1, 1),
            location.offset(1, 1)
        );
    }

    @Override
    public boolean canMoveFrom(Location start, Location end) {
        int diffRank = Math.abs(start.rank() - end.rank());
        int diffFile = Math.abs(start.file() - end.file());
        return !start.equals(end) && diffRank <= 1 && diffFile <= 1;
    }

    @Override
    public char getLetter() {
        return alliance.isWhite() ? 'K' : 'k';
    }

    @Override
    public Collection<MoveRecord> getLegalMoves(Location location, BoardModel board) {
        Collection<MoveRecord> legalMoves = new HashSet<>();
        if (board.isEmpty(location)) {
            return legalMoves;
        }

        legalMoves.add(new MoveRecord(location, location.offset(-1, -1)));
        legalMoves.add(new MoveRecord(location, location.offset(-1, 1)));
        legalMoves.add(new MoveRecord(location, location.offset(0, -1)));
        legalMoves.add(new MoveRecord(location, location.offset(0, 1)));
        legalMoves.add(new MoveRecord(location, location.offset(1, -1)));
        legalMoves.add(new MoveRecord(location, location.offset(-1, 0)));
        legalMoves.add(new MoveRecord(location, location.offset(1, 0)));
        legalMoves.add(new MoveRecord(location, location.offset(1, 1)));

        if (isShortCastlingMove(location, new Location(alliance.getStartingPieceRank(),7), board)) {
            legalMoves.add(new MoveRecord(location, new Location(alliance.getStartingPieceRank(), 7)));
        }

        if (isLongCastlingMove(location, new Location(alliance.getStartingPieceRank(), 3), board)) {
            legalMoves.add(new MoveRecord(location, new Location(alliance.getStartingPieceRank(), 3)));
        }

        return legalMoves;
    }

    public static boolean isShortCastlingMove(Location start, Location end, BoardModel board) {
		Piece king = board.pieceAt(start);
		Piece rook = board.pieceAt(start.offset(0, 3));

		return start.rank() == end.rank() 
			&& start.rank() == king.getAlliance().getStartingPieceRank() 
			&& start.file() == 5 
			&& end.file() == 7
			&& king instanceof King
			&& rook instanceof Rook
			&& board.isEmpty(start.offset(0, 1))
			&& board.isEmpty(start.offset(0, 2))
			&& board.hasPieceNotMoved(king)
			&& board.hasPieceNotMoved(rook);
    }

    public static boolean isLongCastlingMove(Location start, Location end, BoardModel board) {
		Piece king = board.pieceAt(start);
		Piece rook = board.pieceAt(start.offset(0, -3));

		return start.rank() == end.rank() 
			&& start.rank() == king.getAlliance().getStartingPieceRank() 
			&& start.file() == 5 
			&& end.file() == 3
			&& king instanceof King
			&& rook instanceof Rook
			&& board.isEmpty(start.offset(0, -1))
			&& board.isEmpty(start.offset(0, -2))
			&& board.hasPieceNotMoved(king)
			&& board.hasPieceNotMoved(rook);
    }
}
