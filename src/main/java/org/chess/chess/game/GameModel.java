package org.chess.chess.game;

import org.chess.chess.board.Alliance;
import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.King;
import org.chess.chess.board.piece.Pawn;
import org.chess.chess.board.piece.Piece;
import org.chess.chess.board.piece.Rook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameModel {
    public final Player white = new Player(Alliance.WHITE, new Location(1, 5));
    public final Player black = new Player(Alliance.BLACK, new Location(8, 5));
    private Player currentPlayer;
    private BoardModel board;

    public GameModel() {
        this.currentPlayer = white;
        this.board = new BoardModel();
    }

    public void move(Location start, Location end) {
        Move move = new Move(start, end);
        if (isValidMove(move)) {
            executeMove(move, board);
            currentPlayer = getNextPlayer();
        }
    }

    boolean isValidMove(Move move) {
        if (!move.isWithinBounds()) {
            return false;
        }

        Piece piece = board.pieceAt(move.start());
        List<Move> candidateMoves = piece.getCandidateMoves(move.start());
        for (Move candidateMove : candidateMoves) {
            if (candidateMove.isWithinBounds() && candidateMove.equals(move)) {
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
        Piece piece = board.pieceAt(location);
        List<Move> candidateMoves = piece.getCandidateMoves(location);
        List<Move> legalMoves = new ArrayList<>();

        for (Move candidateMove : candidateMoves) {
            if (candidateMove.isWithinBounds() && piece.canMoveFrom(location, candidateMove.end(), board)) {
                BoardSnapshot boardSnapshot = new BoardSnapshot(board, currentPlayer);
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

    public boolean isInCheck(Player player) {
        Piece king = board.pieceAt(player.getKingLocation());
        List<Move> movesAtKingLocation = MoveListHelpers.getAllPossibleMoves(player.getKingLocation(), BoardModel.SIZE);
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
            List<Move> candidateMoves = piece.getCandidateMoves(location);
            for (Move move : candidateMoves) {
                if (isValidMove(move) && piece.canMoveFrom(move.end(), location, board)) {
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
        Piece piece = board.pieceAt(move.start());

        if (isCastlingMove(move, board)) {
            castle(move.start(), move.end());
        } else if (isEnPassantMove(move, board)) {
            enPassant(move.start(), move.end());
        } else {
            board.movePiece(move.start(), move.end());
        }

        if (piece instanceof King) {
            currentPlayer.setKingLocation(move.end());
        }
    }

    private void castle(Location kingStart, Location kingEnd) {
        board.movePiece(kingStart, kingEnd);

        Location rookStart, rookEnd;
        if (kingStart.file() > kingEnd.file()) {
            // Long castling
            rookStart = new Location(kingStart.rank(), 1);
            rookEnd = new Location(kingStart.rank(), 4);
        } else {
            // Short castling
            rookStart = new Location(kingStart.rank(), 8);
            rookEnd = new Location(kingStart.rank(), 6);
        }

        board.movePiece(rookStart, rookEnd);
    }

    private boolean isCastlingMove(Move move, BoardModel board) {
        Piece king = board.pieceAt(move.start());
        if (!(king instanceof King) || board.pieceAt(move.end()) == null) {
            return false;
        }

        if (move.start().file() != 5 || move.start().rank() != move.end().rank()) {
            return false;
        }

        if (king.getAlliance() == Alliance.WHITE && move.start().rank() != 1) {
            return false;
        }

        if (king.getAlliance() == Alliance.BLACK && move.start().rank() != 8) {
            return false;
        }

        if (move.end().file() == 3) {
            Piece rook = board.pieceAt(new Location(move.start().rank(), 1));
            return rook instanceof Rook
                    && board.isEmpty(new Location(move.start().rank(), 2))
                    && board.isEmpty(new Location(move.start().rank(), 3))
                    && board.isEmpty(new Location(move.start().rank(), 4));
        } else if (move.end().file() == 7) {
            Piece rook = board.pieceAt(new Location(move.start().rank(), 8));
            return rook instanceof Rook
                    && board.isEmpty(new Location(move.start().rank(), 6))
                    && board.isEmpty(new Location(move.start().rank(), 7));
        }

        return false;
    }

    private void enPassant(Location pawnStart, Location pawnEnd) {
        Location capturedPawnLocation = new Location(pawnStart.rank(), pawnEnd.file());
        board.movePiece(capturedPawnLocation, pawnEnd);
        board.movePiece(pawnStart, pawnEnd);
    }

    private boolean isEnPassantMove(Move move, BoardModel board) {
        Piece pawn = board.pieceAt(move.start());
        if (!(pawn instanceof Pawn) || board.pieceAt(move.end()) == null) {
            return false;
        }

        Piece enemyPawn = board.pieceAt(move.start());
        if (!pawn.isEnemyOf(enemyPawn) || !(enemyPawn instanceof Pawn)) {
            return false;
        }

        return Math.abs(move.start().file() - move.end().file()) == 1
                && move.start().rank() == pawn.getAlliance().getEnPassantStartingRank()
                && move.end().rank() == pawn.getAlliance().getEnPassantEndingRank();
    }

    public Player getNextPlayer() {
        return (currentPlayer == white) ? black : white;
    }

    public BoardModel getBoard() {
        return board;
    }
}
