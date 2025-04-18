package services;

import models.Board;
import models.Piece;
import models.Square;

import java.util.List;

public class BishopMovementStrategy implements MovementStrategyBase {

    private final Piece bishop;

    public BishopMovementStrategy(Piece bishop) {
        this.bishop = bishop;
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        return MovesLogic.diagonalMoves(board, bishop);
    }
}
