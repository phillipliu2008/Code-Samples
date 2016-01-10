import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Utility {
	private String[][] gameBoard;
	
	public int letterToNumber(char letter){
		letter = Character.toLowerCase(letter);
		int result = letter - 'a';
		return result;
	}
	
	public void findShip(String[][] gBoard){
		
		ArrayList<MyEntry<Integer, Integer>> aircraft = new ArrayList<MyEntry<Integer, Integer>>();
		ArrayList<MyEntry<Integer, Integer>> battleship = new ArrayList<MyEntry<Integer, Integer>>();
		ArrayList<MyEntry<Integer, Integer>> cruiser = new ArrayList<MyEntry<Integer, Integer>>();
		ArrayList<MyEntry<Integer, Integer>> destroyer1 = new ArrayList<MyEntry<Integer, Integer>>();
		ArrayList<MyEntry<Integer, Integer>> destroyer2 = new ArrayList<MyEntry<Integer, Integer>>();
		
		gameBoard = gBoard;
		
			for(int i = 0; i < 10; i++){
				for(int j = 0; j < 10; j++){
					
					if(gameBoard[i][j].equals("A")){
						aircraft = search("A", i, j, 5);
					}
					else if(gameBoard[i][j].equals("B")){
						battleship = search("B", i, j, 4);
					}
					else if(gameBoard[i][j].equals("C")){
						cruiser = search("C", i, j, 3);
					}
					else if(gameBoard[i][j].equals("D")){
						if(destroyer1.isEmpty()){
							destroyer1 = search("D", i, j, 2);
						}
						else{
							destroyer2 = search("D", i, j, 2);
						}
					}
				}
			}
			try{
				if(aircraft.size() != 5 || battleship.size() != 4 || cruiser.size() != 3 ||
						destroyer1.size() != 2 || destroyer2.size() != 2){
					throw new Exception();
				}
			}
			catch(Exception e){
				System.out.println("Exception: " + "File format is incorrect!");
				System.exit(1);
			}
	}
	
	private ArrayList<MyEntry<Integer, Integer>> search(String letter, int row, int col, int size){
		
		ArrayList<MyEntry<Integer, Integer>> result = new ArrayList<MyEntry<Integer, Integer>>();
		
		try{
			if(row + 1 < 10 && gameBoard[row + 1][col].equals(letter)){
				for(int k = 0; k < size; k++){
					if(!gameBoard[row + k][col].equals(letter)){
						throw new Exception();
					}
					else if(row + k < 10){
						result.add(new MyEntry<Integer, Integer>(row + k, col));
						gameBoard[row + k][col] = "X";
					}
				}
			}
			else if(col + 1 < 10 && gameBoard[row][col + 1].equals(letter)){
				for(int k = 0; k < size; k++){
					if(!gameBoard[row][col + k].equals(letter)){
						throw new Exception();
					}
					else if(col + k < 10){
						result.add(new MyEntry<Integer, Integer>(row, col + k));
						gameBoard[row][col + k] = "X";
					}
				}
			}
			if(result.size() != size){
				throw new Exception();
			}
		}
		catch(Exception e){
			System.out.println("Exception: " + "File format is incorrect!");
			System.exit(1);
		}
		return result;
	}
	
	public void applyChange(JPanel panelBoard, int in, int mod, int len, ImageIcon symbol){
		for(int i = 0; i < len; i++){
			MyJLabel jl = (MyJLabel) panelBoard.getComponent(in);
			jl.setLabel(symbol);
			jl.repaint();
			in = in + mod;
		}
	}
	
	public boolean checkSelection(JPanel panelBoard, int in, String[] gameBoard, String type, String dir, boolean place){
		int len = 0;
		int mod = 0;
		int index = in;
		int row = index / 11;
		
		ImageIcon symbol = new ImageIcon();	// image
		
		if(type.equals("Aircraft Carrier")){
			len = 5;
			symbol = new ImageIcon("images/A.png");
		}
		else if(type.equals("Battleship")){
			len = 4;
			symbol = new ImageIcon("images/B.png");
		}
		else if(type.equals("Cruiser")){
			len = 3;
			symbol = new ImageIcon("images/C.png");
		}
		else if(type.equals("Destroyer")){
			len = 2;
			symbol = new ImageIcon("images/D.png");
		}
		if(dir.equals("North")){
			mod = -11;
			for(int i = 0; i < len; i++){ 
				if(index < 110 && index > 0){
					if(gameBoard[index].equals("X")){
						index = index - 11;	// previous row
					}
					else{ return false; }
				}
				else{ return false; }
			}
		}
		else if(dir.equals("South")){
			mod = 11;
			for(int i = 0; i < len; i++){ 
				if(index < 110 && index > 0){
					if(gameBoard[index].equals("X")){
						index = index + 11;	// next row
					}
					else{ return false; }
				}
				else{ return false; }
			}
		}
		else if(dir.equals("East")){
			mod = 1;
			for(int i = 0; i < len; i++){
				if(index < 110 && index > 0){
					int col = index % 11 - 1;
					if(gameBoard[index].equals("X") && row == index / 11 && col != -1){ 
						index = index + 1; // move right
					}
					else { return false; };
				}
				else { return false; }
			}
		}
		else if(dir.equals("West")){
			mod = -1;
			for(int i = 0; i < len; i++){
				if(index < 110 && index > 0){
					int col = index % 11 - 1;
					if(gameBoard[index].equals("X") && row == index / 11 && col != -1){ 
						index = index - 1; // move left
					}
					else { return false; };
				}
				else { return false; }
			}
		}
		
		if(place){
			applyChange(panelBoard, in, mod, len, symbol);
		}
		return true;
	}
	
	public void addToArray(ArrayList<Integer> arr, String[] gameBoard, String dir, int index, String symbol, int len){
		int mod = 0;
		if(dir.equals("North")){
			mod = -11;
		}
		else if(dir.equals("South")){
			mod = 11;
		}
		else if(dir.equals("East")){
			mod = 1;
		}
		else if(dir.equals("West")){
			mod = -1;
		}
		for(int i = 0; i < len; i++){
			arr.add(index);
			gameBoard[index] = symbol;
			index = index + mod;
			
		}
	}
}
