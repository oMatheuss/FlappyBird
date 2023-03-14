package principal;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Objeto extends Rectangle {

	public Objeto() {
		super();
	}
	
	public Objeto(int w, int h) {
		super(w, h);
	}
	
	public Objeto(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public abstract void paint(Graphics2D g2d);
}
