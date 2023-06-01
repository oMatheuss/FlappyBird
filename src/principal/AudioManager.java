package principal;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.FloatControl;

public class AudioManager {
	public final int Die = 0;
	public final int Hit = 1;
	public final int Point = 2;
	public final int Swoosh = 3;
	public final int Wing = 4;
	
	
	Clip[] clip = new Clip[5];
	
	public AudioManager(ResourceManager rm) {
		AudioInputStream[] sound = new AudioInputStream[5];
		sound[0] = rm.getAudio("sfx_die");
		sound[1] = rm.getAudio("sfx_hit");
		sound[2] = rm.getAudio("sfx_point");
		sound[3] = rm.getAudio("sfx_swooshing");
		sound[4] = rm.getAudio("sfx_wing");
		
		try {
			for (int i = 0; i < 5; i++) {
				clip[i] = AudioSystem.getClip();
				clip[i].open(sound[i]);
			}
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void volume(float v) {
		if (v < 0f || v > 1f) {
			System.out.println("Volume out of range!");
		} else {
			for (int i = 0; i < 5; i++) {
				FloatControl gainControl = (FloatControl) clip[i].getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue((float) (20f * Math.log10(v)));
			}
		}
	}

	public void playSound(int i) {
		if (clip[i].isRunning())
			clip[i].stop();
		clip[i].flush();
		clip[i].setFramePosition(0);
		clip[i].start();
	}
}
