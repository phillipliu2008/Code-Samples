
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;


public class SoundLibrary {

	public static void playSound(File sound_file) {
		File toPlay = sound_file;
		
		try {
		AudioInputStream stream = AudioSystem.getAudioInputStream(toPlay);
		AudioFormat format = stream.getFormat();
		SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class,format,(int) (stream.getFrameLength() * format.getFrameSize()));
		SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
		
		line.open(stream.getFormat());
		line.start();
		int num_read = 0;
		byte[] buf = new byte[line.getBufferSize()];
		while ((num_read = stream.read(buf, 0, buf.length)) >= 0)
		{
			int offset = 0;
			
			while (offset < num_read)
			{
				offset += line.write(buf, offset, num_read - offset);
			}
		}
		line.drain();
		line.stop();
		} catch(IOException | UnsupportedAudioFileException | LineUnavailableException ioe) {
			System.out.println("Audio file is invalid!");
		}
	}
}
