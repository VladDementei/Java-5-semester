import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PaintPanel extends JPanel{
    private BufferedImage image = ImageIO.read(new File("D:\\IDEA\\3 курс\\1 семестр\\Java-5-semester\\kr\\src\\smile.jpg"));
    private int x = 0;
    private int y = 0;
    private int deltaX = 1;
    private int deltaY = 1;
    public int numCros = 0;

    public PaintPanel() throws IOException {
    }

    public void changePosition(){
        if (x == 500 && y == 500) {
            y = 0;
            deltaX *= -1;
            numCros++;
        } else {
            if (x == 0 && y == 500) {
                y = 0;
                deltaX *= -1;
                numCros++;
            } else {
                y += deltaY;
                x += deltaX;
            }
        }
    }

    public int getNumCros() {
        return numCros;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        changePosition();
        g.drawImage(image, x, y, 100, 100, this);
    }
}
