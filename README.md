# Chess Game Application

This project implements a chess game with a graphical user interface (GUI) using JavaFX.  The game logic handles piece movement, turn-based gameplay, and check/checkmate detection.


## Features

*   **Graphical User Interface (GUI):**  A visually appealing chessboard is displayed using JavaFX.
*   **Piece Movement:**  All standard chess piece movements are implemented, including special moves like castling and en passant.
*   **Game Logic:**  The application accurately enforces chess rules, including check, checkmate, and stalemate detection.
*   **Move History:** A list of moves is displayed on the right side of the board.
*   **Turn-Based Gameplay:**  The game alternates between white and black players.


## Usage

1.  Run the `ChessApplication` class.
2.  Click on a piece to select it.  Possible moves for the selected piece will be highlighted.
3.  Click on a highlighted square to move the piece.
4.  The game will continue until checkmate or stalemate is reached.


## Installation

1.  Ensure you have Java Development Kit (JDK) 8 or higher installed.
2.  Clone the repository: `git clone <repository_url>`
3.  Open the project in an IDE such as IntelliJ IDEA or Eclipse.
4.  Build the project and run the `ChessApplication`.


## Technologies Used

*   **Java:** The primary programming language for the application's logic.
*   **JavaFX:** Used to create the graphical user interface (GUI).
*   **JUnit:**  Used for writing unit tests.
*   **SLF4j:** Used for logging (currently using `simple-logger`).


## Testing

Unit tests are written using JUnit and are located in the `src/test/java` directory.  These tests cover various aspects of the game logic, including piece movement and check/checkmate detection.  To run the tests, use your IDE's built-in testing framework or a command-line tool like Maven or Gradle.


## Dependencies

The project uses the following dependencies:

*   JavaFX (included in the project)
*   JUnit (included in the project)
*   SLF4j and Simple Logger (included in the project)


## Contributing

Contributions are welcome! Please feel free to open issues or submit pull requests.  Ensure that all code changes are well-documented and thoroughly tested.




*README.md was made with [Etchr](https://etchr.dev)*