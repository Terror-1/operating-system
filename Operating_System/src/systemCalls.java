import java.io.*;
import java.util.*;

public class systemCalls {
    String readFromMemory="";
	public systemCalls() {
	}
	  public boolean contains(int varIdx ,String arg, memory memory) {
			for (int i=varIdx ; i < varIdx+3 ; i++) {
				String n[] = (memory.getMemory()[i]+"").split(" ");
				Pair temp = new Pair(n[3],n[n.length-1]);
				if (temp.getX().equals(arg)) {
					readFromMemory=temp.getY();
					return true;
				}
			}

			return false;
		}
    public void print(String arg ,int varIdx,memory memory) {
		if (this.contains(varIdx, arg, memory)){
			System.out.println(readFromMemory+"");
		}
		else {
		System.out.println("printed value : "+arg+"");
		  }
	
	}
	public String readFile(String arg,int varIdx ,memory memory)  {
		String filepath;
		if (this.contains(varIdx, arg, memory))filepath=System.getProperty("user.dir")+"\\Src\\"+readFromMemory+".txt";
		else filepath=arg;
		System.err.println(readFromMemory);
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
	
	public void writeFile(String arg1, String arg2,int varIdx,memory memory) throws IOException {
		String fileName;
		if (this.contains(varIdx, arg1, memory))fileName=readFromMemory;
		else fileName=arg1;
		String text;
		if (this.contains(varIdx, arg2, memory))text=readFromMemory;
		else text=arg2;
		String filePath=System.getProperty("user.dir")+"\\Src\\"; 
		createFile(filePath,fileName);
		FileWriter myWriter = new FileWriter(filePath+"\\"+fileName+".txt");
		myWriter.write(text);
		myWriter.close();
		System.out.println("File created and writed on it");
		
  }
	public void writeFile(String arg1, String arg2) throws IOException {
		String fileName=arg1;
		String text=arg2;
		String filePath=System.getProperty("user.dir")+"\\Src\\"; 
		createFile(filePath,fileName);
		FileWriter myWriter = new FileWriter(filePath+"\\"+fileName+".txt");
		myWriter.write(text);
		myWriter.close();
		
  }
	public void EmptyDisk() throws IOException {
		String fileName="Disk";
		String Empty="";
		for (int i=0 ; i<20 ; i++) {
			Empty+="null" +"\n";
		}
		String text=Empty;
		String filePath=System.getProperty("user.dir")+"\\Src\\"; 
		createFile(filePath,fileName);
		FileWriter myWriter = new FileWriter(filePath+"\\"+fileName+".txt");
		myWriter.write(text);
		myWriter.close();
		
  }
	public static void createFile(String arg , String fileName) throws IOException {
		File file = new File(arg+"\\"+fileName+".txt");
		file.createNewFile();		
	}
	public void printFromTo(String x , String y,int varIdx,memory memory) {
		memory.print();
		int firstNumber;
		int secondNumber;
		if(this.contains(varIdx,x, memory))firstNumber=Integer.parseInt(readFromMemory);
		else firstNumber=Integer.parseInt(x);
		if(this.contains(varIdx, y, memory))secondNumber=Integer.parseInt(readFromMemory);
		else secondNumber=Integer.parseInt(y);	
		
		if(firstNumber<secondNumber)swap(firstNumber, secondNumber);
		
		System.out.print("values between " +firstNumber + " and "+ secondNumber+" >>> ");
		for (int i = firstNumber+1 ; i<secondNumber;i++) {
			System.out.print(i+ " ");
		}
		System.out.println();
	}
	public void writeToMemory(Pair arg, int varIdx , memory memory) {
		memory.getMemory()[varIdx]=arg;
	}
    public String takeInput() {
	  Scanner sc = new Scanner(System.in);
	  System.out.println("Please enter a value");
	  return sc.next();
  }
    public static void swap(int x,int y) {
	  int temp = x;
	  x=y;
	  y=temp;
  }
}
