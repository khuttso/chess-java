package services.movements;

import models.Board;
import models.Piece;
import models.Square;
import services.movements.interfaces.PlayMoveStrategyBase;

public class StandardPlayMoveStrategy implements PlayMoveStrategyBase {
    private final Piece piece;

    public StandardPlayMoveStrategy(Piece piece) {
        this.piece = piece;
    }
//
    @Override
    public boolean playMove(Board board, Square to) {
        Piece pieceOnNewSquare = to.getOccupyingPiece();
        if (pieceOnNewSquare != null) {
            if (pieceOnNewSquare.getColor() == piece.getColor()) {
                return false;
            }
            else {
                board.capturePiece(to, piece);
            }
        }

        moveHelper(to, piece);
        return true;
    }

    private void moveHelper(Square destination, Piece Piece) {
        piece.getPosition().removePiece();
        piece.setPosition(destination);
        piece.getPosition().put(piece);
    }
}
