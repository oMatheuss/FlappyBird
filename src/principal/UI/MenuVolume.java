package principal.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

import principal.Botao;
import principal.WindowController;
import principal.ResourceManager;

public class MenuVolume extends JPanel {
	private static final long serialVersionUID = 1L;

	public final Dimension tam = new Dimension(120, 60);
	
	private WindowController gc;
	
	public BufferedImage bufImg;
	private Graphics2D bufGraph;
	private boolean firstTime;
	
	private Font font;
	private Color cor = Color.decode("#77ACA2");
	private Color cor2 = Color.decode("#031926");
	
	private JScrollBar vol;
	private Botao volume;
	
	public MenuVolume(WindowController gc, ResourceManager rm) {
		setBounds(650, 490, tam.width, tam.height);
		setLayout(null);
		this.gc = gc;
		font = rm.getFont("geomarice");
    	firstTime = true;
    	
    	vol = new JScrollBar();
    	vol.setBounds(10, 35, 100, 20);
		vol.setValues(0, 1, 0, 100);
		vol.setOrientation(Scrollbar.HORIZONTAL);
		vol.setBackground(cor2);
		vol.setValue(20);
	}
	
	public void initComponents() {
		gc.setarVolume(20);
		vol.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				gc.setarVolume(e.getValue());
			}
		});
		this.add(vol);
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
		super.paintChildren(g2d);
		g2d.dispose();
		
		bufGraph.clearRect(0, 0, tam.width, tam.height);
		
		bufGraph.setColor(Color.black);
		bufGraph.fillRect(0, 0, tam.width/100, tam.height);
		bufGraph.fillRect(tam.width-tam.width/100, 0, tam.width/100, tam.height);
		bufGraph.fillRect(0, 0, tam.width, tam.height/60);
		bufGraph.fillRect(0, tam.height-tam.height/60, tam.width, tam.height/60);
		
		volume.paint(bufGraph);
		vol.paint(g2d);
	}
	
	private void drawFirstTime() {
		bufImg = new BufferedImage(tam.width, tam.height, BufferedImage.TYPE_INT_RGB);
		bufImg = (BufferedImage) createImage(tam.width, tam.height);
		bufGraph = bufImg.createGraphics();
		bufGraph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		bufGraph.setBackground(cor);
		
		volume = new Botao(bufGraph, "Volume", font, 24);
		volume.setCoords(60-volume.getWidth()/2, 25);
		
		initComponents();
	}
}
