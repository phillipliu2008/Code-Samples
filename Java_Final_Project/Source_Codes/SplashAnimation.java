import java.util.ArrayList;

import javax.swing.ImageIcon;


public class SplashAnimation extends Thread{
	
	ArrayList<MyJLabel> labels;
	ExplosionAnimation ea = null;
	int waiting_time = 1550;
	
	public SplashAnimation(ArrayList<MyJLabel> labels){
		this.labels = labels;
	}
	
	public void setOrder(ExplosionAnimation ea){
		this.ea = ea;
	}
	
	public void run(){
		if(ea != null){
			try {
				ea.join();
				waiting_time = 0;
			} catch (InterruptedException ie) {
				System.out.println(ie.getMessage());
			}
		}
		
		try {
			// Waitng for cannon sound to finish
			sleep(waiting_time);			
		} catch (InterruptedException ie) {
			System.out.println(ie.getMessage());
		}
		
		for(int i = 0; i < labels.size(); i++){
			MyJLabel cur = labels.get(i);
			ImageIcon final_image = cur.getImage();
			
			cur.setSizes(10,5);
			if(labels.size() == 1){
				// Miss animation
				for(int j = 0; j < 7; j++){
					cur.setLabel(Battleship.splashArr[j]);
					cur.repaint();
					try {
						sleep(142);
					} catch (InterruptedException ie) {
						System.out.println(ie.getMessage());
					}
				}
				cur.setSizes(14, 7);
				cur.setLabel(new ImageIcon("images/M.png"));
				cur.repaint();
			}
			else{
				// Sink animation
				for(int j = 0; j < 7; j++){
					cur.setLabel(Battleship.splashArr[j]);
					cur.repaint();
					try {
						sleep(1000 / labels.size() / 7 + 15);
					} catch (InterruptedException ie) {
						System.out.println(ie.getMessage());
					}
				}
				cur.setSizes(14, 7);
				cur.setLabel(final_image);
				cur.repaint();
			}
		}
	}
	
}
