import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class ShipSelection extends JDialog{

	private static final long serialVersionUID = 1;
	
	public JComboBox<String> jcb;
	public JLabel comboName = new JLabel("Select Ship: ");
	
	public ButtonGroup bg = new ButtonGroup();
	public JRadioButton north = new JRadioButton("North", true);
	public JRadioButton south = new JRadioButton("South");
	public JRadioButton east = new JRadioButton("East");
	public JRadioButton west = new JRadioButton("West");
	public String currentDir = "North";
	
	public JButton placeShip = new JButton("Place Ship");
	
	public Utility util = new Utility();

	public ShipSelection(Battleship jframe, String location){
		super(jframe, "Select Ship at " + location, true);
		setSize(300, 200);
		setLocationRelativeTo(jframe);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		placeShip.setEnabled(false);
		
		ArrayList<String> shipTypes= new ArrayList<String>(); // {"Aircraft Carrier", "Battleship", "Cruiser", "Destroyer"};
		if(jframe.aLabels.isEmpty()){
			shipTypes.add("Aircraft Carrier");
		}
		if(jframe.bLabels.isEmpty()){
			shipTypes.add("Battleship");
		}
		if(jframe.cLabels.isEmpty()){
			shipTypes.add("Cruiser");
		}
		if(jframe.d1Labels.isEmpty() || jframe.d2Labels.isEmpty()){
			shipTypes.add("Destroyer");
		}
		String[] shipArr = new String[shipTypes.size()];
		shipArr = shipTypes.toArray(shipArr);
		jcb = new JComboBox<String>(shipArr);
		
		bg.add(north);
		bg.add(south);
		bg.add(east);
		bg.add(west);
		
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		JPanel jp4 = new JPanel();
		jp1.setLayout(new BoxLayout(jp1, BoxLayout.X_AXIS));
		jp1.add(Box.createGlue());
		jp1.add(comboName);
		jp1.add(jcb);
		jp1.add(Box.createGlue());
		
		jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));
		jp2.add(Box.createGlue());
		jp2.add(north);
		jp2.add(south);
		jp2.add(Box.createGlue());
		
		jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));
		jp3.add(Box.createGlue());
		jp3.add(east);
		jp3.add(west);
		jp3.add(Box.createGlue());
		
		jp4.setLayout(new BoxLayout(jp4, BoxLayout.Y_AXIS));
		jp4.add(jp2);
		jp4.add(jp3);
		
		setLayout(new BorderLayout());
		add(jp1, BorderLayout.NORTH);
		add(jp4, BorderLayout.CENTER);
		add(placeShip, BorderLayout.SOUTH);
		
		
		north.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(north.isSelected()){
					currentDir = "North";
					if(util.checkSelection(jframe.userPanel, jframe.curIndex, jframe.user_gameBoard, (String) jcb.getSelectedItem(), "North", false)){
						placeShip.setEnabled(true);
					}
					else{
						placeShip.setEnabled(false);
					}
				}
			}
		});
		
		south.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(south.isSelected()){
					currentDir = "South";
					if(util.checkSelection(jframe.userPanel, jframe.curIndex, jframe.user_gameBoard, (String) jcb.getSelectedItem(), "South", false)){
						placeShip.setEnabled(true);
					}
					else{
						placeShip.setEnabled(false);
					}
				}
			}
		});
		
		east.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(east.isSelected()){
					currentDir = "East";
					if(util.checkSelection(jframe.userPanel, jframe.curIndex, jframe.user_gameBoard, (String) jcb.getSelectedItem(), "East", false)){
						placeShip.setEnabled(true);
					}
					else{
						placeShip.setEnabled(false);
					}
				}
			}
		});
		
		west.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(west.isSelected()){
					currentDir = "West";
					if(util.checkSelection(jframe.userPanel, jframe.curIndex, jframe.user_gameBoard, (String) jcb.getSelectedItem(), "West", false)){
						placeShip.setEnabled(true);
					}
					else{
						placeShip.setEnabled(false);
					}
				}
			}
		});
		
		jcb.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent ie) {
				if(util.checkSelection(jframe.userPanel, jframe.curIndex, jframe.user_gameBoard, (String) ie.getItem(), currentDir, false)){
					placeShip.setEnabled(true);
				}
				else{
					placeShip.setEnabled(false);
				}
			}
		});
		
		placeShip.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent ae) {
				String shipType = (String) jcb.getSelectedItem();
				util.checkSelection(jframe.userPanel, jframe.curIndex, jframe.user_gameBoard, shipType, currentDir, true);
				
				if(shipType.equals("Aircraft Carrier")){
					util.addToArray(jframe.aLabels, jframe.user_gameBoard, currentDir, jframe.curIndex, "A", 5);
				}
				else if(shipType.equals("Battleship")){
					util.addToArray(jframe.bLabels, jframe.user_gameBoard, currentDir, jframe.curIndex, "B", 4);
				}
				else if(shipType.equals("Cruiser")){
					util.addToArray(jframe.cLabels, jframe.user_gameBoard, currentDir, jframe.curIndex, "C", 3);
				}
				else if(shipType.equals("Destroyer")){
					if(jframe.d1Labels.isEmpty()){
						util.addToArray(jframe.d1Labels, jframe.user_gameBoard, currentDir, jframe.curIndex, "D", 2);
					}
					else{
						util.addToArray(jframe.d2Labels, jframe.user_gameBoard, currentDir, jframe.curIndex, "D", 2);
					}
				}
				dispose();
			}
		});
		
		setVisible(true);
	}

}
