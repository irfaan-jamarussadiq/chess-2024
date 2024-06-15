package org.chess.chess.game;

import org.chess.chess.board.Alliance;
import org.chess.chess.board.Location;
import org.chess.chess.board.piece.Piece;

public class Player {
    private final Alliance alliance;
    private Location kingLocation;

    public Player(Alliance alliance, Location kingLocation) {
        this.alliance = alliance;
        this.kingLocation = kingLocation;
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
}
