import java.io.*;
import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

public class codeParser {

	public void parseInput(String arg,PCB pcb,memory memory) throws FileNotFoundException {
		// convert the the program into parse tree stored in stack of the queue
		File file = new File(arg);
		Scanner sc = new Scanner(file);
		String st;
		////////////////////////should be removed//////////////////////
		
		int counter=pcb.getStartBound()+7;
		while(sc.hasNext()) {
			Stack<String> temp = new Stack<>();
			st= sc.nextLine();
			String stringBuilder[] = st.split(" ");
			for(int i = 0 ; i <stringBuilder.length;i++) {
				temp.push(stringBuilder[i]);
			}
			memory.getMemory()[counter]=temp;
			counter++;
		
		}
			
			
		
		
	}
	public void parseInputString(String arg,int loadPos,memory memory) throws FileNotFoundException {
		// convert the the program into parse tree stored in stack of the queue
		File file = new File(arg);
		Scanner sc = new Scanner(file);
		String st;		
		int counter=loadPos;
		while(sc.hasNext()) {
			if (counter >loadPos+6) {
				Stack<String> temp = new Stack<>();
				st= sc.nextLine();
				String stringBuilder[] = st.split(" ");
				for(int i = 0 ; i <stringBuilder.length;i++) {
					temp.push(stringBuilder[i]);
				}
				memory.getMemory()[counter]=temp;
			}
		
			else if (counter==loadPos+1)  {
				st=sc.nextLine();
				if (st.equals("STATUS IS READY")){
					memory.getMemory()[counter]=processStatus.READY;
				}
				else if (st.equals("STATUS IS BLOCKED") )memory.getMemory()[counter]=processStatus.BLOCKED;
			}
			else if (counter==loadPos||counter==loadPos+2){
				st=sc.nextLine();
				if(st.equals(null)) {
					this.isEmpty(loadPos,memory);
					break;
				}
				memory.getMemory()[counter]=Integer.parseInt(st);
			}
			else {
				memory.getMemory()[counter]=sc.nextLine();
			}
			
			counter++;
			
		}
		
			
			
		
		
	}
	public void isEmpty(int start , memory memory) {
		for (int i=start ; i<start+20;i++) {
			memory.getMemory()[i]=null;
		}
	}

}
