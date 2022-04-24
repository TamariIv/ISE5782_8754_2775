package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void testWriteToImage() {
        int nX = 800;
        int nY = 500;

//        Color yellowColor = new Color(java.awt.Color.YELLOW);
        Color yellowColor = new Color(255d, 255d, 0d);
        Color redColor = new Color(255d, 0d, 0d);
        ImageWriter imageWriter = new ImageWriter("yellowsubmarine", nX, nY);
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, redColor);
                }
                else {
                    imageWriter.writePixel(i, j, yellowColor);
                }
            }
        }
        imageWriter.writeToImage();
    }
}