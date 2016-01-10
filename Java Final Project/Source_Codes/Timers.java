import java.util.Random;

import javax.swing.JOptionPane;


public class Timers extends Thread{
	Battleship bs;
	public int time = 15;
	private int comp_waiting_time;
	public boolean running = true;
	private boolean ending = false;
	private String ending_msg;
	private Random rand = new Random();
	private SplashAnimation sa = null;
	
	Timers(Battleship bs){
		this.bs = bs;
		comp_waiting_time = rand.nextInt(14) + 1;
	}
	
	public String getTime(){
		if(time < 10){
			return "0:0" + time;
		}
		return "0:" + time;
	}
	
	public void stopTime(){
		running = false;
	}
	
	public void waitAnimationSound(SplashAnimation sa, String ending_msg){
		ending = true;
		this.sa = sa;
		this.ending_msg = ending_msg;
	}
	
	public void run() {
		while(running){
			bs.time.setText("Time - " + getTime());
			if(time == 3){
				bs.logMsg.append("\nWarning - 3 seconds left in this round!");
			}
			try {
				sleep(1000);
			} catch (InterruptedException ie) {
				System.out.println(ie.getMessage());
			}
			
			if(ending){
				// If the game is over
				try {
					sa.join();
					JOptionPane.showMessageDialog(bs, 
							ending_msg, 
							"Game Over", 
							JOptionPane.PLAIN_MESSAGE);
					
					// Prevent both player & computer to make additional moves
					comp_waiting_time = -1;
					bs.gameReset();
					
				} catch (InterruptedException ie) {
					System.out.println(ie.getMessage());
				}
			}
			
			if(time == comp_waiting_time){
				Battleship.computer = true;
				bs.computerMove();
			}
			if(Battleship.player && Battleship.computer){
				// Both players made their moves
				bs.round++;
				bs.logMsg.append("\n***Round " + bs.round + "***");
				Battleship.player = Battleship.computer = false;
				comp_waiting_time = rand.nextInt(14) + 1;
				//comp_waiting_time = rand.nextInt(3) + 11;
				time = 16;
			}

			time--;
			
			if(time < 0){
				bs.round++;
				bs.logMsg.append("\n***Round " + bs.round + "***");
				Battleship.player = Battleship.computer = false;
				comp_waiting_time = rand.nextInt(14) + 1;
				//comp_waiting_time = rand.nextInt(3) + 11;
				time = 15;
			}
		}
	}
}
