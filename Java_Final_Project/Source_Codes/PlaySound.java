import java.io.File;


public class PlaySound extends Thread{
	
	File sound;
	boolean sink = false;
	
	public PlaySound(File sound, boolean sink){
		this.sound = sound;
		this.sink = sink;
	}
	
	public void run(){

		try {
			if(sink){
				// Waiting for both cannon & explode sounds
				sleep(3520);
				SoundLibrary.playSound(Battleship.sinking);
			}
			else{
				// Cannon blast sound
				SoundLibrary.playSound(Battleship.cannon);
				SoundLibrary.playSound(sound);
			}	
			
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}
