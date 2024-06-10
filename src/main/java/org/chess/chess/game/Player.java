package org.chess.chess.game;

import org.chess.chess.board.Alliance;
import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.Piece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private final Alliance alliance;
    private Location kingLocation;

    public Player(Alliance alliance, Location kingLocation) {
        this.alliance = alliance;
        this.kingLocation = kingLocation;
    }

    public boolean isInCheck(GameModel game) {
        Piece king = game.getBoard().pieceAt(kingLocation);
        List<Move> movesAtKingLocation = MoveListHelpers.getAllPossibleMoves(kingLocation, BoardModel.SIZE);
        for (Move move : movesAtKingLocation) {
            if (move.isWithinBounds()) {
                Piece potentialEnemy = game.getBoard().pieceAt(move.end());
                if (king.isEnemyOf(potentialEnemy)
                        && potentialEnemy.canMoveFrom(move.end(), move.start(), game.getBoard())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isInCheckmate(GameModel game) {
        return isInCheck(game) && hasNoPossibleMoves(game);
    }

    public boolean isInStalemate(GameModel game) {
        return !isInCheck(game) && hasNoPossibleMoves(game);
    }

    private boolean hasNoPossibleMoves(GameModel game) {
        Map<Location, Piece> playerPieces = new HashMap<>();
        for (int rank = 1; rank <= BoardModel.SIZE; rank++) {
            for (int file = 1; file <= BoardModel.SIZE; file++) {
                Location location = new Location(rank, file);
                Piece piece = game.getBoard().pieceAt(location);
                if (piece.getAlliance() == alliance) {
                    playerPieces.put(location, piece);
                }
            }
        }

        for (Map.Entry<Location, Piece> entry : playerPieces.entrySet()) {
            Location location = entry.getKey();
            Piece piece = entry.getValue();
            List<Move> candidateMoves = piece.getCandidateMoves(location);
            for (Move move : candidateMoves) {
                if (game.isValidMove(move, game.getBoard()) && piece.canMoveFrom(location, move.end(), game.getBoard())) {
                    return false;
                }
            }
        }

        return true;
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
