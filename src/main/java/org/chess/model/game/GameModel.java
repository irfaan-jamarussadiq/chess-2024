package org.chess.model.game;

import java.util.*;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.piece.King;
import org.chess.model.piece.Pawn;
import org.chess.model.piece.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameModel {
    private Player currentPlayer;
    private BoardModel board;

    private static final Logger logger = LoggerFactory.getLogger(GameModel.class);

    public GameModel(BoardModel board) {
        this.currentPlayer = Player.getPlayer(Alliance.WHITE);
        this.board = board;
    }

    public GameModel() {
        this(new BoardModel());
    }

    public void move(Move move) {
        Piece piece = board.pieceAt(move.start());
        if (currentPlayer.isPieceAlly(piece) && isValidMove(move)) {
            executeMove(move.start(), move.end(), board);
            if (piece instanceof King) {
                currentPlayer.setKingLocation(move.end());
            }

            currentPlayer = currentPlayer.getOpponent();
        }

        logger.debug("After executing move: \n" + board.toString());
    }

    private void executeMove(Location start, Location end, BoardModel board) {
        if (board.isEmpty(start)) {
            return;
        }

        Piece piece = board.pieceAt(start);
        Piece enemy = board.pieceAt(end);
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
    }

    public boolean isValidMove(Move move) {
        if (!move.isWithinBounds() || move.start().equals(move.end())) {
            return false;
        }

        BoardModel copy = new BoardModel(board);
        executeMove(move.start(), move.end(), copy);
        return !isInCheck(currentPlayer, copy);
    }

    public Collection<Move> getLegalMoves(Location location) {
        Collection<Move> legalMoves = new ArrayList<>();

        logger.info("Finding legal moves...");
        if (board.isEmpty(location)) {
            return legalMoves;
        }

        Piece piece = board.pieceAt(location);
        if (!currentPlayer.isPieceAlly(piece)) {
            return legalMoves;
        }

        Collection<Move> pieceMoves =  piece.getLegalMoves(location, board);
        for (Move move : pieceMoves) {
            if (isValidMove(move)) {
                legalMoves.add(move);
            }
        }

        logger.debug(String.format("Legal moves at %s are %s", location, legalMoves));
        return legalMoves;
    }

    public boolean isInCheck(Player player) {
        return isInCheck(player, board);
    }

    private boolean isInCheck(Player player, BoardModel board) {
        Piece king = board.pieceAt(player.getKingLocation());
        for (int rank = 1; rank <= 8; rank++) {
            for (int file = 1; file <= 8; file++) {
                Location location = new Location(rank, file);
                Piece potentialEnemy = board.pieceAt(location);
                if (Piece.areEnemies(king, potentialEnemy)) {
                    Collection<Move> enemyAttackMoves = potentialEnemy.getLegalMoves(location, board);
                    for (Move enemyMove : enemyAttackMoves) {
                        if (enemyMove.end().equals(currentPlayer.getKingLocation())) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean isInCheckmate(Player player) {
        return isInCheck(player, board) && hasNoPossibleMoves(player);
    }

    public boolean isInStalemate(Player player) {
        return !isInCheck(player, board) && hasNoPossibleMoves(player);
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
