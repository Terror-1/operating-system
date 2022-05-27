import java.io.*;
import java.util.*;

public class codeParser {

	public void parseInput(String arg,process p,memory memory) throws FileNotFoundException {
		// convert the the program into parse tree stored in stack of the queue
		File file = new File(arg);
		Scanner sc = new Scanner(file);
		String st;
		////////////////////////should be removed//////////////////////
		
		while(sc.hasNext()) {
			int counter=p.getPcb().getStartBound()+4;
			int instCount=0;
			Stack<String> temp = new Stack<>();
			st= sc.nextLine();
			String stringBuilder[] = st.split(" ");
			for(int i = 0 ; i <stringBuilder.length;i++) {
				temp.push(stringBuilder[i]);
			}
			////////////use Write to memory ///////////////////////
			memory.getMemory()[counter]=temp;
			instCount++;
			counter++;
			p.setInstCount(instCount);
			//p.instructions.add(temp);	
		}
			
			
		
		
	}
}
