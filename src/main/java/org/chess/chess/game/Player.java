package org.chess.chess.game;

import org.chess.chess.board.Alliance;
import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.Piece;

import java.util.List;

public class Player {
    private final Alliance alliance;
    private Location kingLocation;

    public Player(Alliance alliance, Location kingLocation) {
        this.alliance = alliance;
        this.kingLocation = kingLocation;
    }

    public boolean isInCheck(BoardModel board) {
        Piece king = board.pieceAt(kingLocation);
        List<Move> movesAtKingLocation = MoveListHelpers.getAllPossibleMoves(kingLocation, BoardModel.SIZE);
        for (Move move : movesAtKingLocation) {
            if (move.isWithinBounds()) {
                Piece potentialEnemy = board.pieceAt(move.end());
                if (king.isEnemyOf(potentialEnemy)
                        && potentialEnemy.canMoveFrom(move.end(), move.start(), board)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isInCheckmate(BoardModel board) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public boolean isInStalemate(BoardModel board) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public Location getKingLocation() {
        return kingLocation;
    }

    public void setKingLocation(Location location) {
        this.kingLocation = location;
    }
}
