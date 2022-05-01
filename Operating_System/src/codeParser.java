import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class codeParser {
Hashtable<String, String> variables=new Hashtable<>();
	public void print(String arg ) {
		System.out.println(arg+"");
	
	}
	public void assign(String arg1 ,String arg2){
		if (arg2.equals("input")) {
			System.out.println("please enter a value");
			Scanner sc = new Scanner(System.in);
			arg2= sc.next();
		}
		if (variables.containsKey(arg1)) {
			variables.replace(arg1, arg2);
		}
		else {
			variables.put(arg1, arg2);
		}	
	}
	public String readFile(String arg) throws IOException {
		File file = new File(arg);
		Scanner sc = new Scanner(file);
		String st = "";
		while (sc.hasNext()) {
			st+= sc.next()+"\n";
		
		}
		return st;
	}
	private void writeFile(String filePath, String text) {
        File file = new File(filePath);
        FileWriter fr = null;
        BufferedWriter br = null;
        PrintWriter pr = null;
        try {
            // to append to file, you need to initialize FileWriter using below constructor
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            pr = new PrintWriter(br);
            pr.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pr.close();
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }S
}
