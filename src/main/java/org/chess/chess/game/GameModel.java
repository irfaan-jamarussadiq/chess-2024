package org.chess.chess.game;

import org.chess.chess.board.Alliance;
import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.King;
import org.chess.chess.board.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameModel {
    public final Player white = new Player(Alliance.WHITE, new Location(1, 5));
    public final Player black = new Player(Alliance.BLACK, new Location(8, 5));
    private Player currentPlayer;
    private BoardModel board;

    public GameModel(BoardModel board) {
        this.currentPlayer = white;
        this.board = board;
    }

    public GameModel() {
        this(new BoardModel());
    }

    public void move(Location start, Location end) {
        Move move = findMoveFromPath(new Path(start, end), board);
        if (move != null && isValidMove(move)) {
            executeMove(move, board);
            currentPlayer = getNextPlayer();
        }
    }

    boolean isValidMove(Move move) {
        if (!move.isWithinBounds() || move.getStart().equals(move.getEnd()) || !move.isValid(board)) {
            return false;
        }

        Piece piece = board.pieceAt(move.getStart());
        List<Path> candidatePaths = piece.getCandidatePaths(move.getStart());
        for (Path path : candidatePaths) {
            if (!path.isWithinBounds()) {
                continue;
            }

            Move candidateMove = findMoveFromPath(path, board);
            if (candidateMove != null && candidateMove.equals(move)) {
                BoardSnapshot boardSnapshot = new BoardSnapshot(board, currentPlayer);
                executeMove(candidateMove, board);
                boolean movePutPlayerInCheck = isInCheck(currentPlayer);
                restoreFromMemento(boardSnapshot);
                if (!movePutPlayerInCheck) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Move> getLegalMoves(Location location) {
        List<Move> legalMoves = new ArrayList<>();

        if (board.isEmpty(location)) {
            return legalMoves;
        }

        Piece piece = board.pieceAt(location);
        List<Path> candidatePaths = piece.getCandidatePaths(location);

        for (Path path : candidatePaths) {
            if (currentPlayer.isPieceAlly(piece) && piece.canMoveFrom(location, path.end(), board)) {
                BoardSnapshot boardSnapshot = new BoardSnapshot(board, currentPlayer);
                Move candidateMove = findMoveFromPath(path, board);
                if (candidateMove == null) {
                    continue;
                }
                executeMove(candidateMove, board);
                boolean movePutPlayerInCheck = isInCheck(currentPlayer);
                restoreFromMemento(boardSnapshot);
                if (!movePutPlayerInCheck) {
                    legalMoves.add(candidateMove);
                }
            }
        }

        return legalMoves;
    }

    private Move findMoveFromPath(Path path, BoardModel board) {
        Move[] possibleMoves = new Move[] {
                new NormalMove(path.start(), path.end()),
                new EnPassantMove(path.start(), path.end()),
                new CastlingMove(path.start(), path.end())
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
                if (king.isEnemyOf(potentialEnemy)
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
            List<Path> candidatePaths = piece.getCandidatePaths(location);
            for (Path path : candidatePaths) {
                if (!path.isWithinBounds()) {
                    continue;
                }

                Move candidateMove = findMoveFromPath(path, board);
                if (candidateMove != null && isValidMove(candidateMove)
                        && piece.canMoveFrom(path.start(), location, board)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void restoreFromMemento(BoardSnapshot boardSnapshot) {
        this.board = boardSnapshot.board();
        this.currentPlayer.setKingLocation(boardSnapshot.getPlayer().getKingLocation());
    }

    private void executeMove(Move move, BoardModel board) {
        Piece piece = board.pieceAt(move.getStart());
        move.execute(board);
        if (piece instanceof King) {
            currentPlayer.setKingLocation(move.getEnd());
        }
    }

    public Player getNextPlayer() {
        return (currentPlayer == white) ? black : white;
    }

    public BoardModel getBoard() {
        return board;
    }
}
