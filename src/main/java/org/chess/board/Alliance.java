package org.chess.board;

public enum Alliance {
    WHITE {
        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public int getEnPassantStartingRank() {
            return 5;
        }

        @Override
        public int getEnPassantEndingRank() {
            return 6;
        }

        @Override
        public int getPawnDirection() {
            return 1;
        }
    }, BLACK {
        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public int getEnPassantStartingRank() {
            return 4;
        }

        @Override
        public int getEnPassantEndingRank() {
            return 3;
        }

        @Override
        public int getPawnDirection() {
            return -1;
        }
    };

    public abstract boolean isWhite();

    public abstract int getEnPassantStartingRank();

    public abstract int getEnPassantEndingRank();

    public abstract int getPawnDirection();
}
