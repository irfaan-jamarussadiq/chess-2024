module org.chess.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.slf4j;


    opens org.chess.chess to javafx.fxml;
    exports org.chess.chess;
    exports org.chess.chess.board;
    exports org.chess.chess.board.piece;
    exports org.chess.chess.game;
    opens org.chess.chess.game to javafx.fxml;
}