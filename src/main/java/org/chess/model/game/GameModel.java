package org.chess.model.game;

import java.util.*;

import org.chess.model.board.Alliance;
import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;
import org.chess.model.game.move.*;
import org.chess.model.piece.King;
import org.chess.model.piece.Piece;
import org.chess.model.piece.Rook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameModel {
    private Collection<Move> history;
    private Player currentPlayer;
    private BoardModel board;

    private static final Logger logger = LoggerFactory.getLogger(GameModel.class);

    public GameModel(BoardModel board) {
        this.currentPlayer = Player.getPlayer(Alliance.WHITE);
        this.board = board;
        this.history = new Stack<>();
    }

    public GameModel() {
        this(new BoardModel());
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

    public void executeMove(Location start, Location end) {
        Optional<Move> move = identifyMove(start, end);
        move.ifPresent((m) -> m.execute(board));
    }

    private Optional<Move> identifyMove(Location start, Location end) {
        if (isShortCastlingMove(start, end)) {
            return Optional.of(new CastlingMove(start, end));
        } else if (isLongCastlingMove(start, end)) {
            return Optional.of(new CastlingMove(start, end));
        } else if (isEnPassantMove(start, end)) {
            return Optional.of(new EnPassantMove(start, end));
        } else if (isTwoSquarePawnMove(start, end)) {
            return Optional.of(new TwoSquarePawnMove(start, end));
        } else if (isStandardMove(start, end)) {
            return Optional.of(new StandardMove(start, end));
        } else {
            return Optional.empty();
        }
    }

    private boolean isShortCastlingMove(Location start, Location end) {
		Piece king = board.pieceAt(start);
		Piece rook = board.pieceAt(start.offset(0, 3));
		int rankDiff = end.rank() - start.rank(); 

		return rankDiff == 0 
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

    private boolean isLongCastlingMove(Location start, Location end) {
		Piece king = board.pieceAt(start);
		Piece rook = board.pieceAt(start.offset(0, -3));
		int rankDiff = end.rank() - start.rank(); 

		return rankDiff == 0 
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

    private boolean isEnPassantMove(Location start, Location end) {
		int rankDiff = end.rank() - start.rank(); 
		int fileDiff = end.file() - start.file(); 

        Piece pawn = board.pieceAt(start);
		Piece enemyPawn = board.pieceAt(end.offset(0, fileDiff));	
		int enPassantRank = pawn.getAlliance().isWhite() ? 6 : 3; 

		return rankDiff == pawn.getAlliance().getPawnDirection() 
			&& Math.abs(fileDiff) == 1 
			&& start.rank() == enPassantRank 
			&& Piece.areEnemies(pawn, enemyPawn) 
			&& board.isEmpty(end.offset(0, fileDiff));
    }

    private boolean isTwoSquarePawnMove(Location start, Location end) {
        Piece pawn = board.pieceAt(start);
		int pawnDirection = 2 * pawn.getAlliance().getPawnDirection();
		return start.rank() == end.rank() 
            && start.rank() == pawn.getAlliance().getStartingPieceRank() 
			&& end.equals(start.offset(pawnDirection, 0))
			&& board.isEmpty(end);
    }

    private boolean isStandardMove(Location start, Location end) {
        Piece piece = board.pieceAt(start);
        Piece enemy = board.pieceAt(end);
        return piece.canMoveFrom(start, end) && !Piece.areAllies(piece, enemy);
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
                boolean hasMoved = piece.hasMoved();
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
                new CastlingMove(start, end),
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

    public boolean isInCheck(Player player) {
        Piece king = board.pieceAt(player.getKingLocation());
        List<Path> movesAtKingLocation = PathHelpers.getAllPossiblePaths(player.getKingLocation(), BoardModel.SIZE);
        for (Path path : movesAtKingLocation) {
            if (path.isWithinBounds()) {
                Piece potentialEnemy = board.pieceAt(path.end());
                if (Piece.areEnemies(king, potentialEnemy)
                        && potentialEnemy.canMoveFrom(path.end(), path.start(), board)) {
                    return true;
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
            currentPlayer.setKingLocation(move.getEnd());
        }
    }

    public BoardModel getBoard() {
        return board;
    }

    public Collection<Move> getHistory() { 
        return history; 
    }

}
