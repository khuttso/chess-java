package models;

import services.movements.interfaces.MovementStrategyBase;
import services.movements.interfaces.PlayMoveStrategyBase;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class King extends Piece {

    public King(int color, Square initSq, String img_file) throws IOException {
        super(color, initSq, img_file);
    }

    public void setStrategies(MovementStrategyBase movementStrategy, PlayMoveStrategyBase playMoveStrategyBase) throws IOException {
        this.movementStrategy = movementStrategy;
        this.playMoveStrategy = playMoveStrategyBase;
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
LinkedList<Square> legalMoves = new LinkedList<Square>();
        
        Square[][] board = b.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if(!(i == 0 && k == 0)) {
                    try {
                        if(!board[y + k][x + i].isOccupied() || 
                                board[y + k][x + i].getOccupyingPiece().getColor() 
                                != this.getColor()) {
                            legalMoves.add(board[y + k][x + i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }
        
        return legalMoves;
    }

}
