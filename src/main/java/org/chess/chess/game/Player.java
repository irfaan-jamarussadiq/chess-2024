package org.chess.chess.game;

import org.chess.chess.board.Alliance;
import org.chess.chess.board.BoardModel;
import org.chess.chess.board.Location;

public class Player {
    private final Alliance alliance;
    private Location kingLocation;

    public Player(Alliance alliance, Location kingLocation) {
        this.alliance = alliance;
        this.kingLocation = kingLocation;
    }

    public boolean isInCheck(BoardModel board) {
        // TODO: Implement this method!
        return false;
//        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public boolean isInCheckmate(BoardModel board) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public boolean isInStalemate(BoardModel board) {
        throw new UnsupportedOperationException("Not implemented yet!");
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
