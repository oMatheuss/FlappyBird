package principal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class Pipe extends Objeto {
	private final ImageObserver o;
	private final ResourceManager rm;
	
	private final Image[] pipeImg = new Image[4];
	private final int direction;
	
	private final int[] posImg = new int[2];
	
	public Pipe(int w, int h, int direction, ImageObserver o, ResourceManager rm) {
		super(w, h);
		this.direction = direction;
		this.o = o;
		this.rm = rm;
	}

	public void setNewPosition(int x, int y) {
		this.setLocation(x, y);
		initSptrites();
	}
	
	public void initSptrites() {
		pipeImg[0] = rm.getSprite("pipe-top-01").getScaledInstance(width, width, Image.SCALE_SMOOTH);
		pipeImg[1] = rm.getSprite("pipe-bottom-01").getScaledInstance(width, width, Image.SCALE_SMOOTH);
		if (height > width)
			pipeImg[2] = rm.getSprite("pipe-mid-01").getScaledInstance(width, height-width, Image.SCALE_SMOOTH);
		
		posImg[0] = y+height-width;
		posImg[1] = y+width;
	}
 	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.blue);
		//g2d.draw3DRect(x, y, width, height, true);
		
		if (direction == 1) {
			g2d.drawImage(pipeImg[1], x, posImg[0], o);
			if (height > width)
				g2d.drawImage(pipeImg[2], x, y, o);
		} else {
			g2d.drawImage(pipeImg[0], x, y, o);
			if (height > width)
				g2d.drawImage(pipeImg[2], x, posImg[1], o);
		}
	}
}
