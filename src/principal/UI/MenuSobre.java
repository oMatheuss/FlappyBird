package principal.UI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import principal.Background;
import principal.Botao;
import principal.WindowController;
import principal.ResourceManager;
import principal.WindowController.Entrada;

public class MenuSobre extends Canvas {

	private static final long serialVersionUID = 1L;

	private WindowController gc;

    private BufferedImage bufImg;
	private Graphics2D bufGph;
	private boolean firstTime;

    private Background background;
    private Font font;
    
    private Botao voltar;

    public MenuSobre(WindowController gc, ResourceManager rm) {
    	setBounds(0, 0, 800, 600);
    	setVisible(false);
        this.gc = gc;
        font = rm.getFont(rm.fonteSecundaria).deriveFont(Font.PLAIN, 24);
        background = new Background(0, 0, 2.5, this, rm);

        setPreferredSize(new Dimension(800, 600));

        firstTime = true;
    }

    public void initComponents() {
    	addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				if (voltar.contemCoord(p)) {
					voltar.ativo(true);
					repaint();
				} else {
                    voltar.ativo(false);
					repaint();
                }
			}
		});
		
    	addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
				if (voltar.contemCoord(p))
                    gc.seletor(Entrada.MENU_INICIAL);
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
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bufImg, 0, 0, this);
        g2d.dispose();
        
        if (firstTime) {
            firstTime = false;
            drawFirstTime();
        }
        bufGph.clearRect(0, 0, 800, 600);
        background.paint(bufGph);
        bufGph.setColor(Color.black);
        bufGph.drawString("Desenvolvido por: Matheus Silva Moura.", 10, 50);
        bufGph.drawString("Aluno de Eng. da Computacao - 3P.", 10, 75);
        bufGph.drawString("Este jogo foi desenvolvido como projeto final da disciplina de POO!", 10, 125);
        bufGph.drawString("Professora: Luciene Chagas de Oliveira.", 10, 150);
        bufGph.drawString("Objetivo: Desenvolver um projeto integrado em java.", 10, 200);
        bufGph.drawString("--junho de 2021.", 10, 250);
        
        voltar.paint(bufGph);
    }

    public void drawFirstTime() {
        bufImg = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		bufImg = (BufferedImage) createImage(800, 600);
		bufGph = bufImg.createGraphics();
		bufGph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		bufGph.setBackground(Color.white);
		bufGph.setFont(font);
		
		voltar = new Botao(bufGph, "Voltar", font, 24);
		voltar.setCoords(400, 500);
		voltar.setLocationAsCenter();
		initComponents();
    } 
}
