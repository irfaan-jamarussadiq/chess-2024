# Chess

Chess program written in Java. Graphics made with JavaFX.

## Design

### Board

The Board class can add, remove, and locate pieces on the chess board. It uses a one dimensional array of Tile objects as the data structure. The Board class can also move pieces on the board. 
No validation is done on the move besides whether the start and end locations are within the bounds of the board. Other classes will handle the validation.

### Game

The Game class is responsible for move validation, including determining whether a move is legal by how the piece should move, whether the square is blocked by another piece, and whether the move would put the player in check.
