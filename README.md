# Chess

Chess program written in Java. Graphics made with JavaFX.

## Design

### Board

The Board class can add, remove, and locate pieces on the chess board. It uses a one dimensional array of Tile objects as the data structure. The Board class can also move pieces on the board. No validation is done on the move besides whether the start and end locations are within the bounds of the board. Other classes will handle the validation.
