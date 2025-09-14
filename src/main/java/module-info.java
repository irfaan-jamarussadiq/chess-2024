module org.chess.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires junit;
    requires org.slf4j;


    opens org.chess to javafx.fxml;
    exports org.chess;
    exports org.chess.model.board;
    exports org.chess.model.piece;
    exports org.chess.model.game;
    opens org.chess.model.game to javafx.fxml;
    exports org.chess.model.game.move;
    opens org.chess.model.game.move to javafx.fxml;
}