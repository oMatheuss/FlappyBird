package principal;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

public class ResourceManager {
	private final HashMap<String, Object> resources;
	public final String fontePrincipal = "chlorinr";
	public final String fonteSecundaria = "geomarice";

	public ResourceManager() {
		resources = new HashMap<>();
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
		URL resource = ClassLoader.getSystemResource("resources/sprites/" + fileName);
		try(InputStream is = resource.openStream()) {
			resources.put(name, ImageIO.read(is));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setTrueTypeFont(String fileName, String name) {
		URL resource = ClassLoader.getSystemResource("resources/fonts/" + fileName);
		try(InputStream is = resource.openStream()) {
			resources.put(name, Font.createFont(Font.TRUETYPE_FONT, is));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setSound(String fileName, String name) {
		URL resource = ClassLoader.getSystemResource("resources/sfx/" + fileName);
		try {
			InputStream is = new BufferedInputStream(resource.openStream());
			resources.put(name, AudioSystem.getAudioInputStream(is));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
