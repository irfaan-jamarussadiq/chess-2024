package org.chess.chess.board;

public enum Alliance {
    WHITE {
        @Override
        public int getEnPassantStartingRank() {
            return 5;
        }

        @Override
        public int getEnPassantEndingRank() {
            return 6;
        }
    }, BLACK {
        @Override
        public int getEnPassantStartingRank() {
            return 4;
        }

        @Override
        public int getEnPassantEndingRank() {
            return 3;
        }
    };

    public abstract int getEnPassantStartingRank();

    public abstract int getEnPassantEndingRank();
}
