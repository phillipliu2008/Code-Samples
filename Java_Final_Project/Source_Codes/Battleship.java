import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;


public class Battleship extends JFrame{

	private static final long serialVersionUID = 1;
	
	//Gameboard
	public String[][] comp_gameBoard = new String[10][10];
	public String[] user_gameBoard = new String[121];
	
	//GUI Board
	public JPanel compPanel = new JPanel();
	public JPanel userPanel = new JPanel();
	
	//Panel ArrayList
	ArrayList<MyJLabel> userArr = new ArrayList<MyJLabel>();
	ArrayList<MyJLabel> compArr = new ArrayList<MyJLabel>();
	
	//Log Message
	public JTextArea logMsg = new JTextArea(4, 30);
	public JPanel logBox = new JPanel();
	public JScrollPane jsp;
	public JScrollBar vertical;
	
	//Game State EDIT/GAME
	public String gstate = "EDIT";
	
	//Select Button
	public JButton selectFile = new JButton("Select File...");
	
	//Input File
	public File inputFile;
	public JLabel fileName = new JLabel("File: ");
	
	//Start Button
	public JButton startGame = new JButton("START");
	
	//Current JLabel Coordinates
	public int curRow;
	public int curCol;
	public int curIndex;
	
	//Ship Locations/Indexes
	ArrayList<Integer> aLabels = new ArrayList<Integer>();
	ArrayList<Integer> bLabels = new ArrayList<Integer>();
	ArrayList<Integer> cLabels = new ArrayList<Integer>();
	ArrayList<Integer> d1Labels = new ArrayList<Integer>();
	ArrayList<Integer> d2Labels = new ArrayList<Integer>();
	
	//Condition Check
	public boolean hasFile = false;
	public boolean hasPlaced = false;
	
	//Player's Move and Computer's Move
	public String pMove;
	public String cMove;
	
	//Alphabet Array
	String[] aa = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
	
	//Copy of the index of User Array
	ArrayList<Integer> userArr_copy = new ArrayList<Integer>();
	
	//Ships Destoryed/Player Win Counter
	public int wCounter = 0;
	
	//MyJLabel array counters for sinking animation
	ArrayList<MyJLabel> paCounter = new ArrayList<MyJLabel>();
	ArrayList<MyJLabel> caCounter = new ArrayList<MyJLabel>();
	ArrayList<MyJLabel> pbCounter = new ArrayList<MyJLabel>();
	ArrayList<MyJLabel> cbCounter = new ArrayList<MyJLabel>();
	ArrayList<MyJLabel> pcCounter = new ArrayList<MyJLabel>();
	ArrayList<MyJLabel> ccCounter = new ArrayList<MyJLabel>();
	ArrayList<MyJLabel> pd1Counter = new ArrayList<MyJLabel>();
	ArrayList<MyJLabel> cd1Counter = new ArrayList<MyJLabel>();
	ArrayList<MyJLabel> pd2Counter = new ArrayList<MyJLabel>();
	ArrayList<MyJLabel> cd2Counter = new ArrayList<MyJLabel>();
	
	//Locks for Destroyers
	public boolean pd1Lock = false;
	public boolean pd2Lock = false;
	public boolean cd1Lock = false;
	public boolean cd2Lock = false;
	
	//Timer
	public JLabel time = new JLabel("Time - 0:15");
	public Timers timer;
	
	//Water animation
	public WaterAnimation waterAni;
	
	//Who finished the turn
	public static boolean player = false;
	public static boolean computer = false;
	
	//Water image array
	public static ImageIcon[] waterArr = {new ImageIcon("water/water1.png"), new ImageIcon("water/water2.png")};
	
	//Explosion image array
	public static ImageIcon[] explodeArr = {new ImageIcon("explosion/expl1.png"), new ImageIcon("explosion/expl2.png"),
			new ImageIcon("explosion/expl3.png"), new ImageIcon("explosion/expl4.png"), new ImageIcon("explosion/expl5.png")};
	
	//Splash image array
	public static ImageIcon[] splashArr = {new ImageIcon("splash/splash1.png"), new ImageIcon("splash/splash2.png"),
		new ImageIcon("splash/splash3.png"), new ImageIcon("splash/splash4.png"), new ImageIcon("splash/splash5.png"),
		new ImageIcon("splash/splash6.png"), new ImageIcon("splash/splash7.png")};
	
	//Game log panel
	JPanel jp3 = new JPanel();
	
	//Round number
	public int round = 0;
	
	//Music files
	public static File cannon = new File("sounds/cannon.wav");
	public static File explode = new File("sounds/explode.wav");
	public static File sinking = new File("sounds/sinking.wav");
	public static File splash = new File("sounds/splash.wav");
	
	public Battleship(){
		super("Battleship");
		setSize(700, 560);
		setLocation(300, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		compPanel = getGLPanel();
		userPanel = getGLPanel();
		updateGameboard();
		
		userArrayPanel();
		setPlayerListener();
		
		JPanel jp = new JPanel();
		JPanel jp2 = new JPanel();
		jp3 = new JPanel();
		
		GridBagConstraints gbc= new GridBagConstraints();
		jp.setLayout(new GridBagLayout());
		gbc.ipadx = 130;
		gbc.ipady = 170;
		gbc.weightx = 0.3;
		gbc.weighty = 0.3;
		jp.add(userPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		jp.add(compPanel, gbc);
		
		jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));
		jp2.add(Box.createGlue());
		jp2.add(new JLabel("PLAYER"));
		jp2.add(Box.createGlue());
		jp2.add(time);
		jp2.add(Box.createGlue());
		jp2.add(new JLabel("COMPUTER"));
		jp2.add(Box.createGlue());

		startGame.setEnabled(false);
		// Automatically move the scroll bar to the bottom
		DefaultCaret caret = (DefaultCaret)logMsg.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		logMsg.setText("You are in Edit Mode, click to place your ships");
		logMsg.setEditable(false);
		logMsg.setLineWrap(true);
		selectFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				int returnVal;
				
				// Only accepts .battle files
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Battle Files (*.battle)","battle");
				jfc.setFileFilter(filter);
				returnVal = jfc.showOpenDialog(null);
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
					inputFile = jfc.getSelectedFile();
					fileName.setText("File: " + jfc.getSelectedFile().getName().split("\\.")[0]);
					readFile(inputFile);
					
					// Add listeners to computer labels after read file
					setComputerListener();
					hasFile = true;
				}
				if(hasFile && hasPlaced){
					startGame.setEnabled(true);
				}
				else{
					startGame.setEnabled(false);
				}
			}
		});
		
		startGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				gstate = "GAME";
				// Remove all the bottom components and replace it by a single log box
				Battleship.this.remove(jp3);
				Battleship.this.add(logBox, BorderLayout.SOUTH);
				Battleship.this.revalidate();
				copyArray();
				timer = new Timers(Battleship.this);
				timer.start();
				round++;
				logMsg.setText("***Round " + round + "***");
			}
		});
		
		jsp = new JScrollPane(logMsg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//vertical = jsp.getVerticalScrollBar();
		//vertical.setValue( vertical.getMaximum() );
		logBox.setLayout(new BorderLayout());
		logBox.add(jsp, BorderLayout.CENTER);
		logBox.setBorder(BorderFactory.createTitledBorder("Game Log"));
		
		jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));
		jp3.add(logBox);
		jp3.add(selectFile);
		jp3.add(fileName);
		jp3.add(startGame);
		
		// Set up Menu and MenuItems
		JMenuBar jmb = new JMenuBar();
		JMenu info = new JMenu("Info");
		info.setMnemonic('I');
		JMenuItem howTo = new JMenuItem("How To");
		howTo.setMnemonic('H');
		howTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		JMenuItem about = new JMenuItem("About");
		about.setMnemonic('A');
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		jmb.add(info);
		info.add(howTo);
		info.add(about);

		// Instruction Menu
		howTo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				JTextArea jta = new JTextArea();
				jta.setLineWrap(true);
				jta.setEditable(false);
				JScrollPane jsp = new JScrollPane(jta);
				
				JPanel jp = new JPanel();
				jp.setLayout(new BorderLayout());
				jp.add(jsp, BorderLayout.CENTER);
				
				JDialog jd = new JDialog();
				jd.setTitle("Battleship Instructions");
				jd.setLocationRelativeTo(Battleship.this);
				jd.setSize(350, 250);
				jd.setModal(true);
				
				try {
					FileReader fr = new FileReader("src/Battleship Rules.txt");
					BufferedReader reader = new BufferedReader(fr);
					jta.read(reader, "jta");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				jd.add(jp);
				jd.setVisible(true);
			}
		});
		
		// About Menu
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				
				JPanel jp = new JPanel();
				JPanel jp2 = new JPanel();
				JPanel jp3 = new JPanel();
				JPanel jp4 = new JPanel();
				jp.setLayout(new BorderLayout());
				jp2.setLayout(new BoxLayout(jp2, BoxLayout.X_AXIS));
				jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));
				jp4.setLayout(new BoxLayout(jp4, BoxLayout.X_AXIS));
				
				JDialog jd = new JDialog();
				jd.setTitle("About");
				jd.setLocationRelativeTo(Battleship.this);
				jd.setSize(350, 250);
				jd.setModal(true);
				
				jp2.add(Box.createGlue());
				jp2.add(new JLabel("CSCI 201 USC: Assignment 4"));
				jp2.add(Box.createGlue());
				
				jp3.add(Box.createGlue());
				jp3.add(new JLabel("Made by Yifei Liu"));
				jp3.add(Box.createGlue());
				
				jp4.add(Box.createGlue());
				jp4.add(new JLabel("4/2/2015"));
				jp4.add(Box.createGlue());
				
				jp.add(jp2, BorderLayout.NORTH);
				jp.add(jp3, BorderLayout.CENTER);
				jp.add(jp4, BorderLayout.SOUTH);
	
				jd.add(jp);
				jd.setVisible(true);
			}
		});
		
		add(jp, BorderLayout.CENTER);
		add(jp2, BorderLayout.NORTH);
		add(jp3, BorderLayout.SOUTH);
		setJMenuBar(jmb);
		setVisible(true);
		
		waterAni = new WaterAnimation(this);
		waterAni.start();
	}
	
	private JPanel getGLPanel(){
		// Fill in the Gameboard with "?"
		ImageIcon label = new ImageIcon("images/Q.png");
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(11, 11));
		for(int i = 0; i < 121; i++){
			MyJLabel jl;
			if(i % 11 != 0 && i < 110){
				 jl = new MyJLabel(label);
				 jl.setBorder(BorderFactory.createLineBorder(Color.RED));
			}
			else{
				jl = new MyJLabel("", SwingConstants.CENTER);
			}
			jl.setOpaque(true);
			jl.setBackground(Color.YELLOW);
			jp.add(jl);
		}
		return jp;
	}
	
	private void userArrayPanel(){
		// Copy the compoments from JPanel to ArrayList and initialize user_gameBoard with "X"
		userArr.clear();
		for(int i = 0; i < 121; i++){
			userArr.add((MyJLabel) userPanel.getComponent(i));
			user_gameBoard[i] = "X";
		}
	}
	
	private void compArrayPanel(){
		// Copy the compoments from JPanel to ArrayList
		compArr.clear();
		for(int i = 0; i < 121; i++){
			compArr.add((MyJLabel) compPanel.getComponent(i));
		}
	}
	
	private void updateGameboard(){
		// Update Gameboard with alphabets and numbers
		char alphabet = 'A';
		for(int i = 0; i < 10; i++){
			MyJLabel jlabel1 = (MyJLabel) compPanel.getComponent(i * 11);
			MyJLabel jlabel2 = (MyJLabel) compPanel.getComponent(111 + i);
			MyJLabel jlabel3 = (MyJLabel) userPanel.getComponent(i * 11);
			MyJLabel jlabel4 = (MyJLabel) userPanel.getComponent(111 + i);
			jlabel1.setText(Character.toString(alphabet));
			jlabel2.setText(Integer.toString(i+1));
			jlabel3.setText(Character.toString(alphabet));
			jlabel4.setText(Integer.toString(i+1));
			alphabet++;
		}
	}
	
	private void readFile(File file){
		try {
			FileReader fr = new FileReader(file);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			
			// Create Gameboard
			for(int i = 0; i < 10; i++){
				String temp = br.readLine();
				if(temp.length() > 10){
					throw new Exception();
				}
				for(int j = 0; j < 10; j++){
					char symbol = temp.charAt(j);
					if(symbol != 'A' && symbol != 'B' && symbol != 'C' && symbol != 'D' && symbol != 'X'){
						throw new Exception();
					}
					comp_gameBoard[i][j] = Character.toString(symbol);
				}
			}
			br.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: " + e.getMessage());
			System.exit(1);
		}
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
			System.exit(1);
		}
		catch (Exception e){
			System.out.println("Exception: " + "File format is incorrect!");
			System.exit(1);
		}
		
		Utility util = new Utility();
		String[][] copy = new String[10][10];
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				copy[i][j] = comp_gameBoard[i][j];
			}
		}
		// Check Ships' Corrdinates
		util.findShip(copy);
		compArrayPanel();
	}
	
	
	private void setPlayerListener(){
		for(int i = 0; i < 110; i++){
			if(i % 11 != 0){
				MyJLabel jl = (MyJLabel) userPanel.getComponent(i);
				jl.addMouseListener(new MouseAdapter(){
					
					public void mouseClicked(MouseEvent me){
						if(gstate.equals("EDIT")){
							MyJLabel jl = (MyJLabel) me.getComponent();
							int index = userArr.indexOf(jl);
							curRow = index / 11;
							curCol = index % 11 - 1;
							curIndex = index;
							pMove = aa[curRow] + (curCol + 1);
							
							// Add Ship
							if(user_gameBoard[curIndex].equals("X")){
								if(aLabels.isEmpty() || bLabels.isEmpty() || cLabels.isEmpty()
									|| d1Labels.isEmpty() || d2Labels.isEmpty()){
									new ShipSelection(Battleship.this, pMove);
								}
							}
							// Remove Ship
							else{
								if(user_gameBoard[curIndex].equals("A")){
									for(int i = 0; i < aLabels.size(); i++){
										MyJLabel jl2 = (MyJLabel) userPanel.getComponent(aLabels.get(i));
										jl2.setLabel(new ImageIcon("images/Q.png"));
										jl2.repaint();
										user_gameBoard[aLabels.get(i)] = "X";
									}
									aLabels.clear();
								}
								else if(user_gameBoard[curIndex].equals("B")){
									for(int i = 0; i < bLabels.size(); i++){
										MyJLabel jl2 = (MyJLabel) userPanel.getComponent(bLabels.get(i));
										jl2.setLabel(new ImageIcon("images/Q.png"));
										jl2.repaint();
										user_gameBoard[bLabels.get(i)] = "X";
									}
									bLabels.clear();
								}
								else if(user_gameBoard[curIndex].equals("C")){
									for(int i = 0; i < cLabels.size(); i++){
										MyJLabel jl2 = (MyJLabel) userPanel.getComponent(cLabels.get(i));
										jl2.setLabel(new ImageIcon("images/Q.png"));
										jl2.repaint();
										user_gameBoard[cLabels.get(i)] = "X";
									}
									cLabels.clear();
								}
								else if(user_gameBoard[curIndex].equals("D")){
									ArrayList<Integer> temp;
									if(d1Labels.contains(curIndex)){
										temp = d1Labels;
									}
									else{
										temp = d2Labels;
									}
									for(int i = 0; i < temp.size(); i++){
										MyJLabel jl2 = (MyJLabel) userPanel.getComponent(temp.get(i));
										jl2.setLabel(new ImageIcon("images/Q.png"));
										jl2.repaint();
										user_gameBoard[temp.get(i)] = "X";
									}
									temp.clear();
								}
							}
							if(aLabels.isEmpty() || bLabels.isEmpty() || cLabels.isEmpty()
									|| d1Labels.isEmpty() || d2Labels.isEmpty()){
									hasPlaced = false;
							}
							else{
								hasPlaced = true;
							}
							if(hasPlaced && hasFile){
								startGame.setEnabled(true);
							}
							else{
								startGame.setEnabled(false);
							}
						}
					}
				});
			}
		}
	}
	
	private void setComputerListener(){
		for(int i = 0; i < 110; i++){
			if(i % 11 != 0){
				MyJLabel jl = (MyJLabel) compPanel.getComponent(i);
				jl.addMouseListener(new MouseAdapter(){
					
					public void mouseClicked(MouseEvent me){
						if(gstate.equals("GAME")){
							MyJLabel jl = (MyJLabel) me.getComponent();
							int index = compArr.indexOf(jl);
							curRow = index / 11;
							curCol = index % 11 - 1;
							curIndex = index;
							
							String temp = comp_gameBoard[curRow][curCol];
							
							if(!temp.equals("PASS") && player == false){
								
								pMove = aa[curRow] + (curCol + 1);
								ExplosionAnimation ea = null;
								SplashAnimation sa = null;
								PlaySound ps = null;
								
								if(temp.equals("X")){
									ArrayList<MyJLabel> temp2 = new ArrayList<MyJLabel>(1);
									temp2.add(jl);
									sa = new SplashAnimation(temp2);
									ps = new PlaySound(splash, false);
									sa.start();
									ps.start();
									logMsg.append("\nPlayer hit " + pMove + " and missed! (" + timer.getTime() + ")");
								}
								else if(temp.equals("A")){
									ea = new ExplosionAnimation(jl, new ImageIcon("images/A.png"));
									ps = new PlaySound(explode, false);
									ea.start();
									ps.start();
									logMsg.append("\nPlayer hit " + pMove + " and hit an Aircraft Carrier! (" + timer.getTime() + ")");
									wCounter++;
									
									paCounter.add(jl);
									if(paCounter.size() == 5){
										sa = new SplashAnimation(paCounter);
										ps = new PlaySound(null, true);
										sa.setOrder(ea);
										sa.start();
										ps.start();
										logMsg.append("\nPlayer sunk Computer's Aircraft Carrier!");
									}
								}
								else if(temp.equals("B")){
									ea = new ExplosionAnimation(jl, new ImageIcon("images/B.png"));
									ps = new PlaySound(explode, false);
									ea.start();
									ps.start();
									logMsg.append("\nPlayer hit " + pMove + " and hit a Battleship! (" + timer.getTime() + ")");
									wCounter++;
									
									pbCounter.add(jl);
									if(pbCounter.size() == 4){
										sa = new SplashAnimation(pbCounter);
										ps = new PlaySound(null, true);
										sa.setOrder(ea);
										sa.start();
										ps.start();
										logMsg.append("\nPlayer sunk Computer's Battleship!");
									}
								}
								else if(temp.equals("C")){
									ea = new ExplosionAnimation(jl, new ImageIcon("images/C.png"));
									ps = new PlaySound(explode, false);
									ea.start();
									ps.start();
									logMsg.append("\nPlayer hit " + pMove + " and hit a Cruiser! (" + timer.getTime() + ")");
									wCounter++;
									
									pcCounter.add(jl);
									if(pcCounter.size() == 3){
										sa = new SplashAnimation(pcCounter);
										ps = new PlaySound(null, true);
										sa.setOrder(ea);
										sa.start();
										ps.start();
										logMsg.append("\nPlayer sunk Computer's Cruiser!");
									}
								}
								else{
									ea = new ExplosionAnimation(jl, new ImageIcon("images/D.png"));
									ps = new PlaySound(explode, false);
									ea.start();
									ps.start();
									logMsg.append("\nPlayer hit " + pMove + " and hit a Destroyer! (" + timer.getTime() + ")");
									wCounter++;
									
									findDestroyer(jl, curRow, curCol);
									if(pd1Counter.size() == 2 && pd1Lock == false){
										pd1Lock = true;
										sa = new SplashAnimation(pd1Counter);
										ps = new PlaySound(null, true);
										sa.setOrder(ea);
										sa.start();
										ps.start();
										logMsg.append("\nPlayer sunk Computer's Destroyer!");
									}
									else if(pd2Counter.size() == 2 && pd2Lock == false){
										pd2Lock = true;
										sa = new SplashAnimation(pd2Counter);
										ps = new PlaySound(null, true);
										sa.setOrder(ea);
										sa.start();
										ps.start();
										logMsg.append("\nPlayer sunk Computer's Destroyer!");
									}
								}
								
								jl.repaint();
								
								// Player just made a move
								player = true;	
								
								if(wCounter >= 16){
									// Player Wins
									timer.waitAnimationSound(sa, "You Won!");
								}
								else{
									// Change the current coordinates to "PASS" so it can't be repeatedly selected
									comp_gameBoard[curRow][curCol] = "PASS";
								}
							}
						}
					}
				});
			}
		}
	}
	
	public void computerMove(){
		
		Random rand = new Random();
		// Create a random number that's between 0 and the size of userArr_copy
		int randNum = rand.nextInt(userArr_copy.size());
		// Take out the index number (userArr_copy stores Indexes as its values)
		int index = userArr_copy.get(randNum);
		// Use that index to get JLabel from userArr
		MyJLabel jl = userArr.get(index);
		
		int curRow = index / 11;
		int curCol = index % 11 - 1;
		cMove = aa[curRow] + (curCol + 1);
		SplashAnimation sa = null;
		PlaySound ps = null;
		
		if(user_gameBoard[index].equals("X")){
			ArrayList<MyJLabel> temp = new ArrayList<MyJLabel>(1);
			temp.add(jl);
			sa = new SplashAnimation(temp);
			ps = new PlaySound(splash, false);
			sa.start();
			ps.start();
			logMsg.append("\nComputer hit " + cMove + " and missed! (" + timer.getTime() + ")");
		}
		else{
			ExplosionAnimation ea = new ExplosionAnimation(jl, new ImageIcon("images/X.png"));
			ps = new PlaySound(explode, false);
			ea.start();
			ps.start();
			if(user_gameBoard[index].equals("A")){
				aLabels.remove(aLabels.indexOf(index));
				logMsg.append("\nComputer hit " + cMove + " and hit an Aircraft Carrier! (" + timer.getTime() + ")");
				caCounter.add(jl);
				if(caCounter.size() == 5){
					sa = new SplashAnimation(caCounter);
					ps = new PlaySound(null, true);
					sa.setOrder(ea);
					sa.start();
					ps.start();
					logMsg.append("\nComputer sunk Player's Aircraft Carrier!");
				}
			}
			else if(user_gameBoard[index].equals("B")){
				bLabels.remove(bLabels.indexOf(index));
				logMsg.append("\nComputer hit " + cMove + " and hit a Battleship! (" + timer.getTime() + ")");
				cbCounter.add(jl);
				if(cbCounter.size() == 4){
					sa = new SplashAnimation(cbCounter);
					ps = new PlaySound(null, true);
					sa.setOrder(ea);
					sa.start();
					ps.start();
					logMsg.append("\nComputer sunk Player's Battleship!");
				}
			}
			else if(user_gameBoard[index].equals("C")){
				cLabels.remove(cLabels.indexOf(index));
				logMsg.append("\nComputer hit " + cMove + " and hit a Cruiser! (" + timer.getTime() + ")");
				ccCounter.add(jl);
				if(ccCounter.size() == 3){
					sa = new SplashAnimation(ccCounter);
					ps = new PlaySound(null, true);
					sa.setOrder(ea);
					sa.start();
					ps.start();
					logMsg.append("\nComputer sunk Player's Cruiser!");
				}
			}
			else if(user_gameBoard[index].equals("D")){
				if(d1Labels.contains(index)){
					d1Labels.remove(d1Labels.indexOf(index));
					cd1Counter.add(jl);
				}
				else{
					d2Labels.remove(d2Labels.indexOf(index));
					cd2Counter.add(jl);
				}
				logMsg.append("\nComputer hit " + cMove + " and hit a Destroyer! (" + timer.getTime() + ")");
				
				ps = new PlaySound(null, true);
				if(cd1Counter.size() == 2 && cd1Lock == false){
					cd1Lock = true;
					sa = new SplashAnimation(cd1Counter);
					sa.setOrder(ea);
					sa.start();
					ps.start();
					logMsg.append("\nComputer sunk Player's Destroyer!");
				}
				else if(cd2Counter.size() == 2 && cd2Lock == false){
					cd2Lock = true;
					sa = new SplashAnimation(cd2Counter);
					sa.setOrder(ea);
					sa.start();
					ps.start();
					logMsg.append("\nComputer sunk Player's Destroyer!");
				}
			}
		}

		jl.repaint();
		
		// Remove the index number so no more repeats
		userArr_copy.remove(randNum);
		
		if(aLabels.isEmpty() && bLabels.isEmpty() && cLabels.isEmpty()
				&& d1Labels.isEmpty() && d2Labels.isEmpty()){
			// Computer Wins
			timer.waitAnimationSound(sa, "You Lost!");
		}
		else if(userArr_copy.isEmpty()){
			// Tie game
			timer.waitAnimationSound(sa, "Tie Game!");
		}
	}

	private void copyArray(){
		// Extract all the vaild index numbers to userArr_copy
		for(int i = 0; i < 110; i++){
			if(i % 11 != 0){
				userArr_copy.add(i);
			}
		}
	}
	
	public void gameReset(){
		// Reset and restart the game
		timer.stopTime();
		gstate = "EDIT";
		for(int i = 0; i < 110; i++){
			if(i % 11 != 0){
				MyJLabel jl1 = userArr.get(i);
				MyJLabel jl2 = compArr.get(i);
				jl1.setLabel(new ImageIcon("images/Q.png"));
				jl2.setLabel(new ImageIcon("images/Q.png"));
			}
		}
		userArrayPanel();
		aLabels.clear();
		bLabels.clear();
		cLabels.clear();
		d1Labels.clear();
		d2Labels.clear();
		userArr_copy.clear();
		
		hasFile = false;
		hasPlaced = false;
		wCounter = 0;
		
		paCounter.clear();
		caCounter.clear();
		pbCounter.clear();
		cbCounter.clear();
		pcCounter.clear();
		ccCounter.clear();
		pd1Counter.clear();
		pd2Counter.clear();
		cd1Counter.clear();
		cd2Counter.clear();
		
		pd1Lock = false;
		pd2Lock = false;
		cd1Lock = false;
		cd2Lock = false;
		
		round = 0;
		logMsg.setText("You are in Edit Mode, click to place your ships");
		fileName.setText("File: ");

		startGame.setEnabled(false);
		player = computer = false;
		time.setText("Time - 0:15");
		
		// Reset the Game Log
		jp3.removeAll();
		jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));
		jp3.add(logBox);
		jp3.add(selectFile);
		jp3.add(fileName);
		jp3.add(startGame);
		this.add(jp3, BorderLayout.SOUTH);
		revalidate();
	}
	
	public void findDestroyer(MyJLabel mjl, int row, int col){
		if(pd1Counter.size() == 1){
			MyJLabel temp = pd1Counter.get(0);
			int index = compArr.indexOf(temp);
			int tempRow = index / 11;
			int tempCol = index % 11 - 1;
			if((row - 1 == tempRow) || (row + 1 == tempRow) || 
					(row == tempRow && col + 1 == tempCol) || (row == tempRow && col - 1 == tempCol)){
				pd1Counter.add(mjl);
			}
			else{
				pd2Counter.add(mjl);
			}
		}
		else if(pd2Counter.size() == 1){
			MyJLabel temp = pd2Counter.get(0);
			int index = compArr.indexOf(temp);
			int tempRow = index / 11;
			int tempCol = index % 11 - 1;
			if((row - 1 == tempRow) || (row + 1 == tempRow) || 
					(row == tempRow && col + 1 == tempCol) || (row == tempRow && col - 1 == tempCol)){
				pd2Counter.add(mjl);
			}
			else{
				pd1Counter.add(mjl);
			}
		}
		else if(pd1Counter.isEmpty()){
			pd1Counter.add(mjl);
		}
		else if(pd2Counter.isEmpty()){
			pd2Counter.add(mjl);
		}
	}
	
	public static void main(String[] args){
		// Start GUI
		new Battleship();
	}
	
}
