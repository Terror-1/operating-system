import java.io.*;
import java.util.*;

public class codeParser {

	public void parseInput(String arg,process p,memory memory) throws FileNotFoundException {
		// convert the the program into parse tree stored in stack of the queue
		File file = new File(arg);
		Scanner sc = new Scanner(file);
		String st;
		////////////////////////should be removed//////////////////////
		
		int counter=p.getPcb().getStartBound()+7;
		while(sc.hasNext()) {
			int instCount=0;
			Stack<String> temp = new Stack<>();
			st= sc.nextLine();
			String stringBuilder[] = st.split(" ");
			for(int i = 0 ; i <stringBuilder.length;i++) {
				temp.push(stringBuilder[i]);
			}
			memory.getMemory()[counter]=temp;
			instCount++;
			counter++;
			p.setInstCount(instCount);
			//p.instructions.add(temp);	
		}
			
			
		
		
	}
	public void parseInputString(String arg,process p,memory memory) throws FileNotFoundException {
		// convert the the program into parse tree stored in stack of the queue
		File file = new File(arg);
		Scanner sc = new Scanner(file);
		String st;		
		int counter=p.getPcb().getStartBound();
		while(sc.hasNext()) {
			if (counter >p.getPcb().getStartBound()+6) {
				Stack<String> temp = new Stack<>();
				st= sc.nextLine();
				String stringBuilder[] = st.split(" ");
				for(int i = 0 ; i <stringBuilder.length;i++) {
					temp.push(stringBuilder[i]);
				}
				memory.getMemory()[counter]=temp;
			}
		
			else if (counter==p.getPcb().getStartBound()+1)  {
				st=sc.nextLine();
				memory.getMemory()[counter]=processStatus.READY;
			}
			else if (counter==p.getPcb().getStartBound()||counter==p.getPcb().getStartBound()+2){
				st=sc.nextLine();
				memory.getMemory()[counter]=Integer.parseInt(st);
			}
			else {
				memory.getMemory()[counter]=sc.nextLine();
			}
			
			counter++;
			
		}
			
			
		
		
	}

}
