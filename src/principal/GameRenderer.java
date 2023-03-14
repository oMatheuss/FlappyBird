package principal;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class GameRenderer extends Canvas {
	private BufferedImage bufferedImg;
	private Graphics2D buf;
	private boolean firstTime;
	
	private final Objeto[] obj;

	public GameRenderer(Objeto[] obj) {
		this.obj = obj;
		
		setBounds(0, 0, 800, 600);
		//setOpaque(true);
		firstTime = true;
		//initComponents();
	}
	
	public void paintScreen() {
		Graphics2D g2d = (Graphics2D) getGraphics();
		if (g2d != null) {
			if (firstTime) {
				firstTime = false;
				drawFirstTime();
			}
			for (Objeto objeto : obj) {
				objeto.paint(buf);
			}
			g2d.drawImage(bufferedImg, 0, 0, this);
			Toolkit.getDefaultToolkit().sync();
			g2d.dispose();
		}
	}
	
	private void drawFirstTime() {
		bufferedImg = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		bufferedImg = (BufferedImage) createImage(800, 600);
		buf = bufferedImg.createGraphics();
		buf.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		buf.setBackground(Color.cyan);
	}
}
