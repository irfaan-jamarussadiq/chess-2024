module org.chess.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens org.chess.chess to javafx.fxml;
    exports org.chess.chess;
    exports org.chess.chess.board;
}