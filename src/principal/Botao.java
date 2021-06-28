package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class Botao {
	private int x, y, w, h;
	private String label;
	private Font font;
	private boolean ativo;
	
	private Color cor = Color.decode("#031926");
	private Color cor2 = Color.decode("#468189");
	
	public Botao(Graphics2D g2d, String label, Font font, int fontSize) {
		ativo = false;
		
		this.label = label;
		this.font = font.deriveFont(Font.PLAIN, fontSize);
		
		w = (int)this.font.getStringBounds(label, g2d.getFontRenderContext()).getWidth();
		h = (int)this.font.getStringBounds(label, g2d.getFontRenderContext()).getHeight();
		x = w;
		y = h;
	}
	
	public void paint(Graphics2D g2d) {
		if (ativo)
			g2d.setColor(cor2);
		else
			g2d.setColor(cor);
		g2d.setFont(font);
		g2d.drawString(label, x, y);
		//g2d.draw3DRect(x, y-h, w, h, true);
		
	}
	
	public void ativo(boolean b) {
		ativo = b;
	}
	
	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean contemCoord(Point p) {
		if (p.x > x && p.y > y-h && p.x < x+w && p.y < y)
			return true;
		else
			return false;
	}
	public void setLocationAsCenter() {
		x -= w/2;
	}
	public int getWidth() {
		return w;
	}
	public int getHeight() {
		return h;
	}
	public Point getLocation() {
		return new Point(x, y);
	}
}
