package org.chess.view.game;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

import java.util.Collection;

import org.chess.model.board.Location;
import org.chess.model.game.GameModel;
import org.chess.model.game.Move;
import org.chess.view.board.BoardViewBuilder;
import org.chess.view.board.HighlightEvent;
import org.chess.view.board.TileView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameViewBuilder implements Builder<Region> {
    private final GameModel gameModel;
    private final BoardViewBuilder boardViewBuilder;

    private static final Logger logger = LoggerFactory.getLogger(GameViewBuilder.class);

    public GameViewBuilder(GameModel gameModel, BoardViewBuilder boardViewBuilder) {
        this.gameModel = gameModel;
        this.boardViewBuilder = boardViewBuilder;
    }

    @Override
    public Region build() {
        BorderPane pane = new BorderPane();
        pane.setCenter(boardViewBuilder.build());
        pane.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                MouseEvent mouseEvent = (MouseEvent) event;
                int rank = BoardViewBuilder.SIDE_LENGTH - (int) mouseEvent.getY() / TileView.SIDE_LENGTH;
                int file = (int) mouseEvent.getX() / TileView.SIDE_LENGTH + 1;
                Location location = new Location(rank, file);
                Collection<Move> legalMoves = gameModel.getLegalMoves(location);
                gameModel.setMoveDestinations(legalMoves);
                Event highlightEvent = new Event(HighlightEvent.HIGHLIGHT_REQUEST);
                pane.fireEvent(highlightEvent);
                logger.debug("Event fired");
            }
        });

        return pane;
    }

}
