
public class WaterAnimation extends Thread{
	Battleship bs;
	int counter = 0;
	
	WaterAnimation(Battleship bs){
		this.bs = bs;
	}
	
	public void run(){
		while(true){
			for(int i = 0; i < 110; i++){
				if(i % 11 != 0){
					MyJLabel mj1 = (MyJLabel) bs.userPanel.getComponent(i);
					MyJLabel mj2 = (MyJLabel) bs.compPanel.getComponent(i);
					mj1.setImage(Battleship.waterArr[counter]);
					mj2.setImage(Battleship.waterArr[counter]);
					mj1.repaint();
					mj2.repaint();
				}
			}
			if(counter == 0){
				counter++;
			}
			else if(counter == 1){
				counter--;
			}
			try {
				sleep(400);
			} catch (InterruptedException ie) {
				System.out.println(ie.getMessage());
			}
		}
	}
}
