package models;

import services.helpers.PieceImageSettings;
import services.movements.interfaces.MovementStrategyBase;
import services.movements.interfaces.PlayMoveStrategyBase;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class Piece {
    private int color;
    private Square currentSquare;
    private BufferedImage img;
    protected MovementStrategyBase movementStrategy;
    protected PlayMoveStrategyBase playMoveStrategy;
    public Piece(int color, Square initSq, String img_file) throws IOException {
        this.color = color;
        this.currentSquare = initSq;
        this.img = PieceImageSettings.load(img_file);
    }
    public Piece(MovementStrategyBase movementStrategy, PlayMoveStrategyBase playMoveStrategy) throws IOException {
        this.movementStrategy = movementStrategy;
        this.playMoveStrategy = playMoveStrategy;
    }
    
    public boolean move(Square fin) {
        Piece occup = fin.getOccupyingPiece();
        
        if (occup != null) {
            if (occup.getColor() == this.color) return false;
            else fin.capture(this);
        }
        
        currentSquare.removePiece();
        this.currentSquare = fin;
        currentSquare.put(this);
        return true;
    }


    public Square getPosition() {
        return currentSquare;
    }
    
    public void setPosition(Square sq) {
        this.currentSquare = sq;
    }
    
    public int getColor() {
        return color;
    }
    
    public Image getImage() {
        return img;
    }
    
    public void draw(Graphics g) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }
    

    // No implementation, to be implemented by each subclass
    public List<Square> getLegalMoves(Board b) {
        return this.movementStrategy.getLegalMoves(b);
    }
}