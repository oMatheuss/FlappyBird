package principal;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.FloatControl;

public class AudioManager {
	private AudioInputStream[] sound = new AudioInputStream[5];
	
	public final int Die = 0;
	public final int Hit = 1;
	public final int Point = 2;
	public final int Swoosh = 3;
	public final int Wing = 4;
	
	
	Clip[] clip = new Clip[5];
	
	public AudioManager(ResourceManager rm) {
		sound[0] = rm.getAudio("sfx_die");
		sound[1] = rm.getAudio("sfx_hit");
		sound[2] = rm.getAudio("sfx_point");
		sound[3] = rm.getAudio("sfx_swooshing");
		sound[4] = rm.getAudio("sfx_wing");
		
		try {
			for (int i = 0; i < 5; i++) {
				clip[i] = AudioSystem.getClip();
				byte[] audio = audioToByteArray(sound[i]);
				AudioFormat format = sound[i].getFormat();
				clip[i].open(format, audio, 0, audio.length);
			}
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] audioToByteArray(AudioInputStream audio) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedInputStream in = new BufferedInputStream(audio);
		
		int read;
		byte[] buff = new byte[512];
		try {
			while ((read = in.read(buff)) > 0)
			{
			    out.write(buff, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return out.toByteArray();
	}
	
	public void volume(float v) {
		if (v < 0f || v > 1f) {
			System.out.println("Volume out of range!");
			return;
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
