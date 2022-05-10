import java.io.*;
import java.util.*;

public class codeParser {

	public void parseInput(String arg,process p) throws FileNotFoundException {
		// convert the the program into parse tree stored in stack of the queue
		File file = new File(arg);
		Scanner sc = new Scanner(file);
		String st;
		while(sc.hasNext()) {
			Stack<String> temp = new Stack<>();
			st= sc.nextLine();
			String stringBuilder[] = st.split(" ");
			for(int i = 0 ; i <stringBuilder.length;i++) {
				temp.push(stringBuilder[i]);
			}
			
			p.instructions.add(temp);	
		}
	}
}
