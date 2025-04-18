package services;

import models.Board;
import models.Piece;
import models.Square;

import java.util.ArrayList;
import java.util.List;

public class KingMovementStrategy implements MovementStrategyBase{
    private final Piece king;

    public KingMovementStrategy(Piece king) {
        this.king = king;
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<Square>();

        Square[][] currentBoard = board.getSquareArray();

        Square position = king.getPosition();

        int x = position.getXNum();
        int y = position.getYNum();

        for (int i = 0; i >= -1; i--) {
            for (int j = 1; j >= -1; j--) {
                if (i!=0 || j!=0) {
                    try {
                        if (currentBoard[y + j][x + i].isOccupied() || currentBoard[y + j][x + i].getOccupyingPiece().getColor() != king.getColor()) {
                            legalMoves.add(board.getSquareArray()[y + j][x + i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // todo - ErrorMessage
                        continue;
                    }
                }
            }
        }
        return legalMoves;
    }
}
