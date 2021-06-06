package uniandes.dpoo.proyecto1.GUI.EstructuraVentana;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelImagenFondo extends JPanel {

    private JPanel panelpintar;
    private static final long serialVersionUID = 1L;
    public final static String RUTA = "./data/imagenes/backgroundp.jpg";
    private BufferedImage image;

    

    public PanelImagenFondo( ) {
        try {
            image = ImageIO.read(new File(RUTA));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(),
                    this);

            setOpaque(false);
        } else {
            setOpaque(true);
        }
        super.paint(g);
    }

}
