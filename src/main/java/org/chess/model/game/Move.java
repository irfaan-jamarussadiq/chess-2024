package org.chess.model.game;

import org.chess.model.board.Location;

public record Move(Location start, Location end) {
   public boolean isWithinBounds() {
    return start.isWithinBounds() && end.isWithinBounds();
   } 
}
