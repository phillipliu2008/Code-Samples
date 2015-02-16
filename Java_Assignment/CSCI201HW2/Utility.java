import java.util.ArrayList;


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
}
