import java.io.*;

public class Lab2_5 extends FileReader
{
	File file;
	char [] content = new char[1000];
	Lab2_5(File file) throws Exception
	{
		super(file);
		this.file = file;
	}

	Lab2_5(String filename) throws Exception
	{
		super(filename);
		file = new File(filename);
	}

	public String readContent() throws Exception
	{
		String content = "";
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		while(br.readLine() != null) {
			String temp = br.readLine();
			content = content.concat(temp);
		}
		
		br.close();
		return content;
	
	}

	@SuppressWarnings("resource")
	public static void main(String [] args) throws 
     Exception
	{
		Lab2_5 reader = new Lab2_5("Welcome.txt");
		System.out.println(reader.readContent());

	     File file = new File("Welcome.txt");
	     Lab2_5 reader2 = new Lab2_5(file);
		System.out.println(reader2.readContent());
	}

}

