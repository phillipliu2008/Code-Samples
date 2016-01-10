import javax.swing.ImageIcon;


public class ExplosionAnimation extends Thread{
	public MyJLabel mjl = null;
	public ImageIcon final_image = null;
	
	public ExplosionAnimation(MyJLabel mjl, ImageIcon final_image){
		this.mjl = mjl;
		this.final_image = final_image;
	}
	
	public void run(){
		try {
			// Waitng for cannon sound to finish
			sleep(1640);
		} catch (InterruptedException ie) {
			System.out.println(ie.getMessage());
		}
		
		mjl.setSizes(12, 6);
		for(int i = 0; i < 5; i++){
			mjl.setLabel(Battleship.explodeArr[i]);
			mjl.repaint();
			try {
				sleep(400);
			} catch (InterruptedException ie) {
				System.out.println(ie.getMessage());
			}
		}
		mjl.setSizes(14, 7);
		mjl.setLabel(final_image);
		mjl.repaint();
	}
}
