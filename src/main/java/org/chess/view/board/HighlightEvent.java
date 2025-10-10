package org.chess.view.board;

import javafx.event.Event;
import javafx.event.EventType;
import org.chess.model.board.Location;
import java.util.Collection;

public class HighlightEvent extends Event {
    public static final EventType<HighlightEvent> HIGHLIGHT_REQUEST =
        new EventType<>(Event.ANY, "HIGHLIGHT_REQUEST");

    private final Collection<Location> locations;

    public HighlightEvent(Collection<Location> locations) {
        super(HIGHLIGHT_REQUEST);
        this.locations = locations;
    }

    public Collection<Location> getLocations() {
        return locations;
    }
}
