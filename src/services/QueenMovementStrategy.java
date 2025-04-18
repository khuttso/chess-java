package services;

import models.Board;
import models.Piece;
import models.Square;

import java.util.ArrayList;
import java.util.List;

public class QueenMovementStrategy implements MovementStrategyBase{

    private final Piece queen;

    public QueenMovementStrategy(Piece queen) {
        this.queen = queen;
    }
    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> diagonalLegalMoves = MovesLogic.diagonalMoves(board, queen);
        List<Square> HorizontalVerticalLegalMoves = MovesLogic.getHorizontalVerticalMoves(board, queen);

        List<Square> result = new ArrayList<>();
        result.addAll(HorizontalVerticalLegalMoves);
        result.addAll(diagonalLegalMoves);

        return result;
    }
}
