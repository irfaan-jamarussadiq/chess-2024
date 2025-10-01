package org.chess.model.game;

import java.util.*;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.piece.King;
import org.chess.model.piece.Knight;
import org.chess.model.piece.Pawn;
import org.chess.model.piece.Piece;
import org.chess.model.piece.Queen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameModel {
    private Player currentPlayer;
    private Location currentKingLocation;
    private BoardModel board;

    private static final Logger logger = LoggerFactory.getLogger(GameModel.class);

    public GameModel(BoardModel board) {
        this.currentPlayer = Player.getPlayer(Alliance.WHITE);
        this.currentKingLocation = new Location(currentPlayer.getAlliance().getStartingPieceRank(), 5);
        this.board = board;
    }

    public GameModel() {
        this(new BoardModel());
    }

    public void move(Move move) {
        Piece piece = board.pieceAt(move.start());
        if (isValidMove(move)) {
            executeMove(move.start(), move.end(), board);
            if (piece instanceof King) {
                currentKingLocation = move.end();
            }
        }
    }

    private void executeMove(Location start, Location end, BoardModel board) {
        Piece piece = board.pieceAt(start);
        Piece enemy = board.pieceAt(end);

        if (piece == null) {
            return;
        }

        if (King.isShortCastlingMove(start, end, board)) {
            Location rookStart = new Location(start.rank(), 8);
            Location rookEnd = new Location(start.rank(), 6);
            board.movePiece(start, end);
            board.movePiece(rookStart, rookEnd);
        } else if (King.isLongCastlingMove(start, end, board)) {
            Location rookStart = new Location(start.rank(), 1);
            Location rookEnd = new Location(start.rank(), 4);
            board.movePiece(start, end);
            board.movePiece(rookStart, rookEnd);
        } else if (Pawn.isEnPassantMove(start, end, board)) {
            Location enPassant = new Location(start.rank(), end.file());
            board.movePiece(start, enPassant);
            board.movePiece(enPassant, end);
        } else if (Pawn.isTwoSquarePawnMove(start, end, board)) {
            board.movePiece(start, end);
        } else if (Pawn.isPromotionMove(start, end, board)) {
            board.movePiece(start, end);
            // TODO: Change pawn to promoted piece
        } else if (piece.canMoveFrom(start, end) && !Piece.areAllies(piece, enemy)) {
            board.movePiece(start, end);
        }

        logger.debug("After executing move: \n" + board.toString());
    }

    public boolean isValidMove(Move move) {
        if (!move.isWithinBounds() || move.start().equals(move.end())) {
            return false;
        }

        Piece piece = board.pieceAt(move.start());
        if (!piece.canMoveFrom(move.start(), move.end())) {
            return false;
        }

        Collection<Location> possibleDestinations = piece.getPossibleDestinations(move.start());
        for (Location destination : possibleDestinations) {
            if (!destination.isWithinBounds()) {
                continue;
            }

            BoardModel copy = new BoardModel(board);
            executeMove(move.start(), move.end(), board);
            boolean movePutPlayerInCheck = isInCheck(currentPlayer);
            board = copy;
            if (!movePutPlayerInCheck) {
                return true;
            }
        }

        return false;
    }

    public Collection<Move> getLegalMoves(Location location) {
        Collection<Move> legalMoves = new ArrayList<>();

        logger.info("Finding legal moves...");
        if (board.isEmpty(location)) {
            return legalMoves;
        }

        Piece piece = board.pieceAt(location);
        Collection<Move> pieceMoves =  piece.getLegalMoves(location, board);

        for (Move move : pieceMoves) {
            BoardModel boardBeforeMakingMove = new BoardModel(board);
            executeMove(move.start(), move.end(), board);
            boolean movePutPlayerInCheck = isInCheck(currentPlayer);
            board = boardBeforeMakingMove;
            if (!movePutPlayerInCheck) {
                legalMoves.add(move);
            }
        }

        logger.debug(String.format("Legal moves at %s are %s", location, legalMoves));
        return legalMoves;
    }

    public boolean isInCheck(Player player) {
        Piece king = board.pieceAt(currentKingLocation);
        Collection<Move> enemyAttackMoves = new HashSet<>();
        enemyAttackMoves.addAll(new Queen(player.getAlliance()).getLegalMoves(currentKingLocation, board));
        enemyAttackMoves.addAll(new Knight(player.getAlliance()).getLegalMoves(currentKingLocation, board));

        for (Move enemyAttackMove : enemyAttackMoves) {
            Piece potentialEnemy = board.pieceAt(enemyAttackMove.end());
            if (Piece.areEnemies(king, potentialEnemy)) {
                for (Move enemyMove : potentialEnemy.getLegalMoves(enemyAttackMove.end(), board)) {
                    if (enemyMove.end() == currentKingLocation) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isInCheckmate(Player player) {
        return isInCheck(player) && hasNoPossibleMoves(player);
    }

    public boolean isInStalemate(Player player) {
        return !isInCheck(player) && hasNoPossibleMoves(player);
    }

    private boolean hasNoPossibleMoves(Player player) {
        for (int rank = 1; rank <= BoardModel.SIZE; rank++) {
            for (int file = 1; file <= BoardModel.SIZE; file++) {
                Location location = new Location(rank, file);
                Piece piece = board.pieceAt(location);
                Collection<Move> moves = getLegalMoves(location);
                if (!board.isEmpty(location) && player.hasPiece(piece) && !moves.isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

}
