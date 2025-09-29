package org.chess.model.board;

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

        @Override
        public int getStartingPieceRank() {
            return 1;
        }

        @Override
        public Alliance getEnemy() {
            return Alliance.BLACK;
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

        @Override
        public int getStartingPieceRank() {
            return BoardModel.SIZE;
        }

        @Override
        public Alliance getEnemy() {
            return Alliance.WHITE;
        }
    };

    public abstract boolean isWhite();

    public abstract int getEnPassantStartingRank();

    public abstract int getEnPassantEndingRank();

    public abstract int getPawnDirection();

    public abstract int getStartingPieceRank();

    public abstract Alliance getEnemy();

    public int getStartingPawnRank() {
        return getStartingPieceRank() + getPawnDirection();
    }

}
