package services.movements;

import models.Board;
import models.Piece;
import models.Square;
import services.helpers.MovesLogic;
import services.movements.interfaces.MovementStrategyBase;

import java.util.List;

public class RookMovementStrategy implements MovementStrategyBase {
    private final Piece rook;
//
    public RookMovementStrategy(Piece rook) {
        this.rook = rook;
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        return MovesLogic.getHorizontalVerticalMoves(board, rook);
    }
}
