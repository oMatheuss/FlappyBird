package principal;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class Bird extends Objeto {
	private final ImageObserver o;
	private final ResourceManager rm;
	
	private final Image[] birdImg = new Image[4];
	private final int[] tamEPos = new int[4];
	private int frame;
	
	
	public Bird(int x, int y, int scale, ImageObserver o, ResourceManager rm) {
		super(x, y, scale, scale);
		this.o = o;
		this.rm = rm;
		frame = 0;
		initSprites();
	}
	
	public void initSprites() {
		//a imagem tem o dobro do tamanho do retangulo
		tamEPos[0] = width*2;
		tamEPos[1] = height*2;
		birdImg[0] = rm.getSprite("bird1").getScaledInstance(tamEPos[0], tamEPos[1], Image.SCALE_SMOOTH);
		birdImg[1] = rm.getSprite("bird2").getScaledInstance(tamEPos[0], tamEPos[1], Image.SCALE_SMOOTH);
		birdImg[2] = rm.getSprite("bird3").getScaledInstance(tamEPos[0], tamEPos[1], Image.SCALE_SMOOTH);
		birdImg[3] = rm.getSprite("bird4").getScaledInstance(tamEPos[0], tamEPos[1], Image.SCALE_SMOOTH);
		
		//determinar posicao da imagem relativo ao Rectangle usado para colisao
		tamEPos[2] = width/2+tamEPos[0]/20;
		tamEPos[3] = height/2-tamEPos[1]/2;
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.drawImage(birdImg[frame], x - tamEPos[2], y + tamEPos[3], o);
		//g2d.drawRect(x, y, width, height);
	}
	
	public void setFrame(int frame) {
		this.frame = frame;
	}
}
