package principal.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import principal.Background;
import principal.WindowController;
import principal.ResourceManager;
import principal.WindowController.Entrada;

public class MenuInicial extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private WindowController gc;
	
	private BufferedImage bufImg;
	private Graphics2D bufGph;
	private boolean firstTime;
	
	private Background background;
	private Font font;
	private Font font2;
	private Color cor = Color.decode("#090C08");
	private Color cor2 = Color.decode("#4B5842");
	private int[][] posOpcoes = new int[4][3];
	private int highlightOption = 0;
	
	public MenuInicial(WindowController gc, ResourceManager rm) {
		setBounds(0, 0, 800, 600);
		this.gc = gc;

		font = rm.getFont(rm.fontePrincipal).deriveFont(Font.PLAIN, 48);
		font2 = rm.getFont(rm.fonteSecundaria).deriveFont(Font.PLAIN, 48);

		background = new Background(0, 0, 2.5, this, rm);
		firstTime = true;
		repaint();
		initComponents();
		
	}
	
	public void initComponents() {
		MouseMotionListener mml = new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				if (p.x > posOpcoes[1][2] && p.x < (posOpcoes[1][2]+posOpcoes[1][0]) && p.y > 300-posOpcoes[1][1] && p.y < 300) {
					highlightOption = 1;
					//System.out.println(p.x + "  " + p.y);
					repaint();
				} else if (p.x > posOpcoes[3][2] && p.x < (posOpcoes[3][2]+posOpcoes[3][0]) && p.y > 350-posOpcoes[2][1] && p.y < 350) {
					highlightOption = 2;
					repaint();
				} else if (p.x > posOpcoes[2][2] && p.x < (posOpcoes[2][2]+posOpcoes[2][0]) && p.y > 400-posOpcoes[2][1] && p.y < 400) {
					highlightOption = 3;
					repaint();
				} else {
					highlightOption = 0;
					repaint();
				}
			}
		};
		
		MouseListener ml = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX(), y = e.getY();
				if (x > posOpcoes[1][2] && x < (posOpcoes[1][2]+posOpcoes[1][0]) && y > 300-posOpcoes[1][1] && y < 300)
					gc.seletor(Entrada.MENU_DIFICULDADES);
				
				if (x > posOpcoes[3][2] && x < (posOpcoes[3][2]+posOpcoes[3][0]) && y > 350-posOpcoes[3][1] && y < 350)
					gc.seletor(Entrada.MENU_SOBRE);

				if (x > posOpcoes[2][2] && x < (posOpcoes[2][2]+posOpcoes[2][0]) && y > 400-posOpcoes[2][1] && y < 400)
					gc.seletor(Entrada.SAIR);
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
    	};
    	this.addMouseMotionListener(mml);
    	this.addMouseListener(ml);
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
		g2d.drawImage(bufImg, 0, 0, this);
		
		bufGph.clearRect(0, 0, 800, 600);
		background.paint(bufGph);
		bufGph.setColor(cor);
		bufGph.setFont(font);
		bufGph.drawString("Flappy Bird", posOpcoes[0][2], 100);

		bufGph.setFont(font2);
		
		switch (highlightOption) {
			case 1:
				bufGph.drawString("Sobre", posOpcoes[3][2], 350);
				bufGph.drawString("Sair", posOpcoes[2][2], 400);
				bufGph.setColor(cor2);
				bufGph.drawString("Novo Jogo", posOpcoes[1][2], 300);
				break;
			case 2:
				bufGph.drawString("Novo Jogo", posOpcoes[1][2], 300);
				bufGph.drawString("Sair", posOpcoes[2][2], 400);
				bufGph.setColor(cor2);
				bufGph.drawString("Sobre", posOpcoes[3][2], 350);
				break;
			case 3:
				bufGph.drawString("Novo Jogo", posOpcoes[1][2], 300);
				bufGph.drawString("Sobre", posOpcoes[3][2], 350);
				bufGph.setColor(cor2);
				bufGph.drawString("Sair", posOpcoes[2][2], 400);
				break;
			default:
				bufGph.drawString("Novo Jogo", posOpcoes[1][2], 300);
				bufGph.drawString("Sobre", posOpcoes[3][2], 350);
				bufGph.drawString("Sair", posOpcoes[2][2], 400);
				break;
		}
	}
	
	private void drawFirstTime() {
		bufImg = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		bufImg = (BufferedImage) createImage(800, 600);
		bufGph = bufImg.createGraphics();
		bufGph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		bufGph.setBackground(Color.white);
		
		bufGph.setFont(font);
		//Tamanho da palavras
		//Titulo
		posOpcoes[0][0] = (int) font.getStringBounds("Flappy Bird", bufGph.getFontRenderContext()).getWidth();
		posOpcoes[0][1] = (int) font.getStringBounds("Flappy Bird", bufGph.getFontRenderContext()).getHeight();
		posOpcoes[0][2] = 400-posOpcoes[0][0]/2;

		bufGph.setFont(font2);
		//NovoJogo
		posOpcoes[1][0] = (int) font2.getStringBounds("Novo Jogo", bufGph.getFontRenderContext()).getWidth();
		posOpcoes[1][1] = (int) font2.getStringBounds("Novo Jogo", bufGph.getFontRenderContext()).getHeight();
		posOpcoes[1][2] = 400-posOpcoes[1][0]/2;
		//Sair
		posOpcoes[2][0] = (int) font2.getStringBounds("Sair", bufGph.getFontRenderContext()).getWidth();
		posOpcoes[2][1] = (int) font2.getStringBounds("Sair", bufGph.getFontRenderContext()).getHeight();
		posOpcoes[2][2] = 400-posOpcoes[2][0]/2;
		//Sobre
		posOpcoes[3][0] = (int) font2.getStringBounds("Sobre", bufGph.getFontRenderContext()).getWidth();
		posOpcoes[3][1] = (int) font2.getStringBounds("Sobre", bufGph.getFontRenderContext()).getHeight();
		posOpcoes[3][2] = 400-posOpcoes[3][0]/2;
	}
}
