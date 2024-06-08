module org.chess.chess {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.chess.chess to javafx.fxml;
    exports org.chess.chess;
}