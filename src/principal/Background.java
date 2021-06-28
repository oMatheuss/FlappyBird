package principal;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Background extends Objeto {
	private static final long serialVersionUID = 1L;
	private ImageObserver o;
	private ResourceManager rm;
	
	public int x, y, w, h;
	double scale;
	
	private BufferedImage backgroundBImg;
	private Image backgroundImg;
	
	public Background(int x, int y, double scale, ImageObserver observer, ResourceManager rm) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		o = observer;
		this.rm = rm;
		init();
	}
	
	public void init() {
		backgroundBImg = rm.getSprite("mainBackground");
		
		w = (int) (backgroundBImg.getWidth()*scale);
		h = (int) (backgroundBImg.getHeight()*scale);
		backgroundImg = backgroundBImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	}
	
	public void paint(Graphics2D g2d) {
		g2d.drawImage(backgroundImg, x, y, o);
	}
}
