import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class MyJLabel extends JLabel{

	private static final long serialVersionUID = 1;
	ImageIcon front_image = null;
	ImageIcon back_image = null;
	int mod = 14;
	int pos = 7;
	
	MyJLabel(){
		super();
	}
	
	MyJLabel(String text){
		super(text);
	}
	
	MyJLabel(ImageIcon img){
		super(img);
		front_image = img;
	}
	
	MyJLabel(String text, int pos){
		super(text, pos);
	}
	
	public void setImage(ImageIcon image){
		this.back_image = image;
	}
	
	public void setLabel(ImageIcon image){
		this.front_image = image;
	}
	
	public void setSizes(int mod, int pos){
		this.mod = mod;
		this.pos = pos;
	}
	
	public ImageIcon getImage(){
		return front_image;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(front_image != null && back_image != null){
			g.drawImage(back_image.getImage(), 0, 0, this.getSize().width, this.getSize().height,null);
			g.drawImage(front_image.getImage(), pos, pos, this.getSize().width - mod, this.getSize().height - mod,null);
		}
	}
}
