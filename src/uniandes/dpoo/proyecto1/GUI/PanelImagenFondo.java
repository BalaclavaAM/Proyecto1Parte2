package uniandes.dpoo.proyecto1.GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelImagenFondo extends JPanel {
	
    private static final long serialVersionUID = 1L;
    public final static String RUTA = "./data/imagenes/backgroundp.jpg";
    private BufferedImage image;

    
    @Override
    protected void paintComponent(Graphics g) {
		try {                
	        image = ImageIO.read(new File(RUTA));
	     } catch (IOException ex) {
	          // handle exception...
	     }
      super.paintComponent(g);
          g.drawImage(image, 0, 0, null);
  }
    
    public PanelImagenFondo( )
    {
        
    }

    
}
