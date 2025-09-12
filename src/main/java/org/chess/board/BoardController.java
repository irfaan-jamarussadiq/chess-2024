package org.chess.board;

public class BoardController {
    private final BoardModel boardModel;
    private final BoardView boardView;

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
