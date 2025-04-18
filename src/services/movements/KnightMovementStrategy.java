package services.movements;

import models.Board;
import models.Piece;
import models.Square;
import services.movements.interfaces.MovementStrategyBase;

import java.util.ArrayList;
import java.util.List;
//
public class KnightMovementStrategy implements MovementStrategyBase {

    private final Piece knight;

    public KnightMovementStrategy(Piece knight) {
        this.knight = knight;
    }
    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();
        Square[][] currentBoard = board.getSquareArray();
        Square position = knight.getPosition();

        int x = position.getXNum();
        int y = position.getYNum();

        for (int i=2;  i>=-2;  i--) {
            for (int j=2;  j>=-2; j--) {
                if ((Math.abs(i) == 2) != (Math.abs(j) == 2)) {
                    if (j != 0 && i !=0) {
                        try {
                            legalMoves.add(currentBoard[y + j][x + i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            // TODO - Error Message
                            continue;
                        }
                    }
                }
            }
        }

        return legalMoves;
    }
}
