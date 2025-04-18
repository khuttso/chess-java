package services;

import models.Board;
import models.Square;
import java.util.List;

public interface MovementStrategyBase {
    public List<Square> getLegalMoves(Board board);
}
