//package services.movements;
//
//import models.Board;
//import models.Piece;
//import models.Square;
//import services.movements.interfaces.MovementStrategyBase;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PawnMovementStrategy implements MovementStrategyBase {
//    private final Piece pawn;
//    private boolean isStartingPosition;
//
//    public PawnMovementStrategy(Piece pawn, boolean isStartingPosition) {
//        this.pawn = pawn;
//        this.isStartingPosition = isStartingPosition;
//    }
////
//
//
//    @Override
//    public List<Square> getLegalMoves(Board board) {
//        List<Square> legalMoves = new ArrayList<>();
//
//        Square[][] currentBoard = board.getSquareArray();
//        Square position = pawn.getPosition();
//        int x = position.getXNum();
//        int y = position.getYNum();
//        int color = pawn.getColor();
//
//        // BLACK
//        if (color == 2 && !isStartingPosition) {
//            if (currentBoard[y+2][x].isOccupied()) {
//                legalMoves.add(currentBoard[y+2][x]);
//                legalMoves.add(currentBoard[y+1][x]);
//            }
//        }
//
//        if (color == 2 && y+1 < 8) {
//            if (currentBoard[y+1][x].isOccupied()) {
//                legalMoves.add(currentBoard[y+1][x]);
//            }
//        }
//
//        if (color == 2 && x-1 < 8 && y+1 < 8) {
//            if (!currentBoard[y+1][x-1].isOccupied()) {
//                legalMoves.add(currentBoard[y+1][x-1]);
//            }
//        }
//
//        // WHITE
//        if (color == 1 && !isStartingPosition) {
//            if (!currentBoard[y-2][x].isOccupied()) {
//                legalMoves.add(currentBoard[y-2][x]);
//            }
//        }
//
//        if (color == 1 && y-1 >= 0) {
//            if (!currentBoard[y-1][x].isOccupied()) {
//                legalMoves.add(currentBoard[y-1][x]);
//            }
//        }
//
//        if (color == 1 && x+1 < 8 && y-1 >= 0) {
//            if (currentBoard[y-1][x+1].isOccupied()) {
//                legalMoves.add(currentBoard[y-1][x+1]);
//            }
//        }
//
//        if (color == 1 && y-1 >= 0) {
//            if (currentBoard[y-1][x-1].isOccupied()) {
//                legalMoves.add(currentBoard[y-1][x-1]);
//            }
//        }
//
//        return legalMoves;
//    }
//}


package services.movements;

import models.Board;

import models.Piece;
import models.Square;
import services.movements.interfaces.MovementStrategyBase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PawnMovementStrategy implements MovementStrategyBase {
    public final Piece pawn;
    public boolean wasMoved;
    public PawnMovementStrategy(Piece piece, boolean wasMoved){
        this.pawn=piece;
        this.wasMoved=wasMoved;
    }
    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new ArrayList<>();
        Square[][] squares = board.getSquareArray();
        int x = this.pawn.getPosition().getXNum();
        int y = this.pawn.getPosition().getYNum();
        int color = this.pawn.getColor();

        // Determine movement direction based on color
        int forwardDir = (color == 0) ? -1 : 1;

        // Forward moves
        addForwardMoves(legalMoves, squares, x, y, forwardDir);

        // Capture moves
        addCaptureMoves(legalMoves, squares, x, y, forwardDir, color);

        // En passant (would need board to track last move)
        // addEnPassantMoves(legalMoves, board, x, y, forwardDir, color);

        return legalMoves;
    }

    private void addForwardMoves(List<Square> legalMoves, Square[][] squares,
                                 int x, int y, int forwardDir) {
        // Single move forward
        int newY = y + forwardDir;
        if (isValidSquare(x, newY) && !squares[newY][x].isOccupied()) {
            legalMoves.add(squares[newY][x]);

            // Double move from starting position
            if (!wasMoved) {
                newY = y + 2 * forwardDir;
                if (isValidSquare(x, newY) && !squares[newY][x].isOccupied()) {
                    legalMoves.add(squares[newY][x]);
                }
            }
        }
    }

    private void addCaptureMoves(List<Square> legalMoves, Square[][] squares,
                                 int x, int y, int forwardDir, int color) {
        // Diagonal captures
        int[] captureXs = {x - 1, x + 1};
        int newY = y + forwardDir;

        for (int captureX : captureXs) {
            if (isValidSquare(captureX, newY)) {
                Square target = squares[newY][captureX];
                if (target.isOccupied() && target.getOccupyingPiece().getColor() != color) {
                    legalMoves.add(target);
                }
                // Here you could also check en passant
            }
        }
    }

    private boolean isValidSquare(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
}
