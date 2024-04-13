import java.util.Scanner;

public class Lab2_3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("How many mark you want to insert: ");
		int n = sc.nextInt();
		
		float total = 0;
		for(int i=1; i<=n; i++) {
			System.out.println("Please enter mark no "+i);
			float mark = sc.nextFloat();
			total += mark;
			sc.close();
			System.out.println("Mark: "+mark+" has been inserted");
		}
		
		// Display result
		System.out.println("Total mark is: "+total);
	}

}
