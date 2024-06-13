package org.chess.chess.board;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.chess.chess.board.piece.Piece;
import org.chess.chess.game.Move;

import java.util.List;

public class BoardController {
    private final BoardModel boardModel;
    private final BoardView boardView;
    private Piece selectedPiece;

    public BoardController() {
        this.boardModel = new BoardModel();
        this.boardView = new BoardView();
    }

    public void move(Location start, Location end) {
        boardModel.movePiece(start, end);
        boardView.move(start, end);
    }

    public BoardView getBoardView() {
        return boardView;
    }
}
