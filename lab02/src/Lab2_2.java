
import java.util.Scanner;

public class Lab2_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		
		// Get 1st number
		System.out.println("Insert your first number");
		float num1 = scanner.nextInt();
		
		// Get 2nd number
		System.out.println("Insert your second number");
		float num2 = scanner.nextInt();
		
		scanner.close();
		
		// Display sum
		System.out.println("The sum of 2 value is: "+(num1 + num2));
	}

}
