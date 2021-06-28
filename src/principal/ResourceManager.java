package principal;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ResourceManager {
	private HashMap<String, Object> resources;
	private InputStream is;
	
	public final String fontePrincipal = "chlorinr";
	public final String fonteSecundaria = "geomarice";

	public ResourceManager() {
		resources = new HashMap<String, Object>();
	}
	
	public void loadResources() {
		setSprite("frame-1.png", "bird1");
		setSprite("frame-2.png", "bird2");
		setSprite("frame-3.png", "bird3");
		setSprite("frame-4.png", "bird4");
		
		setSprite("pipe-top-white.png", "pipe-top-01");
		setSprite("pipe-bottom-white.png", "pipe-bottom-01");
		setSprite("pipe-mid-white.png", "pipe-mid-01");
		
		setSprite("mainBackground.png", "mainBackground");
		
		setTrueTypeFont("gomarice_game_continue_02.ttf", fonteSecundaria);
		//titulo
		setTrueTypeFont("Chlorinr.ttf", fontePrincipal);
		
		setSound("sfx_die.wav", "sfx_die");
		setSound("sfx_hit.wav", "sfx_hit");
		setSound("sfx_point.wav", "sfx_point");
		setSound("sfx_swooshing.wav", "sfx_swooshing");
		setSound("sfx_wing.wav", "sfx_wing");
	}
	
	public void setSprite(String fileName, String name){
		is = getClass().getClassLoader().getResourceAsStream("resources/sprites/"+fileName);
		if (is != null) {
			try {
				resources.put(name, ImageIO.read(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("recurso nao encontrado (Image)");
		}
	}
	
	public void setTrueTypeFont(String fileName, String name) {
		BufferedInputStream au = new BufferedInputStream(
				getClass().getClassLoader().getResourceAsStream("resources/fonts/"+fileName));
		if (is != null) {
			try {
				resources.put(name, Font.createFont(Font.TRUETYPE_FONT, au));
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("recurso nao encontrado (Font)");
		}
	}
	
	public void setSound(String fileName, String name) {
		is = getClass().getClassLoader().getResourceAsStream("resources/sfx/"+fileName);
		BufferedInputStream au = new BufferedInputStream(is);
		if (is != null) {
			try {
				resources.put(name, AudioSystem.getAudioInputStream(au));
			} catch (UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("recurso nao encontrado (Sound)");
		}
	}
	
	public Object getResource(String name) {
		return resources.get(name);
	}
	
	public BufferedImage getSprite(String name) {
		return (BufferedImage) resources.get(name);
	}
	
	public Font getFont(String name) {
		return (Font) resources.get(name);
	}
	
	public AudioInputStream getAudio(String name) {
		return (AudioInputStream) resources.get(name);
	}
}
