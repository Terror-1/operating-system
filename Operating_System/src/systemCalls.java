import java.io.*;
import java.util.*;

public class systemCalls {
  
	public systemCalls() {
	}
    public void print(String arg ,process p) {
		if (p.variables.containsKey(arg)){
			System.out.println(p.variables.get(arg)+"");
		}
		else {
		System.out.println("printed value : "+arg+"");
		  }
	
	}
	public String readFile(String arg,process p)  {
		String filepath;
		if (p.variables.containsKey(arg))filepath=System.getProperty("user.dir")+"\\Src\\"+readFromMemory(arg, p)+".txt";
		else filepath=arg;
		File file = new File(filepath);
		Scanner sc;
		String st = "";
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				st+= sc.nextLine()+"\n";
				
			}
		} catch (FileNotFoundException e) {
			System.out.println(" Warning!!! File not found 404 ");
			st+="File not Found";
		}
		return st;
	}
	
	public void writeFile(String arg1, String arg2,process p) throws IOException {
		String fileName;
		if (p.variables.containsKey(arg1))fileName=readFromMemory(arg1, p);
		else fileName=arg1;
		String text;
		if (p.variables.containsKey(arg2))text=readFromMemory(arg2, p);
		else text=arg2;
		String filePath=System.getProperty("user.dir")+"\\Src\\"; 
		createFile(filePath,fileName);
		FileWriter myWriter = new FileWriter(filePath+"\\"+fileName+".txt");
		myWriter.write(text);
		myWriter.close();
		System.out.println("File created and writed on it");
		
  }
	public static void createFile(String arg , String fileName) throws IOException {
		File file = new File(arg+"\\"+fileName+".txt");
		file.createNewFile();		
	}
	public void printFromTo(String x , String y,process p) {
		int firstNumber;
		int secondNumber;
		if(p.variables.containsKey(x))firstNumber=Integer.parseInt(readFromMemory(x, p));
		else firstNumber=Integer.parseInt(x);
		if(p.variables.containsKey(y))secondNumber=Integer.parseInt(readFromMemory(y, p));
		else secondNumber=Integer.parseInt(y);	
		
		if(firstNumber<secondNumber)swap(firstNumber, secondNumber);
		
		System.out.print("values between " +firstNumber + " and "+ secondNumber+" >>> ");
		for (int i = firstNumber+1 ; i<secondNumber;i++) {
			System.out.print(i+ " ");
		}
		System.out.println();
	}
    public String takeInput() {
	  Scanner sc = new Scanner(System.in);
	  System.out.println("Please enter a value");
	  return sc.next();
  }
    public void writeToMemory(String arg1, String arg2,process p) {
	  p.variables.put(arg1, arg2);
  }
    public String readFromMemory(String key,process p) {
	  return p.variables.getOrDefault(key,"Key not Found");
  }
    public static void swap(int x,int y) {
	  int temp = x;
	  x=y;
	  y=temp;
  }
}
