module org.chess.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires junit;
    requires org.slf4j;


    opens org.chess to javafx.fxml;
    exports org.chess;
    exports org.chess.board;
    exports org.chess.piece;
    exports org.chess.game;
    opens org.chess.game to javafx.fxml;
    exports org.chess.game.move;
    opens org.chess.game.move to javafx.fxml;
}