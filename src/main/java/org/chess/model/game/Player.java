package org.chess.model.game;

import org.chess.model.board.Alliance;
import org.chess.model.piece.Piece;

public class Player {
    private final static Player WHITE = new Player(Alliance.WHITE); 
    private final static Player BLACK = new Player(Alliance.BLACK); 

    private final Alliance alliance;

    private Player(Alliance alliance) {
        this.alliance = alliance;
    }

    public boolean isPieceAlly(Piece piece) {
        return piece.getAlliance() == alliance;
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public static Player getPlayer(Alliance alliance) {
        return alliance.isWhite() ? WHITE : BLACK;
    }

    public Player getOpponent() {
        return alliance.isWhite() ? BLACK : WHITE;
    }

    public boolean hasPiece(Piece piece) {
        return piece.getAlliance() == alliance;
    }
}
