package models;

import services.movements.interfaces.MovementStrategyBase;
import services.movements.interfaces.PlayMoveStrategyBase;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Rook extends Piece {

    public Rook(int color, Square initSq, String img_file) throws IOException {
        super(color, initSq, img_file);
    }

    public void setStrategies(MovementStrategyBase movementStrategy, PlayMoveStrategyBase playMoveStrategyBase) throws IOException {
        this.movementStrategy = movementStrategy;
        this.playMoveStrategy = playMoveStrategyBase;
    }
}
