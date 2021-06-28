package principal.UI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import principal.Botao;
import principal.WindowController;
import principal.ResourceManager;
import principal.Game.Dificuldade;
import principal.WindowController.Entrada;

public class MenuDificuldades extends Canvas {
	private static final long serialVersionUID = 1L;
	
	public final Dimension tam = new Dimension(320, 100);
	
	private WindowController gc;
	
	public BufferedImage bufImg;
	private Graphics2D bufGraph;
	private boolean firstTime;
	
	private Font font;
	private Color cor = Color.decode("#77ACA2");
	
	Botao facil, medio, dificil, dificuldades;

	public MenuDificuldades(WindowController gc, ResourceManager rm) {
		setBounds(240, 450, tam.width, tam.height);
		this.gc = gc;
		font = rm.getFont("geomarice");
    	firstTime = true;
	}
	
	public void initComponents() {
		MouseMotionListener mml = new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				if (facil.contemCoord(p)) {
					facil.ativo(true);
					medio.ativo(false);
					dificil.ativo(false);
					repaint();
				} else if(medio.contemCoord(p)) {
					medio.ativo(true);
					facil.ativo(false);
					dificil.ativo(false);
					repaint();
				} else if(dificil.contemCoord(p)) {
					dificil.ativo(true);
					facil.ativo(false);
					medio.ativo(false);
					repaint();
				} else {
					facil.ativo(false);
					medio.ativo(false);
					dificil.ativo(false);
					repaint();
				}
			}
		};
		MouseListener ml = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				if (facil.contemCoord(p)) {
					gc.dificuldadeAtual = Dificuldade.FACIL;
					gc.seletor(Entrada.NOVO_JOGO);
				}
				if (medio.contemCoord(p)) {
					gc.dificuldadeAtual = Dificuldade.MEDIO;
					gc.seletor(Entrada.NOVO_JOGO);
				}
				if (dificil.contemCoord(p)) {
					gc.dificuldadeAtual = Dificuldade.DIFICIL;
					gc.seletor(Entrada.NOVO_JOGO);
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				facil.ativo(false);
				medio.ativo(false);
				dificil.ativo(false);
				repaint();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
    	};
    	this.addMouseMotionListener(mml);
    	this.addMouseListener(ml);
    	
    	repaint();
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void paint(Graphics g) {
		if (firstTime == true) {
			firstTime = false;
			drawFirstTime();
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bufImg, 0, 0, tam.width, tam.height, this);
		g2d.dispose();
		
		bufGraph.clearRect(0, 0, tam.width, tam.height);
		
		bufGraph.setColor(Color.black);
		bufGraph.fillRect(0, 0, tam.width/100, tam.height);
		bufGraph.fillRect(tam.width-tam.width/100, 0, tam.width/100, tam.height);
		bufGraph.fillRect(0, 0, tam.width, tam.height/100);
		bufGraph.fillRect(0, tam.height-tam.height/100, tam.width, tam.height/100);
		
		facil.paint(bufGraph);
		medio.paint(bufGraph);
		dificil.paint(bufGraph);
		dificuldades.paint(bufGraph);
	}
	
	private void drawFirstTime() {
		bufImg = new BufferedImage(tam.width, tam.height, BufferedImage.TYPE_INT_RGB);
		bufImg = (BufferedImage) createImage(tam.width, tam.height);
		bufGraph = bufImg.createGraphics();
		bufGraph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		bufGraph.setBackground(cor);
		
		dificuldades = new Botao(bufGraph, "Dificuldades:", font, 48);
		dificuldades.setCoords(160 - dificuldades.getWidth()/2, 45);
		
		facil = new Botao(bufGraph, "Facil", font, 36);
		facil.setCoords(10, 95);
		
		medio = new Botao(bufGraph, "Medio", font, 36);
		medio.setCoords(160 - medio.getWidth()/2, 95); 
		
		dificil = new Botao(bufGraph, "Dificil", font, 36);
		dificil.setCoords(310-dificil.getWidth(), 95);
		initComponents();
	}
}