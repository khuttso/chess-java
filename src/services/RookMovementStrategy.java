package services;

import models.Board;
import models.Piece;
import models.Square;

import java.util.List;

public class RookMovementStrategy implements MovementStrategyBase {
    private final Piece rook;

    public RookMovementStrategy(Piece rook) {
        this.rook = rook;
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        return MovesLogic.getHorizontalVerticalMoves(board, rook);
    }
}
