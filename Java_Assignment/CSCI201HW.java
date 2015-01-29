import java.lang.Math;
import java.text.DecimalFormat;
import java.util.Scanner;

abstract class MathObj{
	protected double x, y, z;
	protected String type;
	
	public MathObj(double x, double y, double z, String type){
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}
	
	public abstract void add(MathObj m);
	
	public abstract void subtract(MathObj m);
	
	public abstract void angle(MathObj m);
	
	public abstract String toString();
}

class Vector extends MathObj{
	public Vector(double x, double y, double z){
		super(x, y, z, "Vector");
	}
	
	public void add(MathObj m){
		if(m.type == "Vector"){
			MathObj v = new Vector(x + m.x, y + m.y, z + m.z);
			System.out.println("The result is " + v.toString());
		}
		else{
			System.out.println("Cannot add a " + m.type +" to a Vector!");
		}
	}
	
	public void subtract(MathObj m){
		if(m.type == "Vector"){
			MathObj v = new Vector(x - m.x, y - m.y, z - m.z);
			System.out.println("The result is " + v.toString());
		}
		else{
			System.out.println("Cannot subtract a " + m.type +" from a Vector!");
		}
	}
	
	public void angle(MathObj m){
		if(m.type == "Vector"){
			double La = length(x, y, z);
			double Lb = length(m.x, m.y, m.z);
			if(La == 0.0 || Lb == 0.0){
				System.out.println("Cannot find an angle between vectors where one or both vectors' length are zero!");
			}
			else{
				double degree = Math.toDegrees(Math.acos(dot(m)/(La * Lb)));
				DecimalFormat numFormat = new DecimalFormat("#.00");
				System.out.println("The angle between them is " + numFormat.format(degree) + " degrees");
			}
		}
		else{
			System.out.println("Cannot find an angle between a " + m.type +" and a Vector!");
		}
	}
	
	public String toString(){
		return "Vector:<" + x + "," + y  + "," + z + ">";
	}
	
	private double length(double x, double y, double z){
		return Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0) + Math.pow(z, 2.0));
	}
	
	private double dot(MathObj m){
		return (x*m.x + y*m.y + z*m.z);
	}
}

class Point extends MathObj{

	public Point(double x, double y, double z) {
		super(x, y, z, "Point");
	}

	public void add(MathObj m) {
		if(m.type == "Vector"){
			MathObj p = new Point(x + m.x, y + m.y, z + m.z);
			System.out.println("The result is " + p.toString());
		}
		else{
			System.out.println("Cannot add a " + m.type +" to a Point!");
		}
	}

	public void subtract(MathObj m) {
		if(m.type == "Vector"){
			MathObj p = new Point(x - m.x, y - m.y, z - m.z);
			System.out.println("The result is " + p.toString());
		}
		else if(m.type == "Point"){
			MathObj v = new Vector(x - m.x, y - m.y, z - m.z);
			System.out.println("The result is " + v.toString());
		}
		else{
			System.out.println("Cannot subtract a " + m.type +" from a Point!");
		}
	}

	public void angle(MathObj m) {
		System.out.println("Cannot find an angle between a " + m.type +" and a Point!");
	}

	public String toString() {
		return "Point:<" + x + "," + y  + "," + z + ">";
	}
	
}

public class CSCI201HW{
	private static double[] result;
	private static Scanner in;
	
	public static boolean parseInput(){
		in = new Scanner(System.in);
		String text = "";

		text = in.nextLine();
		if(text.charAt(0) == '<' && text.charAt(text.length()-1) == '>'){
			String[] temp = text.split("<|>");
				
			if(temp.length == 0){
				System.out.println("Nothing inside the brackets!");
			}
			else{
				String[] temp2 = temp[1].split(",");
				
				if(temp2.length > 3){
					System.out.println("Too many parameters!");
				}
				else{
					result = new double[3];
					result[0] = result[1] = result[2] = 0.0;
					try{
						for(int i = 0; i < temp2.length; i++){
							result[i] = Double.parseDouble(temp2[i]);
						}
						return true;
							
					}catch(NumberFormatException e){
						System.out.println("Elements must be doubles!");
					}
				}
			}
		}
		else{
			System.out.println("Invalid input!");
		}
		return false;
	} 
	
	public static void main(String[] arg){

		MathObj object1 = null;
		MathObj object2 = null;
		
		while(true){
			System.out.println();
			if(object1 == null){
				System.out.println("Object1 has not been assigned.");
			}
			if(object2 == null){
				System.out.println("Object2 has not been assigned.");
			}
			System.out.println("1: Change value of " + object1);
			System.out.println("2: Change value of " + object2);
			System.out.println("3: Add the objects.");
			System.out.println("4: Subtract the objects.");
			System.out.println("5: Find the angle between objects.");
			System.out.println("6: Quit.");

			in = new Scanner(System.in);
			String text = in.nextLine();
			
			if(text.equals("1") || text.equals("2")){
				System.out.println("Enter values in the following format: <x,y,z>");
				if(parseInput()){
					System.out.println("Is this a Vector or a Point?");
					in = new Scanner(System.in);
					String text2 = in.nextLine();
					text2 = text2.toUpperCase();

					if(text2.equals("VECTOR")){
						if(text.equals("1")){
							object1 = new Vector(result[0], result[1], result[2]);
						}
						else{
							object2 = new Vector(result[0], result[1], result[2]);
						}
					}
					else if(text2.equals("POINT")){
						if(text.equals("1")){
							object1 = new Point(result[0], result[1], result[2]);
						}
						else{
							object2 = new Point(result[0], result[1], result[2]);
						}
					}
					else{
						System.out.println("Invalid input!");
					}
				}
			}
			else if(text.equals("3")){
				if(object1 != null && object2 != null){
					object1.add(object2);
				}
				else{
					System.out.println("Cannot add NULL object(s)!");
				}
			}
			else if(text.equals("4")){
				if(object1 != null && object2 != null){
					object1.subtract(object2);
				}
				else{
					System.out.println("Cannot subtract NULL object(s)!");
				}
			}
			else if(text.equals("5")){
				if(object1 != null && object2 != null){
					object1.angle(object2);
				}
				else{
					System.out.println("Cannot find angle for NULL object(s)!");
				}
			}
			else if(text.equals("6")){
				break;
			}
			else{
				System.out.println("Invalid command!");
			}
		}
	}
}