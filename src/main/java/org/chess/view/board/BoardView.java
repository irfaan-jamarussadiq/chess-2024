package org.chess.view.board;

import java.util.Collection;

import org.chess.model.board.BoardModel;
import org.chess.model.board.Location;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BoardView extends GridPane {    
    private static final Color LIGHT_SQUARE = Color.rgb(157, 172, 255);
    private static final Color DARK_SQUARE = Color.rgb(111, 115, 210);

    public BoardView(BoardModel boardModel) {
        for (int rank = 1; rank <= BoardModel.SIZE; rank++) {
            for (int file = 1; file <= BoardModel.SIZE; file++) {
                Location location = new Location(rank, file);
                Color color = ((rank + file) % 2 == 0) ? LIGHT_SQUARE : DARK_SQUARE;
                TileView tileView = new TileView(boardModel.piecePropertyAt(location), color);
                this.add(tileView, file, BoardModel.SIZE - rank);
            }
        }
    }

    public void highlightSquares(Collection<Location> locations) {
        for (Location location : locations) {
            TileView tileView = (TileView) getNodeFromGridPane(this, BoardModel.SIZE - location.rank(), location.file());
            tileView.highlight();
        }
    }

    public void resetSquares(Collection<Location> locations) {
        for (Location location : locations) {
            TileView tileView = (TileView) getNodeFromGridPane(this, BoardModel.SIZE - location.rank(), location.file());
            tileView.reset();
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }

        return null;
    }
}
