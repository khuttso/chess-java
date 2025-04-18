package models;

import services.movements.interfaces.MovementStrategyBase;
import services.movements.interfaces.PlayMoveStrategyBase;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {

    public Knight(int color, Square initSq, String img_file) throws IOException {
        super(color, initSq, img_file);
    }
    public void setStrategies(MovementStrategyBase movementStrategy, PlayMoveStrategyBase playMoveStrategyBase) {
        this.movementStrategy = movementStrategy;
        this.playMoveStrategy = playMoveStrategyBase;
    }
    @Override
    public List<Square> getLegalMoves(Board b) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();
        Square[][] board = b.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        for (int i = 2; i > -3; i--) {
            for (int k = 2; k > -3; k--) {
                if(Math.abs(i) == 2 ^ Math.abs(k) == 2) {
                    if (k != 0 && i != 0) {
                        try {
                            legalMoves.add(board[y + k][x + i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }
        }
        
        return legalMoves;
    }

}
