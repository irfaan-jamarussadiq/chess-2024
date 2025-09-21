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
    private Collection<Move> history;
    private Player currentPlayer;
    private Location currentKingLocation;
    private BoardModel board;

    private static final Logger logger = LoggerFactory.getLogger(GameModel.class);

    public GameModel(BoardModel board) {
        this.currentPlayer = Player.getPlayer(Alliance.WHITE);
        this.currentKingLocation = new Location(currentPlayer.getAlliance().getStartingPieceRank(), 5);
        this.history = new Stack<>();
        this.board = board;
    }

    public GameModel() {
        this(new BoardModel());
    }


    public void executeMove(Location start, Location end) {
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
        } else if (isStandardMove(start, end)) {
            board.movePiece(start, end);
        }

        logger.debug("After executing move: \n" + board.toString());
    }

    private boolean isStandardMove(Location start, Location end) {
        Piece piece = board.pieceAt(start);
        Piece enemy = board.pieceAt(end);
        return piece.canMoveFrom(start, end) && !Piece.areAllies(piece, enemy);
    }

    public void move(Location start, Location end) {
        Move move = new Move(start, end);
        if (isValidMove(move)) {
            executeMove(move, board);
            currentPlayer = currentPlayer.getOpponent();
            history.add(move);
        }

        logger.debug("\n" + board.toString());
    }

    public void move(Move move) {
        move(move.start(), move.end());
    }

    boolean isValidMove(Move move) {
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
            executeMove(move, board);
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
            executeMove(move, board);
            boolean movePutPlayerInCheck = isInCheck(currentPlayer);
            board = boardBeforeMakingMove;
            if (!movePutPlayerInCheck) {
                legalMoves.add(move);
            }
        }

        logger.debug(String.format("Legal moves at %s are %s", location, legalMoves));
        return legalMoves;
    }

    public boolean isInCheckmate(Player player) {
        return isInCheck(player) && hasNoPossibleMoves(player);
    }

    public boolean isInStalemate(Player player) {
        return !isInCheck(player) && hasNoPossibleMoves(player);
    }

    public boolean isInCheck(Player player) {
        Piece king = board.pieceAt(currentKingLocation);
        for (Move move : getLegalMoves(player.getOpponent().getAlliance(), currentKingLocation)) {
            Piece potentialEnemy = board.pieceAt(move.end());
            if (Piece.areEnemies(king, potentialEnemy)) {
                for (Move enemyMove : potentialEnemy.getLegalMoves(move.end(), board)) {
                    if (enemyMove.end() == currentKingLocation) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private Collection<Move> getLegalMoves(Alliance alliance, Location location) {
        Collection<Move> legalMoves = new HashSet<>();

        Piece enemyQueen = new Queen(alliance);
        Piece enemyKing = new King(alliance);
        Piece enemyKnight = new Knight(alliance);
        Piece enemyPawn = new Pawn(alliance);

        legalMoves.addAll(enemyQueen.getLegalMoves(location, board));
        legalMoves.addAll(enemyKing.getLegalMoves(location, board));
        legalMoves.addAll(enemyKnight.getLegalMoves(location, board));
        legalMoves.addAll(enemyPawn.getLegalMoves(location, board));

        return legalMoves;
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

    private void executeMove(Move move, BoardModel board) {
        Piece piece = board.pieceAt(move.start());
        executeMove(move, board); 
        if (piece instanceof King) {
            currentKingLocation = move.end();
        }
    }

    public BoardModel getBoard() {
        return board;
    }

    public Collection<Move> getHistory() { 
        return history; 
    }

}
