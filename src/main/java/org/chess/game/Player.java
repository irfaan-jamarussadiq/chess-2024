package org.chess.game;

import org.chess.board.Alliance;
import org.chess.board.Location;
import org.chess.piece.Piece;

public class Player {
    private final static Player WHITE = new Player(Alliance.WHITE); 
    private final static Player BLACK = new Player(Alliance.BLACK); 

    private final Alliance alliance;
    private Location kingLocation;

    private Player(Alliance alliance) {
        this.alliance = alliance;
        this.kingLocation = new Location(alliance.isWhite() ? 1 : 8, 5);
    }

    public boolean isPieceAlly(Piece piece) {
        return piece.getAlliance() == alliance;
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

    public static Player getPlayer(Alliance alliance) {
        return alliance.isWhite() ? WHITE : BLACK;
    }

    public Player getOpponent() {
        return alliance.isWhite() ? BLACK : WHITE;
    }
}
