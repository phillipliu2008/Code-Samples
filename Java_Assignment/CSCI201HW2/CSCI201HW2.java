import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class CSCI201HW2 extends JFrame{

	private static final long serialVersionUID = 1;

	// Leaderboard
	public static String[][] highScore = new String[10][4];
	
	//Gameboard
	public static String[][] gameBoard = new String[10][10];
	
	//GUI Board
	public static JPanel mainPanel = new JPanel();
	public static JPanel scores = new JPanel();
	
	public CSCI201HW2(){
		super("Battleship");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = getGLPanel();
		scores = getBOXPanel();
		add(mainPanel, BorderLayout.CENTER);
		add(scores, BorderLayout.EAST);
		setVisible(true);
	}
	
	private JPanel getGLPanel(){
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(11, 11));
		for(int i = 0; i < 121; i++){
			JLabel jl = new JLabel("?");
			jp.add(jl);
		}
		return jp;
	}
	
	private JPanel getBOXPanel(){
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		for(int i = 0; i < 11; i++){
			JLabel jl = new JLabel("scores");
			jp.add(jl);
		}
		return jp;
	}
	
	@SuppressWarnings("resource")
	private void gameStart(){
		Utility util = new Utility();
		String cmd;
		Scanner in;
		int counter = 1;
		int[] shipCounter = {0, 0, 0, 0, 0};
		
		
		while(shipCounter[0] < 5 || shipCounter[1] < 4 || shipCounter[2] < 3 ||
				shipCounter[3] < 2 || shipCounter[4] < 2){
			System.out.print("Turn " + counter + " - Please Enter a Coordinate: ");
			in = new Scanner(System.in);
			cmd = in.nextLine();
			
			try{
				char rowChar = cmd.charAt(0);
				int row = util.letterToNumber(rowChar);
				cmd = cmd.substring(1);
				int col = Integer.parseInt(cmd) - 1;
				
				//System.out.println("Row: " + row + " Col: " + col);
				
				if(cmd.length() > 3 || row > 9 || col > 9){
					System.out.println("That's an invalid Coordinate, try again!");
				}
				else{
					counter++;
					JLabel jl = (JLabel) mainPanel.getComponent(row * 11 +  col + 1);
					if(!jl.getText().equals("?")){
						counter--;
						throw new Exception();
					}
					else if(gameBoard[row][col].equals("X")){
						jl.setText("MISS!");
						System.out.println("You Missed!");
					}
					else if(gameBoard[row][col].equals("A")){
						jl.setText("A");
						shipCounter[0]++;
						if(shipCounter[0] >= 5){
							System.out.println("You have sunken an Aircraft Carrier!");
						}
						else{
							System.out.println("You hit an Aircraft Carrier!");
						}
					}
					else if(gameBoard[row][col].equals("B")){
						jl.setText("B");
						shipCounter[1]++;
						if(shipCounter[1] >= 4){
							System.out.println("You have sunken a Battleship!");
						}
						else{
							System.out.println("You hit a Battleship!");
						}
					}
					else if(gameBoard[row][col].equals("C")){
						jl.setText("C");
						shipCounter[2]++;
						if(shipCounter[2] >= 3){
							System.out.println("You have sunken a Cruiser!");
						}
						else{
							System.out.println("You hit a Cruiser!");
						}
					}
					else if(gameBoard[row][col].equals("D")){
						jl.setText("D");
						if(shipCounter[3] < 2){
							shipCounter[3]++;
							if(shipCounter[3] >= 2){
								System.out.println("You have sunken a Destroyer!");
							}
							else{
								System.out.println("You hit a Destroyer!");
							}
						}
						else if(shipCounter[4] < 2){
							shipCounter[4]++;
							if(shipCounter[4] >= 2){
								System.out.println("You have sunken a Destroyer!");
							}
							else{
								System.out.println("You hit a Destroyer!");
							}
						}
					}
				}
			}
			catch (Exception e){
				System.out.println("Invalid Input! Please Try Again.");
			}
		}
		System.out.println("You Sank All the Ships!");
		System.out.print("Please Enter Your Name: ");
		in = new Scanner(System.in);
		cmd = in.nextLine();
		
		this.remove(mainPanel);
		this.remove(scores);
		
		updateScore(cmd, counter-1);
		JLabel playerMsg = new JLabel("Thanks for Playing, " + cmd +" !");
		JLabel scoreMsg = new JLabel("Your Score: " + (counter-1));
		
		setLayout(new GridLayout(3,3));
		add(new JLabel());
		add(playerMsg, BorderLayout.NORTH);
		add(new JLabel());
		add(new JLabel());
		add(scores);
		add(new JLabel());
		add(new JLabel());
		add(scoreMsg, BorderLayout.SOUTH);
		add(new JLabel());

		revalidate();
		repaint();
	}
	
	private void updateScore(String name, int score){
		for(int i = 0; i < highScore.length; i++){
			if(highScore[i][3] == null){
				highScore[i][1] = name;
				highScore[i][2] = "-";
				highScore[i][3] = Integer.toString(score);
				break;
			}
			else if(score < Integer.parseInt(highScore[i][3])){
				String temp_name = highScore[i][1];
				int temp_score = Integer.parseInt(highScore[i][3]);
				highScore[i][1] = name;
				highScore[i][3] = Integer.toString(score);
				name = temp_name;
				score = temp_score;
			}
		}
		// Update Leaderboard
		JLabel jl = (JLabel) scores.getComponent(0);
		jl.setText("Highscores: ");
		
		for(int i = 1; i < 11; i++){
			String temp = "";
			for(int j = 0; j < highScore[i-1].length; j++){
				if(highScore[i-1][j] == null || highScore[i-1][j].equals("")){
					break;
				}
				temp += highScore[i-1][j] + " ";
			}
			JLabel jlabel = (JLabel) scores.getComponent(i);
			jlabel.setText(temp);
		}
	}
	
	private static void writeFile(String filename){
		try {
			FileWriter fw = new FileWriter(filename);
			PrintWriter pw = new PrintWriter(fw);
			
			// Writing Leaderboard
			pw.println("Highscores:");
			for(int i = 0; i < 10; i++){
				String line = "";
				for(int j = 0; j < 4; j++){
					if(highScore[i][j] == null){
						break;
					}
					line = line + highScore[i][j] + " ";
				}
				pw.println(line);
			}
			
			// Writing Gameboard
			for(int i = 0; i < 10; i++){
				String line = "";
				for(int j = 0; j < 10; j++){
					line = line + gameBoard[i][j];
				}
				pw.println(line);
			}
			pw.close();	
		} 
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
			System.exit(1);
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args){
		String fileName;
		
		if(args.length != 0){
			fileName = args[0];
		}
		else{
			System.out.print("Please Enter the File Name: ");
			Scanner in = new Scanner(System.in);
			fileName = in.nextLine();
		}
		
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			
			// Create Leaderboard
			for(int i = 0; i < 10; i++){	
				String temp = br.readLine();
				if(temp.toLowerCase().equals("highscores:")){
					temp = br.readLine();
				}
				String[] splited = temp.split("\\s");
				if(splited.length > 4){
					throw new Exception();
				}
				for(int j = 0; j < splited.length; j++){  // Empty space is null!!!
					highScore[i][j] = splited[j];
				}
			}
			
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
					gameBoard[i][j] = Character.toString(symbol);
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
				copy[i][j] = gameBoard[i][j];
			}
		}
		
		// Check Ships' Corrdinates
		util.findShip(copy);
		
		// Start GUI
		CSCI201HW2 hw2 = new CSCI201HW2();
		
		// Update Leaderboard
		JLabel jl = (JLabel) scores.getComponent(0);
		jl.setText("Highscores: ");
		
		for(int i = 1; i < 11; i++){
			String temp = "";
			for(int j = 0; j < highScore[i-1].length; j++){
				if(highScore[i-1][j] == null || highScore[i-1][j].equals("")){
					break;
				}
				temp += highScore[i-1][j] + " ";
			}
			JLabel jlabel = (JLabel) scores.getComponent(i);
			jlabel.setText(temp);
		}
		
		// Update Gameboard
		JLabel jl1 = (JLabel) mainPanel.getComponent(110);
		jl1.setText("");
		
		char alphabet = 'A';
		for(int i = 0; i < 10; i++){
			JLabel jlabel1 = (JLabel) mainPanel.getComponent(i * 11);
			JLabel jlabel2 = (JLabel) mainPanel.getComponent(111 + i);
			jlabel1.setText(Character.toString(alphabet));
			jlabel2.setText(Integer.toString(i+1));
			alphabet++;
		}
		
		// Start Game
		hw2.gameStart();
		writeFile(fileName);
	}
	
}
