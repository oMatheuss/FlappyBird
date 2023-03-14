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
import principal.WindowController.Entrada;


public class MenuPausa extends Canvas {
	public final Dimension tam = new Dimension(250, 150);
	
	private final WindowController gc;
	
	public BufferedImage bufImg;
	private Graphics2D bufGraph;
	private boolean firstTime;
	
	private final Font font;
	private final Color cor = Color.decode("#77ACA2");
	private Botao recomecar, sair, perdeu;

	public MenuPausa(WindowController gc, ResourceManager rm) {
		setBounds(275, 240, tam.width, tam.height);
		this.setVisible(false);
		this.gc = gc;
		font = rm.getFont("geomarice");
    	firstTime = true;
    	initComponents();
	}
	
	public void initComponents() {
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				if (recomecar.contemCoord(p)) {
					recomecar.ativo(true);
					repaint();
				} else if (sair.contemCoord(p)) {
					sair.ativo(true);
					repaint();
				} else {
					recomecar.ativo(false);
					sair.ativo(false);
					repaint();
				}
			}
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				if (recomecar.contemCoord(p))
					gc.seletor(Entrada.REINICIAR);
				if (sair.contemCoord(p)) {
					gc.seletor(Entrada.MENU_INICIAL);
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
    	});
    	
    	repaint();
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void paint(Graphics g) {
		if (firstTime) {
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
		
		perdeu.paint(bufGraph);
		recomecar.paint(bufGraph);
		sair.paint(bufGraph);
	}
	
	private void drawFirstTime() {
		bufImg = new BufferedImage(tam.width, tam.height, BufferedImage.TYPE_INT_RGB);
		bufImg = (BufferedImage) createImage(tam.width, tam.height);
		bufGraph = bufImg.createGraphics();
		bufGraph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		bufGraph.setBackground(cor);
		
		perdeu = new Botao(bufGraph, "Voce Perdeu!", font, 48);
		perdeu.setCoords(125, 45);
		perdeu.setLocationAsCenter();
		recomecar = new Botao(bufGraph, "Recomecar", font, 36);
		recomecar.setCoords(125, 95);
		recomecar.setLocationAsCenter();
		sair = new Botao(bufGraph, "Sair", font, 36);
		sair.setCoords(125, 140);
		sair.setLocationAsCenter();
	}
}
