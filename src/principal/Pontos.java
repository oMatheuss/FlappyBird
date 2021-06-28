package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Pontos extends Objeto {
	private static final long serialVersionUID = 1L;

	private Font font;
	private Color cor = Color.decode("#090C08");
	
	private int pontos;
	private int multiplicador;
	
	public Pontos(int x, int y, ResourceManager rm) {
		super(x, y, 0, 0);
		font = rm.getFont(rm.fonteSecundaria).deriveFont(Font.PLAIN, 48);
		pontos = 0;
		multiplicador = 1;
	}
	
	@Override
	public void paint(Graphics2D g2d) {
		g2d.setFont(font);
		g2d.setColor(cor);
		int w = (int) font.getStringBounds(String.valueOf(pontos), g2d.getFontRenderContext()).getWidth();
		g2d.drawString(String.valueOf(pontos), x-w/2, y);
	}
	
	public void addPonto() {
		pontos += multiplicador;
	}
	public void zerarPontos() {
		pontos = 0;
	}
	public void setMultiplicador(int multiplicador) {
		this.multiplicador = multiplicador;
	}
	public int getPontos() {
		return pontos;
	}
}
