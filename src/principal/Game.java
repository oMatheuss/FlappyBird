package principal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import principal.WindowController.Entrada;

public class Game implements Runnable {
	
	public final int[][] pos = new int[9][2]; {
		pos[0][0] = 500;
		pos[0][1] = -50;
		
		pos[1][0] = 450;
		pos[1][1] = -100;
		
		pos[2][0] = 400;
		pos[2][1] = -150;
		
		pos[3][0] = 350;
		pos[3][1] = -200;
		
		pos[4][0] = 300;
		pos[4][1] = -250;
		
		pos[5][0] = 250;
		pos[5][1] = -300;
		
		pos[6][0] = 200;
		pos[6][1] = -350;
		
		pos[7][0] = 400;
		pos[7][1] = -150;
	
		pos[8][0] = 350;
		pos[8][1] = -200;
	}
	
	private final WindowController gc;
	private final ResourceManager rm;
	private final GameRenderer gui;

	private int velocity;
	private double gravity;
	private double forcaPulo;

	private double forcaY;
	private int cont, frameAtual;
	private boolean pulou;
	private long cooldownPulo;
	private boolean podePular;
	
	private final AudioManager am;
	private Bird player;
	private final Pipe[] obstacle = new Pipe[6];
	private final Background[] background = new Background[2];
	private Pontos pontos;
	
	private int x;
	public int modo;
	public boolean jogando;
	private boolean firstTime;
	
	public boolean[] passou = { false, false, false };
	
	public Game(WindowController gc, ResourceManager rm) {
		this.gc = gc;
		this.rm = rm;
		am = new AudioManager(rm);
		am.volume(0.2f);
		
		gui = new GameRenderer(criarObjetos());
		setarTeclas();
	}
	
	public void run() {
		long before, elapsed, waitTime = 34_000_000;

		while(jogando) {
			before = System.nanoTime();
			
			if (modo == 1) {
				update(1);
				gui.paintScreen();
			} else if (modo == 2) {
				update(2);
				gui.paintScreen();
			}

			elapsed = System.nanoTime() - before;
			
			while (elapsed < waitTime) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				elapsed = System.nanoTime() - before;
				if (elapsed >= waitTime)
					break;
			}
		}
	}
	
	public void start() {
		gui.paintScreen();
		if (!gui.isFocusOwner())
			gui.requestFocus();
		am.playSound(am.Swoosh);
		jogando = true;
		modo = 2;
		new Thread(this).start();
	}
	
	public Objeto[] criarObjetos() {
		Objeto[] obj = new Objeto[10];
		
		background[0] = new Background(0, 0, 2.5, gui, rm);
		background[1] = new Background(background[0].w, 0, 2.5, gui, rm);
		obj[0] = background[0];
		obj[1] = background[1];
		
		for (int i = 0; i < 6; i += 2) {
			obstacle[i] = new Pipe(50, 400, 1, gui, rm);
			obstacle[i+1] = new Pipe(50, 400, -1, gui, rm);
			obj[2+i] = obstacle[i];
			obj[3+i] = obstacle[i+1];
		}
		
		player = new Bird(150, 150, 20, gui, rm);
		obj[8] = player;
		pontos = new Pontos(400, 50, rm);
		obj[9] = pontos;
		return obj;
	}
	
	public void setarTeclas() {
		KeyListener key = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (firstTime) {
						firstTime = false;
						modo = 1;
						cont = 0;
					}
					pulou = true;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE)
					pulou = false;
			}
		};
		gui.addKeyListener(key);
	}
	
	public void setarVolume(int x) {
		am.volume(x/100.0f);
	}
	
	public enum Dificuldade {
		FACIL,
		MEDIO,
		DIFICIL
	}

	public void novoJogo(Dificuldade d) {
		switch (d) {
			case FACIL -> {
				velocity = 480 / 30;
				gravity = 1.0;
				forcaPulo = 400 / 30.0;
			}
			case MEDIO -> {
				velocity = 750 / 30;
				gravity = 50.0 / 30.0;
				forcaPulo = 550 / 30.0;
			}
			case DIFICIL -> {
				velocity = 1050 / 30;
				gravity = 100.0 / 30.0;
				forcaPulo = 750 / 30.0;
			}
		}
		
		firstTime = true;
		
		pontos.zerarPontos();
		forcaY = 0;
		frameAtual = 0;
		cont = 0;
		pulou = false;
		podePular = true;
		player.setLocation(150, 300);
		modo = 2;

		x = randomizar();
		obstacle[0].setNewPosition(850, pos[x][1]);
		obstacle[1].setNewPosition(850, pos[x][0]);
		x = randomizar();
		obstacle[2].setNewPosition(1150, pos[x][1]);
		obstacle[3].setNewPosition(1150, pos[x][0]);
		x = randomizar();
		obstacle[4].setNewPosition(1450, pos[x][1]);
		obstacle[5].setNewPosition(1450, pos[x][0]);
	}
	
	public void update(int i) {
		switch (i) {
			case 1 -> {
				if (colidiu()) {
					jogando = false;
					am.playSound(am.Hit);
					gc.seletor(Entrada.MENU_MORTE);
					return;
				} else if ((player.y > 600) || (player.y < -20)) {
					jogando = false;
					am.playSound(am.Die);
					gc.seletor(Entrada.MENU_MORTE);
					return;
				}
				player.setFrame(frameAtual);
				updateY();
				movendoBackground();
				movendoObstaculos();
			}
			case 2 -> {
				player.setFrame(frameAtual);
				movendoBackground();
				animacaoP(cont == 0);
			}
		}
	}
	 
	public void updateY() {
		if (pulou && podePular) {
			podePular = false;
			cooldownPulo = System.currentTimeMillis();
			animacaoP(true);
			am.playSound(am.Wing);
			forcaY = 0;
			forcaY -= forcaPulo;
			return;
		} else if (!podePular) {
			if (System.currentTimeMillis() - cooldownPulo > 200)
				podePular = true;
		}
		animacaoP(false);
		player.y += forcaY;
		forcaY += gravity;
	}
	
	public void animacaoP(boolean b) {
		if(b) {
			cont = 16;
			frameAtual = 1;
			return;
		}
		if (cont == 0)
			return;

		switch (cont) {
			case 12 -> {
				frameAtual = 2;
				cont--;
			}
			case 8 -> {
				frameAtual = 3;
				cont--;
			}
			case 4 -> {
				frameAtual = 0;
				cont--;
			}
			default -> cont--;
		}
	}
	public void movendoBackground() {
		background[0].x -= velocity/4;
		background[1].x -= velocity/4;
		
		if (background[0].x <= -background[0].w)
			background[0].x = background[1].x+background[1].w;
		
		if (background[1].x <= -background[1].w)
			background[1].x = background[0].x+background[0].w;
	}

	public void movendoObstaculos() {
		for (int i = 0; i < 5; i += 2) {
			obstacle[i].x -= velocity/2;
			obstacle[i+1].x -= velocity/2;
			
			if (obstacle[i].x <= -50) {
				x = randomizar();
				obstacle[i].setNewPosition(850, pos[x][1]);
				obstacle[i+1].setNewPosition(850, pos[x][0]);

				//tornar possivel ganhar pontos
				passou[i/2] = false;
			}
			
			if (player.x > obstacle[i].x+20 && !passou[i / 2]) {
				passou[i/2] = true;
				pontos.addPonto();
				if (pontos.getPontos() % 10 == 0)
					am.playSound(am.Point);
			}
		}
	}
	
	public boolean colidiu(){
		for (int i = 0; i < 6; i++) {
			if (player.intersects(obstacle[i])) {
				return true;
			}
		}
		return false;
	}
	
	public int randomizar() {
		int random = (int) (Math.random()*10);
		if (random > 8)
			random = 8;
		return random;
	}

	public GameRenderer getGui() {
		return gui;
	}
}
