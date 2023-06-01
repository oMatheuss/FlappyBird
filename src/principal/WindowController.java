package principal;

import principal.Game.Dificuldade;
import principal.UI.*;

import javax.swing.*;
import java.awt.*;

public class WindowController extends JFrame {
	private final Game fb;
	
	private JLayeredPane mainPane;
	private final MenuInicial mi;
	private final MenuPausa mp;
	private final MenuSobre ms;
	private final MenuDificuldades md;
	private final MenuVolume vol;
	
	public Dificuldade dificuldadeAtual;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(WindowController::new);
	}
	
	public WindowController() {
		setTitle("FlappyBird");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ResourceManager rm = new ResourceManager();
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
		
		mainPane.setLayer(fb.getGui(), 5);
		mainPane.setLayer(mi, 1);
		mainPane.setLayer(mp, 6);
		mainPane.setLayer(ms, 3);
		mainPane.setLayer(md, 2);
		mainPane.setLayer(vol, 2);
		
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
			case MENU_INICIAL -> {
				setNotVisible();
				vol.setVisible(true);
				mi.setVisible(true);
				vol.repaint();
				mi.repaint();
			}
			case MENU_DIFICULDADES -> {
				md.setVisible(true);
				md.repaint();
			}
			case NOVO_JOGO -> {
				setNotVisible();
				fb.getGui().setVisible(true);
				fb.novoJogo(dificuldadeAtual);
				fb.start();
			}
			case REINICIAR -> {
				mp.setVisible(false);
				fb.novoJogo(dificuldadeAtual);
				fb.start();
			}
			case MENU_MORTE -> {
				mp.setVisible(true);
				mp.repaint();
				mp.requestFocus();
			}
			case MENU_SOBRE -> {
				ms.setVisible(true);
				ms.repaint();
			}
			case SAIR -> System.exit(0);
		}
	}

	public void setVolume(int i) {
		fb.setarVolume(i);
	}
	
	public Dimension getDimension() {
		return new Dimension(800, 600);
	}
}
