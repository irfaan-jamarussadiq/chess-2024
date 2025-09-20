package org.chess.model.game;

import java.util.*;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.move.*;
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
        Optional<Move> move = identifyMove(start, end);
        move.ifPresent((m) -> m.execute(board));
        logger.debug("\n" + board.toString());
    }

    private Optional<Move> identifyMove(Location start, Location end) {
        if (King.isShortCastlingMove(start, end, board)) {
            return Optional.of(new ShortCastlingMove(start, end));
        } else if (King.isLongCastlingMove(start, end, board)) {
            return Optional.of(new LongCastlingMove(start, end));
        } else if (Pawn.isEnPassantMove(start, end, board)) {
            return Optional.of(new EnPassantMove(start, end));
        } else if (Pawn.isTwoSquarePawnMove(start, end, board)) {
            return Optional.of(new TwoSquarePawnMove(start, end));
        } else if (isStandardMove(start, end)) {
            return Optional.of(new StandardMove(start, end));
        } else {
            return Optional.empty();
        }
    }

    private boolean isStandardMove(Location start, Location end) {
        Piece piece = board.pieceAt(start);
        Piece enemy = board.pieceAt(end);
        return piece.canMoveFrom(start, end) && !Piece.areAllies(piece, enemy);
    }

    public void move(Location start, Location end) {
        Move move = findMoveFromPath(start, end, board);
        if (move != null && isValidMove(move)) {
            executeMove(move, board);
            currentPlayer = currentPlayer.getOpponent();
            history.add(move);
        }

        logger.debug("\n" + board.toString());
    }

    public void move(Move move) {
        move(move.getStart(), move.getEnd());
    }

    public Collection<Move> getPossibleMoves(Location location) {
        logger.info("Finding possible moves...");
        Collection<Move> possibleMoves = new HashSet<>();
        if (board.isEmpty(location)) {
            return possibleMoves;
        }

        Piece piece = board.pieceAt(location);
        for (Location possibleDestination : piece.getPossibleDestinations(location)) {
            if (possibleDestination.isWithinBounds()) {
                Optional<Move> move = identifyMove(location, possibleDestination);
                move.ifPresent((m) -> possibleMoves.add(m)); 
            }
        }

        return possibleMoves;
    }

    boolean isValidMove(Move move) {
        if (!move.isWithinBounds() || move.getStart().equals(move.getEnd()) || !move.isValid(board)) {
            return false;
        }

        Piece piece = board.pieceAt(move.getStart());
        Collection<Location> possibleDestinations = piece.getPossibleDestinations(move.getStart());

        for (Location destination : possibleDestinations) {
            if (!destination.isWithinBounds()) {
                continue;
            }

            Move candidateMove = findMoveFromPath(move.getStart(), destination, board);
            if (candidateMove != null && candidateMove.equals(move)) {
                BoardModel copy = new BoardModel(board);
                boolean hasMoved = piece.hasMoved();
                executeMove(candidateMove, board);
                boolean movePutPlayerInCheck = isInCheck(currentPlayer);
                board = copy;
                board.pieceAt(move.getStart()).setHasMoved(hasMoved);
                if (!movePutPlayerInCheck) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Move> getLegalMoves(Location location) {
        logger.info("Finding legal moves...");
        List<Move> legalMoves = new ArrayList<>();

        if (board.isEmpty(location)) {
            return legalMoves;
        }

        Piece piece = board.pieceAt(location);
        Collection<Location> possibleDestinations = piece.getPossibleDestinations(location);

        for (Location destination : possibleDestinations) {
            if (!destination.isWithinBounds()) {
                continue;
            }

            Move candidateMove = findMoveFromPath(location, destination, board);
            if (currentPlayer.isPieceAlly(piece) && candidateMove != null && candidateMove.isValid(board)) {
                BoardModel copy = new BoardModel(board);
                boolean hasMoved = !board.hasPieceAtLocationNotMoved(location); 
                executeMove(candidateMove, board);
                boolean movePutPlayerInCheck = isInCheck(currentPlayer);
                board = copy;
                Map<Location, Location> toUpdate = candidateMove.getLocationMappings(board);
                for (Location l : toUpdate.keySet()) {
                    board.pieceAt(l).setHasMoved(hasMoved);
                }
                if (!movePutPlayerInCheck) {
                    legalMoves.add(candidateMove);
                }
            }
        }

        logger.debug("Legal moves at " + location + " are " + legalMoves);
        return legalMoves;
    }

    public Move findMoveFromPath(Location start, Location end, BoardModel board) {
        Move[] possibleMoves = new Move[] {
                new TwoSquarePawnMove(start, end),
                new ShortCastlingMove(start, end),
                new EnPassantMove(start, end),
                new StandardMove(start, end)
        };

        for (Move move : possibleMoves) {
            if (move.isValid(board)) {
                return move;
            }
        }

        return null;
    }

    public boolean isInCheckmate(Player player) {
        return isInCheck(player) && hasNoPossibleMoves(player);
    }

    public boolean isInStalemate(Player player) {
        return !isInCheck(player) && hasNoPossibleMoves(player);
    }

    public boolean isInCheck(Player player) {
        Piece king = board.pieceAt(currentKingLocation);
        for (MoveRecord move : getLegalMoves(player.getOpponent().getAlliance(), currentKingLocation)) {
            Piece potentialEnemy = board.pieceAt(move.end());
            if (Piece.areEnemies(king, potentialEnemy)) {
                for (MoveRecord enemyMove : potentialEnemy.getLegalMoves(move.end(), board)) {
                    if (enemyMove.end() == currentKingLocation) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private Collection<MoveRecord> getLegalMoves(Alliance alliance, Location location) {
        Collection<MoveRecord> legalMoves = new HashSet<>();

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
        Map<Location, Piece> playerPieces = new HashMap<>();
        for (int rank = 1; rank <= BoardModel.SIZE; rank++) {
            for (int file = 1; file <= BoardModel.SIZE; file++) {
                Location location = new Location(rank, file);
                Piece piece = board.pieceAt(location);
                if (piece != null && piece.getAlliance() == player.getAlliance()) {
                    playerPieces.put(location, piece);
                }
            }
        }

        for (Map.Entry<Location, Piece> entry : playerPieces.entrySet()) {
            Location location = entry.getKey();
            Piece piece = entry.getValue();
            Collection<Location> possibleDestinations = piece.getPossibleDestinations(location);
            for (Location destination : possibleDestinations) {
                if (!destination.isWithinBounds()) {
                    continue;
                }

                Move candidateMove = findMoveFromPath(location, destination, board);
                if (candidateMove != null && isValidMove(candidateMove)
                        && piece.canMoveFrom(location, destination, board)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void executeMove(Move move, BoardModel board) {
        Piece piece = board.pieceAt(move.getStart());
        move.execute(board);
        if (piece instanceof King) {
            currentKingLocation = move.getEnd();
        }
    }

    public BoardModel getBoard() {
        return board;
    }

    public Collection<Move> getHistory() { 
        return history; 
    }

}
