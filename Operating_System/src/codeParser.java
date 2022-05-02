import java.io.*;
import java.util.*;

public class codeParser {
     private Hashtable<String, String> variables;
     
    public codeParser() {
	    this.variables =new Hashtable<>();
}
	public void print(String arg ) {
		if (variables.containsKey(arg)){
			System.out.println(variables.get(arg)+"");
		}
		else {
		System.out.println(arg+"");
		  }
	
	}
	public void assign(String arg1 ,String arg2){
		if (arg2.equals("input")) {
			System.out.println("Please enter a value");
			Scanner sc = new Scanner(System.in);
			arg2= sc.next();
		}
		if (variables.containsKey(arg2)) {
			variables.put(arg1, variables.get(arg2));
		}
		else {
			variables.put(arg1, arg2);
		}	
	}
	public String readFile(String arg) throws IOException {
		String filepath;
		if (variables.containsKey(arg))filepath=variables.get(arg);
		else filepath=arg;
		File file = new File(filepath);
		Scanner sc = new Scanner(file);
		String st = "";
		while (sc.hasNext()) {
			st+= sc.next()+"\n";
		
		}
		return st;
	}
	public void writeFile(String arg1, String arg2) {
		String filePath;
		if (variables.containsKey(arg1))filePath=variables.get(arg1);
		else filePath=arg1;
		String text;
		if (variables.containsKey(arg2))text=variables.get(arg2);
		else text=arg2;
		
	    try (PrintWriter result = new PrintWriter(filePath)) {
	            result.println(text);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
    }
	public void printFromTo(String x , String y) {
		int firstNumber;
		int secondNumber;
		if(variables.containsKey(x))firstNumber=Integer.parseInt(variables.get(x));
		else firstNumber=Integer.parseInt(x);
		if(variables.containsKey(y))secondNumber=Integer.parseInt(variables.get(y));
		else secondNumber=Integer.parseInt(y);	
		for (int i = firstNumber+1 ; i<=secondNumber;i++) {
			System.out.println(i);
		}
	}
	
}
