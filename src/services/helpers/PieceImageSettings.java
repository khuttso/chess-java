package services.helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PieceImageSettings {

    public static boolean successfulAttempt() {
        // TODO - For several attempt for loading
        return false;
    }
// 
    public static BufferedImage load(String imagePath) throws IOException {
        return ImageIO.read(PieceImageSettings.class.getResource(imagePath));
    }
}
