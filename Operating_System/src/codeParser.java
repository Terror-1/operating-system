import java.io.*;
import java.util.*;

public class codeParser {

	public void print(String arg ,process p) {
		if (p.variables.containsKey(arg)){
			System.out.println(p.variables.get(arg)+"");
		}
		else {
		System.out.println("printed value : "+arg+"");
		  }
	
	}
	public void assign(String arg1 ,String arg2,process p){
		if (arg2.equals("input")) {
			System.out.println("Please enter a value");
			Scanner sc = new Scanner(System.in);
			arg2= sc.next();
		}
		if (p.variables.containsKey(arg2)) {
			p.variables.put(arg1, p.variables.get(arg2));
		}
		else {
			p.variables.put(arg1, arg2);
		}
		System.out.println("assigned succesfully");
	}
	public String readFile(String arg,process p) throws IOException {
		String filepath;
		if (p.variables.containsKey(arg))filepath=System.getProperty("user.dir")+"\\Src\\"+p.variables.get(arg)+".txt";
		else filepath=arg;
		File file = new File(filepath);
		Scanner sc = new Scanner(file);
		String st = "";
		while (sc.hasNextLine()) {
			st+= sc.nextLine()+"\n";
		
		}
		return st;
	}
	
	public void writeFile(String arg1, String arg2,process p) throws IOException {
		String fileName;
		if (p.variables.containsKey(arg1))fileName=p.variables.get(arg1);
		else fileName=arg1;
		String text;
		if (p.variables.containsKey(arg2))text=p.variables.get(arg2);
		else text=arg2;
		String filePath=System.getProperty("user.dir")+"\\Src\\"; 
		createFile(filePath,arg1);
		FileWriter myWriter = new FileWriter(filePath+"\\"+fileName+".txt");
		myWriter.write(text);
		myWriter.close();
		System.out.println("File created and writed on it");
		
    }
	public void printFromTo(String x , String y,process p) {
		int firstNumber;
		int secondNumber;
		if(p.variables.containsKey(x))firstNumber=Integer.parseInt(p.variables.get(x));
		else firstNumber=Integer.parseInt(x);
		if(p.variables.containsKey(y))secondNumber=Integer.parseInt(p.variables.get(y));
		else secondNumber=Integer.parseInt(y);	
		
		if(firstNumber<secondNumber)swap(firstNumber, secondNumber);
		
		System.out.print("values between " +firstNumber + " and "+ secondNumber+" >>> ");
		for (int i = firstNumber+1 ; i<secondNumber;i++) {
			System.out.print(i+ " ");
		}
		System.out.println();
	}
	public static void createFile(String arg , String fileName) throws IOException {
		File file = new File(arg+"\\"+fileName+".txt");
		file.createNewFile();		
	}
	public static void swap(int x,int y) {
		int temp = x;
		x=y;
		y=temp;
	}
}
