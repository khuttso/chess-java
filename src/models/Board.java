package models;

import controllers.GameWindow;
import services.movements.*;
import services.movements.interfaces.MovementStrategyBase;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "/resources/wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "/resources/bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "/resources/wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "/resources/bknight.png";
	private static final String RESOURCES_WROOK_PNG = "/resources/wrook.png";
	private static final String RESOURCES_BROOK_PNG = "/resources/brook.png";
	private static final String RESOURCES_WKING_PNG = "/resources/wking.png";
	private static final String RESOURCES_BKING_PNG = "/resources/bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "/resources/bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "/resources/wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "/resources/wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "/resources/bpawn.png";

    // DI BLOCKS
//    private static final MovementStrategyBase bishopMovement = new BishopMovementStrategy()
	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
    
    // List of pieces and whether they are movable
    public final LinkedList<Piece> Bpieces;
    public final LinkedList<Piece> Wpieces;
    public List<Square> movable;
    
    private boolean whiteTurn;

    private Piece currPiece;
    private int currX;
    private int currY;
    
    private CheckmateDetector cmd;
    
    public Board(GameWindow g) throws IOException {
        this.g = g;
        board = new Square[8][8];
        Bpieces = new LinkedList<Piece>();
        Wpieces = new LinkedList<Piece>();
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if ((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                    this.add(board[x][y]);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                    this.add(board[x][y]);
                }
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    private void initializePieces() throws IOException {
    	
        for (int x = 0; x < 8; x++) {
            Pawn pawn1x = new Pawn(0, board[1][x], RESOURCES_BPAWN_PNG);
            pawn1x.setStrategies(new PawnMovementStrategy(pawn1x, true), new StandardPlayMoveStrategy(pawn1x));
            Pawn pawn6x =new Pawn(1, board[6][x], RESOURCES_WPAWN_PNG);
            pawn6x.setStrategies(new PawnMovementStrategy(pawn6x, true), new StandardPlayMoveStrategy(pawn6x));
            board[1][x].put(pawn1x);
            board[6][x].put(pawn6x);
        }

        Queen q73 = new Queen(1, board[7][3], RESOURCES_WQUEEN_PNG);
        Queen q03 = new Queen(0, board[0][3], RESOURCES_BQUEEN_PNG);
        q73.setStrategies(new QueenMovementStrategy(q73), new StandardPlayMoveStrategy(q73));
        q03.setStrategies(new QueenMovementStrategy(q03), new StandardPlayMoveStrategy(q03));
        board[7][3].put(q73);
        board[0][3].put(q03);
        
        King bk = new King(0, board[0][4], RESOURCES_BKING_PNG);
        bk.setStrategies(new KingMovementStrategy(bk), new StandardPlayMoveStrategy(bk));
        King wk = new King(1, board[7][4], RESOURCES_WKING_PNG);
        wk.setStrategies(new KingMovementStrategy(wk), new StandardPlayMoveStrategy(wk));
        board[0][4].put(bk);
        board[7][4].put(wk);

        Rook rook00 = new Rook(0, board[0][0], RESOURCES_BROOK_PNG);
        Rook rook07 = new Rook(0, board[0][7], RESOURCES_BROOK_PNG);
        Rook rook70 = new Rook(1, board[7][0], RESOURCES_WROOK_PNG);
        Rook rook77 = new Rook(1, board[7][7], RESOURCES_WROOK_PNG);
        rook00.setStrategies(new RookMovementStrategy(rook00), new StandardPlayMoveStrategy(rook00));
        rook07.setStrategies(new RookMovementStrategy(rook07), new StandardPlayMoveStrategy(rook07));
        rook70.setStrategies(new RookMovementStrategy(rook70), new StandardPlayMoveStrategy(rook70));
        rook77.setStrategies(new RookMovementStrategy(rook77), new StandardPlayMoveStrategy(rook77));
        board[0][0].put(rook00);
        board[0][7].put(rook07);
        board[7][0].put(rook70);
        board[7][7].put(rook77);

        Knight n01 = new Knight(0, board[0][1], RESOURCES_BKNIGHT_PNG);
        Knight n06 = new Knight(0, board[0][6], RESOURCES_BKNIGHT_PNG);
        Knight n71 = new Knight(1, board[7][1], RESOURCES_WKNIGHT_PNG);
        Knight n76 = new Knight(1, board[7][6], RESOURCES_WKNIGHT_PNG);
        n01.setStrategies(new KnightMovementStrategy(n01), new StandardPlayMoveStrategy(n01));
        n06.setStrategies(new KnightMovementStrategy(n06), new StandardPlayMoveStrategy(n06));
        n71.setStrategies(new KnightMovementStrategy(n71), new StandardPlayMoveStrategy(n71));
        n76.setStrategies(new KnightMovementStrategy(n76), new StandardPlayMoveStrategy(n76));
        board[0][1].put(n01);
        board[0][6].put(n06);
        board[7][1].put(n71);
        board[7][6].put(n76);

        Bishop b02 = new Bishop(0, board[0][2], RESOURCES_BBISHOP_PNG);
        Bishop b05 = new Bishop(0, board[0][5], RESOURCES_BBISHOP_PNG);
        Bishop b72 = new Bishop(1, board[7][2], RESOURCES_WBISHOP_PNG);
        Bishop b75 = new Bishop(1, board[7][5], RESOURCES_WBISHOP_PNG);
        b02.setStrategies(new BishopMovementStrategy(b02), new StandardPlayMoveStrategy(b02));
        b05.setStrategies(new BishopMovementStrategy(b05), new StandardPlayMoveStrategy(b05));
        b72.setStrategies(new BishopMovementStrategy(b72), new StandardPlayMoveStrategy(b72));
        b75.setStrategies(new BishopMovementStrategy(b75), new StandardPlayMoveStrategy(b75));
        board[0][2].put(b02);
        board[0][5].put(b05);
        board[7][2].put(b72);
        board[7][5].put(b75);
        
        
        for(int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                Bpieces.add(board[y][x].getOccupyingPiece());
                Wpieces.add(board[7-y][x].getOccupyingPiece());
            }
        }
        
        cmd = new CheckmateDetector(this, Wpieces, Bpieces, wk, bk);
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public void capturePiece(Square destination, Piece piece) {
        // TODO - Implementation
    }
    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
        // super.paintComponent(g);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[y][x];
                sq.paintComponent(g);
            }
        }

        if (currPiece != null) {
            if ((currPiece.getColor() == 1 && whiteTurn)
                    || (currPiece.getColor() == 0 && !whiteTurn)) {
                final Image i = currPiece.getImage();
                g.drawImage(i, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (currPiece != null) {
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;

            List<Square> legalMoves = currPiece.getLegalMoves(this);
            movable = cmd.getAllowableSquares(whiteTurn);

            if (legalMoves.contains(sq) && movable.contains(sq)
                    && cmd.testMove(currPiece, sq)) {
                sq.setDisplay(true);
                currPiece.move(sq);
                cmd.update();

                if (cmd.blackCheckMated()) {
                    currPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    g.checkmateOccurred(0);
                } else if (cmd.whiteCheckMated()) {
                    currPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    g.checkmateOccurred(1);
                } else {
                    currPiece = null;
                    whiteTurn = !whiteTurn;
                    movable = cmd.getAllowableSquares(whiteTurn);
                }

            } else {
                currPiece.getPosition().setDisplay(true);
                currPiece = null;
            }
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        repaint();
    }

    // Irrelevant methods, do nothing for these mouse behaviors
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}