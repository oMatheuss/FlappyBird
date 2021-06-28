package principal;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import principal.Game.Dificuldade;
import principal.UI.MenuDificuldades;
import principal.UI.MenuInicial;
import principal.UI.MenuPausa;
import principal.UI.MenuSobre;
import principal.UI.MenuVolume;

public class WindowController extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ResourceManager rm;
	private Game fb;
	
	private JLayeredPane mainPane;
	private MenuInicial mi;
	private MenuPausa mp;
	private MenuSobre ms;
	private MenuDificuldades md;
	private MenuVolume vol;
	
	public Dificuldade dificuldadeAtual;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new WindowController();
			}
		});
	}
	
	public WindowController() {
		setTitle("FlappyBird");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		rm = new ResourceManager();
		rm.loadResources();
		
		fb = new Game(this, rm);
    	
    	mi = new MenuInicial(this, rm);
    	mp = new MenuPausa(this, rm);
		ms = new MenuSobre(this, rm);
		md = new MenuDificuldades(this, rm);
		vol = new MenuVolume(this, rm);

    	initComponents();
	}

	public void initComponents() {
        setPreferredSize(getDimension());
        setResizable(false);
        setFocusable(true);
        setAutoRequestFocus(true);
        getContentPane().setEnabled(false);
        
		mainPane = new JLayeredPane();
		
		mainPane.setLayer(fb.getGui(), new Integer(5));
		mainPane.setLayer(mi, new Integer(1));
		mainPane.setLayer(mp, new Integer(6));
		mainPane.setLayer(ms, new Integer(3));
		mainPane.setLayer(md, new Integer(2));
		mainPane.setLayer(vol, new Integer(2));
		
        mainPane.add(fb.getGui());
        mainPane.add(mi);
        mainPane.add(mp);
		mainPane.add(ms);
		mainPane.add(md);
		mainPane.add(vol);
		
		add(mainPane);
        pack();
        setVisible(true);
        
        dificuldadeAtual = Dificuldade.MEDIO;
        seletor(Entrada.MENU_INICIAL);
	}
	
	public void setNotVisible() {
		for (int i = 0; i < 6; i++) {
			mainPane.getComponent(i).setVisible(false);
			mainPane.validate();
			//System.out.println(mainPane.getComponent(i).getName());
		}
	}
	
	//Usado no metodo seletor
	public enum Entrada {
		MENU_INICIAL,
		MENU_DIFICULDADES,
		NOVO_JOGO,
		REINICIAR,
		MENU_MORTE,
		MENU_SOBRE,
		SAIR
	}
	
	
	public void seletor(Entrada opc) {
		switch (opc) {
			case MENU_INICIAL:
				setNotVisible();
				vol.setVisible(true);
				mi.setVisible(true);
				vol.repaint();
				mi.repaint();
				break;
			case MENU_DIFICULDADES:
				md.setVisible(true);
				md.repaint();
				break;
			case NOVO_JOGO:
				setNotVisible();
				fb.getGui().setVisible(true);
				fb.novoJogo(dificuldadeAtual);
				fb.start();
				break;
			case REINICIAR:
				mp.setVisible(false);
				fb.novoJogo(dificuldadeAtual);
				fb.start();
				break;
			case MENU_MORTE:
				mp.setVisible(true);
				mp.repaint();
				mp.requestFocus();
				break;
			case MENU_SOBRE:
				ms.setVisible(true);
				ms.repaint();
				break;
			case SAIR:
				System.exit(0);
				break;
		}
	}
	
	public void setDificuldadeAtual(Dificuldade dificuldadeAtual) {
		this.dificuldadeAtual = dificuldadeAtual;
	}

	public void setarVolume(int i) {
		fb.setarVolume(i);
	}
	
	public Dimension getDimension() {
		return new Dimension(800, 600);
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.dispose();
	}
}
