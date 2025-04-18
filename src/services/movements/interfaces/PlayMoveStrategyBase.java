package services.movements.interfaces;

import models.Board;
import models.Square;

public interface PlayMoveStrategyBase {
    boolean playMove(Board board, Square to);
}
//