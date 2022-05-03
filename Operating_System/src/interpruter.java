import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class interpruter {
	mutexes mut ;
	codeParser parser;
	int[]timeOfArrival;
	Queue<process> readyQueue;
	Queue<process> blockedQueue;
	ArrayList<process> processes;
	int timeSlice;
	int clk;
	int totaNumOfProcesses=3; // blabizo
	int numOfFinshed=0;
	Set<String> ourInstruction = new HashSet<String>();
	public interpruter() {
		this.mut = new mutexes(this.blockedQueue);
		this.parser=new codeParser();
		this.timeOfArrival=new int[totaNumOfProcesses];
		this.readyQueue = new LinkedList<>();
		this.blockedQueue= new LinkedList<>();
		this.processes=new ArrayList<>();
		this.timeSlice=2;
		this.clk=0;
		this.ourInstruction.add("print");
		this.ourInstruction.add("assign");
		this.ourInstruction.add("printFromTo");
		this.ourInstruction.add("semWait");
		this.ourInstruction.add("semSignal");
		this.ourInstruction.add("readFile");
		this.ourInstruction.add("WriteFile");
		
		
	}
	public void readInstructions(String arg,process p) throws FileNotFoundException {
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
	public void programToprocess(ArrayList<String> programs) throws FileNotFoundException {
		for(int i=0;i<programs.size();i++) {
			process p = new process(i,this.timeOfArrival[i], processStatus.NEW);
			this.processes.add(p);
			readInstructions(programs.get(i), p);
			
		}
		this.scheduler();
	}
	public void checkArrival() {
		for(int i =0 ; i <this.processes.size();i++) {
			if (this.processes.get(i).getTimeOfArrival()==clk)this.readyQueue.add(this.processes.get(i));
		}
	}
	public void scheduler() {
		System.out.println("OS starts the scheduler");
		while(true) {
		checkArrival();
		if(!readyQueue.isEmpty()) {
		  process temp = this.readyQueue.poll();
		  System.out.println("process"+" "+temp.getPid()+" is chosen");
		  System.out.println("process"+" "+temp.getPid()+" is currently executing");
		  temp.setCurrentStatus(processStatus.Running);
		  for(int i =0 ; i<timeSlice ;i++) {
		      //get next inst
		      //execute (inst,p)
		      clk++;
		}
		}
		if(numOfFinshed==totaNumOfProcesses)break;
			
		}
		
	}
	
	public void setTimeSlice(int timeSlice) {
		this.timeSlice = timeSlice;
	}
	public static void main(String[] args) throws FileNotFoundException {
		interpruter inter = new interpruter();
		String program1="src/Program_1.txt";
		String program2="src/Program_2.txt";
		String program3="src/Program_3.txt";
		ArrayList<String> programs = new ArrayList<>();
		inter.timeOfArrival[0]=0;
		inter.timeOfArrival[1]=2;
		inter.timeOfArrival[2]=4;
		programs.add(program1);
		programs.add(program2);
		programs.add(program3);
		inter.programToprocess(programs);
//		process p = new process(0, 0, processStatus.BLOCKED);
//		inter.readInstructions(program3, p);
//		int size = p.instructions.size();
//		for( int i =0 ; i<size ;i++) {
//			Stack<String> temp = p.instructions.poll();
//			while(!temp.isEmpty()) {
//				System.out.println(temp.pop());
//			}
//			
//		}
//	}

}
}
